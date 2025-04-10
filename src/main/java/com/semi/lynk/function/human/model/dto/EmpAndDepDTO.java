package com.semi.lynk.function.human.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EmpAndDepDTO {

    // 불러올 애들
    private int employeeNo;                         // 사번
    private String name;                    // 이름
    private DepartmentDTO departmentDTO;    // 부서명
    private HumanDTO humanDTO;              // 직책
    private String email;                   // 이메일
    private String image;                 // 사진
}
