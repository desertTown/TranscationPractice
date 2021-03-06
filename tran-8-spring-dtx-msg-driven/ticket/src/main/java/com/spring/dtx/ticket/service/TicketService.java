package com.spring.dtx.ticket.service;


import com.spring.dtx.service.dto.OrderDTO;
import com.spring.dtx.ticket.dao.TicketRepository;
import com.spring.dtx.ticket.domain.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {

    private static final Logger LOG = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private TicketRepository ticketRepository;

    @Transactional
    @JmsListener(destination = "order:new", containerFactory = "msgFactory")
    public void handleTicketLock(OrderDTO msg) {
        LOG.info("Get new order for ticket lock:{}", msg);
        int lockCount = ticketRepository.lockTicket(msg.getCustomerId(), msg.getTicketNum());
        if (lockCount == 0) {
            msg.setStatus("TICKET_LOCK_FAIL");
            jmsTemplate.convertAndSend("order:fail", msg);
        } else {
            msg.setStatus("TICKET_LOCKED");
            jmsTemplate.convertAndSend("order:locked", msg);
        }
    }

    @Transactional
    @JmsListener(destination = "order:ticket_move", containerFactory = "msgFactory")
    public void handleTicketMove(OrderDTO msg) {
        LOG.info("Get new order for ticket move:{}", msg);
        int moveCount = ticketRepository.moveTicket(msg.getCustomerId(), msg.getTicketNum());
        if (moveCount == 0) {
            LOG.info("Ticket already transferred.");
        }
        msg.setStatus("TICKET_MOVED");
        jmsTemplate.convertAndSend("order:finish", msg);
    }

    /**
     * 触发 error_ticket 的情况：
     *  1. 扣费失败，需要解锁票
     *  2. 订单超时，如果存在锁票就解锁，如果已经交票就撤回
     *  这时候，都已经在OrderDTO里设置了失败的原因，所以这里就不再设置原因。
     * @param msg
     */
    @Transactional
    @JmsListener(destination = "order:ticket_error", containerFactory = "msgFactory")
    public void handleError(OrderDTO msg) {
        LOG.info("Get order error for ticket unlock:{}", msg);
        int count = ticketRepository.unMoveTicket(msg.getCustomerId(), msg.getTicketNum()); // 撤销票的转移
        if (count == 0) {
            LOG.info("Ticket already unlocked:", msg);
        }
        count = ticketRepository.unLockTicket(msg.getCustomerId(), msg.getTicketNum()); // 撤销锁票
        if (count == 0) {
            LOG.info("Ticket already unmoved, or not moved:", msg);
        }
        jmsTemplate.convertAndSend("order:fail", msg);
    }

    @Transactional
    public Ticket lockTicket(OrderDTO orderDTO) {
        Ticket ticket = ticketRepository.findOneByTicketNum(orderDTO.getTicketNum());
        ticket.setLockUser(orderDTO.getCustomerId());
        ticket = ticketRepository.save(ticket);
        try {
            // 这里主要是模拟并发的情况
            //  设计的场景是 customerId为1和customerId为2 分别取锁这张票， 发现后完成update的会覆盖之前update的
            // 造成覆盖的原因是因为hibernate的二级缓存。原理是第一个线程执行save操作之后， 会将结果保存在persistenceContext中，
            // 第二个save操作再执行save操作的时候， persistenceContext就被覆盖了
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        }
        return ticket;
    }

    @Transactional
    public int lockTicket2(OrderDTO orderDTO) {
        // 数据库锁，
        // 下面这条语句被锁了， 会一直等到这
        int updateCount = ticketRepository.lockTicket(orderDTO.getCustomerId(), orderDTO.getTicketNum());
        // 直到第一个语句的十秒结束之后， 才会释放锁， 才会继续执行， 但是另一个线程发现这个锁的lockUser这时候已经变了，已经锁不住了，相当于没有获取到票的锁，所以就跳过了
        LOG.info("Updated ticket count:{}", updateCount);
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        }
        return updateCount;
    }

    @Transactional
    public int unLockTicket(OrderDTO orderDTO) {
        int updateCount = ticketRepository.unLockTicket(orderDTO.getCustomerId(), orderDTO.getTicketNum());
        LOG.info("Updated ticket count:{}", updateCount);
        return updateCount;
    }
}
