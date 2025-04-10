package com.semi.lynk.function.electronic_payment.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DepAndEmpAndHumDTO {
    private String employeeNo; // 사번
    private String employeeName; // 이름
    private DepartmentDTO departmentDTO; // departmentName 부서명을위한 join
    private HumanResourceDTO humanResourceDTO ; // position 직책을위한 join

}
