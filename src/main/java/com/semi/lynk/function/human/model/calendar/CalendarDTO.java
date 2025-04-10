package com.semi.lynk.function.human.model.calendar;

import com.semi.lynk.function.human.model.dto.DepartmentDTO;
import com.semi.lynk.function.human.model.dto.EmployeeDTO;
import com.semi.lynk.function.human.model.dto.HumanDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CalendarDTO {
    private String id; // 유일 식별자 (UUID 또는 DB 식별자)
//    private String title; // 이벤트 제목
//    private String memo;  // 이벤트에 대한 설명
//    private String start; // 시작 날짜 (ISO 8601 형식)  ex) 2024-12-17T14:30:00+09:00
//    private String end;   // 종료 날짜 (ISO 8601 형식)  ex) 2024-12-17T14:30:00+09:00

    // 근태 캘린더
    private EmployeeDTO employeeDTO; // 이름 불러와야돼서
    private HumanDTO humanDTO; // 연차 발생일수 / 사용 연차
    private CommuteDTO commuteDTO; // 출퇴근 이력 DTO
    private ScheduleDTO scheduleDTO; // 일정 DTO
    private DayOffDTO dayOffDTO;          // 휴가 DTO
    private ApprovalDTO approvalDTO;   // 승인 관련 애들 (얜 안 쓴다 함)

    private DraftshDTO draftshDTO;      // 기안

    // 신청 현황
    private DepartmentDTO departmentDTO;  // 부서 DTO
//    private int approver;               // 상태 (0 개인 1 부서 2 전사)
//    private LocalDateTime approveTime; // 결재 승인 시간
//    private LocalDateTime draftDate;    // 기안한 시간
//    private LocalDateTime draftTime;    // 결재 완료 시간


    // 나의 신청 조회 - 상세 조회 - 연장 근무 시간 때문에...
    private Date startOverDay;          // 시작일
    private LocalDateTime startOverTime; // 시작 시간

    private Date endOverDay;            // 종료일
    private LocalDateTime endOverTime;  //종료 시간
    //
    private LocalDateTime workOff;    // 퇴근 시간
    // 기본 퇴근시간(18:00) + (종료시간 - 시작시간)

    private LocalDateTime totalOverTime; // 종료시간 - 시작 시간 = 총 연장 근로 시간

//    private String category;            // 구분 (연차 / 반차 / 연장근무)
//
//    public String getCategory() {
//        if (dayOffDTO != null) {
//            if (dayOffDTO.getLeaveType() == 1) return "반차";
//            if (dayOffDTO.getLeaveType() == 2) return "연차";
//        }
//        if (scheduleDTO != null) {
//            return "연장근무";
//        }
//        return "알 수 없음";
//    }
}
