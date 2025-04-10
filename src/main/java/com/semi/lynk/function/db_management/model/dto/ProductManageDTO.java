package com.semi.lynk.function.db_management.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductManageDTO {
    // 상품관리
    private int productCategory; // 보험회사명 코드
    private String productNo; // 상품번호
    private String productName; // 상품


}
