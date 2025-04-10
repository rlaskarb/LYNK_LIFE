package com.semi.lynk.function.db_management.model.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDTO {
   //납입
    private int paymentCount;  //납입회차
    private int payWith; // 결재수단
    private Date paymentDate; // 납입일
    private int contractMngNo; // 계약일련번호 FK

}
