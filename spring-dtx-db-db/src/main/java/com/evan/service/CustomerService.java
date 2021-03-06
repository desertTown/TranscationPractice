package com.evan.service;

import com.evan.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    @Qualifier("userJdbcTemplate")
    private JdbcTemplate userJdbcTemplate;

    @Autowired
    @Qualifier("orderJdbcTemplate")
    private JdbcTemplate orderJdbcTemplate;

    private static final String SQL_UDPATE_DEPOSIT = "UPDATE customer SET deposit = deposit -  ?  WHERE id = ?";
    private static final String SQL_CREATE_ORDER = "INSERT INTO customer_order (customer_id, title,amount) VALUES (?,?,?)";

    @Transactional
    public void createOrder(Order order) {
        userJdbcTemplate.update(SQL_UDPATE_DEPOSIT,order.getAmount(),order.getCustomerId());
        if(order.getTitle().contains("error1")){
            throw  new RuntimeException("Error1");
        }
        //  如果在DBConfiguration中没有配置链式事务的Bean， 当上面报错时， 上面的事务会回滚，
        //  但是下面的一句会正常执行， 不会回滚(这时候的orderJdbcTemplate没在上一个事务中)
        //   因为@Transaction会拿@Primary的数据源去开启管理事务， 而orderJdbcTemplate只是同步到当前事务中，
        //   如果出现问题， 当前事务并不会帮orderJdbcTemplate进行事务回滚， orderJdbcTemplate是默认自动提交的
        orderJdbcTemplate.update(SQL_CREATE_ORDER,order.getCustomerId(),order.getTitle(),order.getAmount());
        if(order.getTitle().contains("error2")){
            throw  new RuntimeException("Error2");
        }
    }

    public Map userInfo(Long id) {
        Map customer = userJdbcTemplate.queryForMap("SELECT * FROM customer where id = " + id);
        List orders = orderJdbcTemplate.queryForList("select * from customer_order where customer_id=" + id);
        Map result = new HashMap();
        result.put("customer",customer);
        result.put("order",orders);
        return result;
    }
}
