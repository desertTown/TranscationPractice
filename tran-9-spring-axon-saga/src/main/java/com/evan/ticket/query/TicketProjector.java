package com.evan.ticket.query;


import com.evan.ticket.event.OrderTicketMovedEvent;
import com.evan.ticket.event.OrderTicketPreservedEvent;
import com.evan.ticket.event.OrderTicketUnlockedEvent;
import com.evan.ticket.event.TicketCreatedEvent;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketProjector {

    private static final Logger LOG = LoggerFactory.getLogger(TicketProjector.class);

    @Autowired
    private TicketEntityRepository ticketEntityRepository;

    @EventSourcingHandler
    public void onCreate(TicketCreatedEvent event) {
        TicketEntity ticket = new TicketEntity();
        ticket.setId(event.getTicketId());
        ticket.setName(event.getName());
        ticketEntityRepository.save(ticket);
        LOG.info("Executed event in TicketProjector:{}", event);
    }

    @EventSourcingHandler
    public void onPreserve(OrderTicketPreservedEvent event) {
        TicketEntity ticket = ticketEntityRepository.getOne(event.getTicketId());
        ticket.setLockUser(event.getCustomerId());
        ticketEntityRepository.save(ticket);
        LOG.info("Executed event in TicketProjector:{}", event);
    }

    @EventSourcingHandler
    public void onUnlock(OrderTicketUnlockedEvent event) {
        TicketEntity ticket = ticketEntityRepository.getOne(event.getTicketId());
        ticket.setLockUser(null);
        ticketEntityRepository.save(ticket);
        LOG.info("Executed event in TicketProjector:{}", event);
    }

    @EventSourcingHandler
    public void onMove(OrderTicketMovedEvent event) {
        TicketEntity ticket = ticketEntityRepository.getOne(event.getTicketId());
        ticket.setLockUser(null);
        ticket.setOwner(event.getCustomerId());
        ticketEntityRepository.save(ticket);
        LOG.info("Executed event in TicketProjector:{}", event);
    }

}
