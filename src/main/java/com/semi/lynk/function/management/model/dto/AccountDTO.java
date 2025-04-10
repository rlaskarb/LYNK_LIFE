package com.semi.lynk.function.management.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountDTO {
    private String empID;
    private String empName;
    private String deptName;
    private String position;
    private String email;
    private Date loginTime;
//    private LocalDateTime loginTime;
    private int memberStatus;
    private int loginStatus;
    private String image;
    private MultipartFile profileImage;

    private String empPwd;
    private int deptNo;

    private Integer roleAdmin;
    private Integer roleDraft;
    private Integer roleLeave;
    private Integer roleDepartment;
    private Integer roleNotice;
    private Integer roleSchedule;
}
