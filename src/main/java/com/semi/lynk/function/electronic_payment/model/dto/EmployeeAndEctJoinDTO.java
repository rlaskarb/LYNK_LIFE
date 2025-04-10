package com.semi.lynk.function.electronic_payment.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeAndEctJoinDTO {
    private HumanResourceDTO humanResourceDTO ; // position 직책을위한 join
    private AuthorizationDTO authorizationDTO ; // authOfDocument 기안승인권한을위한 join
    private String employeeName; // 이름
    private DepartmentDTO departmentDTO; // departmentName 부서명을위한 join
    private String employeeNo; // 사번
    private int departmentNo; // 부서번호
    private String departmentName; // 부서명
    private int authOfDocument ; // 기안승인권한여부
    private String position; // 직책

}
