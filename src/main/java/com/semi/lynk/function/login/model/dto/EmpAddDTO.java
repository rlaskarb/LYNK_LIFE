package com.semi.lynk.function.login.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmpAddDTO {

    private String userID;
    private String userName;
    private String userPass;
    private String email;
    private int userStatus;
    private int loginFailCount;
    private int workingStatus;
    private String deptNo;
    private String image;
    private MultipartFile profileImage;

    private int roleAdmin;
    private int roleDraft;
    private int roleLeave;
    private int roleDepartment;
    private int roleNotice;
    private int roleSchedule;

}
