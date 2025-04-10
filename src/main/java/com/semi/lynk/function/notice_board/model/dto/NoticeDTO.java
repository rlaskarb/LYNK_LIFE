package com.semi.lynk.function.notice_board.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NoticeDTO {
    private Long noticeNo;
    private LocalDateTime noticeDate;
    private int noticeVote;
    private String noticeTitle;
    private String noticeContent;
    private int noticeHide;
    private int viewerCount;
    private String employeeNo;
    private String employeeName;
    private Long noticePreNo;
}
