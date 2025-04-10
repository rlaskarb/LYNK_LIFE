package com.semi.lynk.function.human.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ModifyDTO {

    private int id;
    private String name;
    private int depNo;
    private HumanDTO humanDTO;

//    private DepartmentDTO departmentDTO;
//    private String position;
//    private String employeementStatus;
//    private String phoneNumber;
//    private String ssn;
}
