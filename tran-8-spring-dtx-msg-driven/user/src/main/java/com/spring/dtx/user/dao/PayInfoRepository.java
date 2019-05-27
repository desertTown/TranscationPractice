package com.spring.dtx.user.dao;

import com.spring.dtx.user.domain.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayInfoRepository extends JpaRepository<PayInfo, Long> {

    PayInfo findOneByOrderId(Long orderId);
}
