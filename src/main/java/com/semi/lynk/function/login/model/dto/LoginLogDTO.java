package com.semi.lynk.function.login.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginLogDTO {

    private String empNo;
    private Integer loginStatus;
    private Timestamp loginTime;

    private String workDate;
    private String workOn;
    private Date workOff;
    private Date workOnOutside;
    private Date workOffOutside;

}
