package com.semi.lynk.function.human.model.dao;

import com.semi.lynk.function.human.model.calendar.CalendarDTO;
import com.semi.lynk.function.human.model.calendar.OverTimeApplicationDTO;
import com.semi.lynk.function.human.model.calendar.VacationApplicationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CalendarMapper {
    List<CalendarDTO> showCalendarSelect(int employeeNo , int roleAdmin);

    List<CalendarDTO> showMyAppStatus1(int employeeNo, int roleAdmin);

    List<VacationApplicationDTO> vacationAppMapper(String employeeNo);

    List<OverTimeApplicationDTO> overTimeAppMapper(); // 연장근무 신청 버튼

    int overTimeAppDataMapper(OverTimeApplicationDTO overTimeDTO , int employeeNo);

    int vacAppUpdateMapper(VacationApplicationDTO vacationApplicationDTO
    , int employeeNo);

    int vacAppInsertMapper(VacationApplicationDTO vacationApplicationDTO
    , int employeeNo , Long draftNo);

    // draft 1씩 늘려주기 위한 selet
    int vacAppDayOffCount(VacationApplicationDTO vacationApplicationDTO
    , int employeeNo);

    int vacAppUpdateMapper2(VacationApplicationDTO vacationApplicationDTO
    , int employeeNo , Long draftNo);

//    int vacAppUpdateMapper3(VacationApplicationDTO vacationApplicationDTO
//    , int employeeNo , Long draftNo);

    int vacStatusUpdateMapper(int draftNo, int newState);

    int vacDeleteMapper(int draftNo);

    int vacUsedLeaveMapper(Map<String, Object> usedLeave);

    List<VacationApplicationDTO> vacLeaderSelectMapper(int roleAdmin);

    List<CalendarDTO> showCalendarSelect2(int employeeNo, int roleAdmin);
}
