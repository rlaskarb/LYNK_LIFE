package com.semi.lynk.function.approval_system.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class EmployeeDTO {
    private int employeeNo;
    private String employeeName;
    private String departmentNo;
    private String departmentName;
    private String position;
}
