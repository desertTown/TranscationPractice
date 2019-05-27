package com.spring.dtx.ticket.dao;

import com.spring.dtx.ticket.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByOwner(Long owner);

    Ticket findOneByTicketNum(Long num);

//    @Modifying(clearAutomatically = true)  // 在同一个实例下， hibernate 的persistenceContext是 同一个， hibernate会帮我们做同步优化的问题
    // 如果在多实例的情况下， 调用完lockTicket这个方法之后， 这个事务就结束了， 但是没有被立刻同步到数据库中。 如果出现这种情况， 则需要加clearAutomatically = true的参数

    @Modifying  //  这种方式实现了幂等性， 多线程执行(多次执行)也不会影响结果
    @Query("UPDATE ticket SET lockUser = ?1 WHERE lockUser is NULL and ticketNum = ?2")
    int lockTicket(Long customerId, Long ticketNum);

    @Modifying
    @Query("UPDATE ticket SET lockUser = null WHERE lockUser = ?1 and ticketNum = ?2")
    int unLockTicket(Long customerId, Long ticketNum);

    @Modifying
    @Query("UPDATE ticket SET owner = ?1, lockUser = null WHERE lockUser = ?1 and ticketNum = ?2")
    int moveTicket(Long customerId, Long ticketNum);

    @Modifying
    @Query("UPDATE ticket SET owner = NULL WHERE owner = ?1 and ticketNum = ?2")
    int unMoveTicket(Long customerId, Long ticketNum);


    // clearAutomatically = true  取消自动同步的作用，但是还是不能解决不同线程 save结果， 后者覆盖前者的情况
    @Override
    @Modifying(clearAutomatically = true)
    Ticket save(Ticket o);

}
