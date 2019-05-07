package com.evan.c3_02_spring_txn_jms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.PostConstruct;


@Service
@Slf4j
public class CustomerService {


    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @PostConstruct
    public void init() {
        jmsTemplate.setReceiveTimeout(3000);
    }

    @JmsListener(destination = "customer:msg:new", containerFactory = "msgFactory")
    public void handle(String msg) {
        log.debug("============== Get JMS message to from customer:{}", msg);
        String reply = "Replied - " + msg;
        jmsTemplate.convertAndSend("customer:msg:reply", reply);
        if (msg.contains("error")) {
            simulateError();
        }
    }

    @JmsListener(destination = "customer:msg2:new", containerFactory = "msgFactory")
    public void handle2(String msg) {
        log.debug("============== Get JMS message2 to from customer:{}", msg);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setTimeout(15);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String reply = "Replied-2 - " + msg;
            jmsTemplate.convertAndSend("customer:msg:reply", reply);
            if (!msg.contains("error")) {
                transactionManager.commit(status);
            } else {
                transactionManager.rollback(status);
            }
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
    /*
    注解方式添加事务
     */
//    @Transactional
//    @JmsListener(destination = "customer:msg2:new", containerFactory = "msgFactory")
//    public void handle3(String msg) {
//        log.debug("============== Get JMS message2 to from customer:{}", msg);
//        String reply = "Replied-2 - " + msg;
//        jmsTemplate.convertAndSend("customer:msg:reply", reply);
//    }

    private void simulateError() {
        throw new RuntimeException("some Data error.");
    }
}
