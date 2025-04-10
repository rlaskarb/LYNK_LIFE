package com.semi.lynk.function.electronic_payment.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthorizationDTO {
    private int authOfAdmin; // ADMIN권한여부
    private int authOfDocument ; // 기안승인권한여부
    private int authOfLeave; // 연차관리권한여부
    private int authOfDepartment; // 부서관리권한여부
    private int authOfNotice; // 공지관리권한여부
    private int authOfSchedule; // 일정관리권한여부
    private String employeeNo ; // 사번
}
