package com.semi.lynk.function.human.service;

import com.semi.lynk.function.human.model.calendar.OverTimeApplicationDTO;
import com.semi.lynk.function.human.model.calendar.VacationApplicationDTO;
import com.semi.lynk.function.human.model.dao.CalendarMapper;
import com.semi.lynk.function.human.model.calendar.CalendarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalendarService {

    private final CalendarMapper calendarMapper;

    @Autowired
    public CalendarService (CalendarMapper calendarMapper) {
        this.calendarMapper = calendarMapper;
    }

    public List<CalendarDTO> calendarService (int employeeNo , int roleAdmin) {
        return calendarMapper.showCalendarSelect(employeeNo, roleAdmin);
    }

    public List<CalendarDTO> myAppStatusService(int employeeNo,int roleAdmin) {
        List<CalendarDTO> appStatus = calendarMapper.showMyAppStatus1(employeeNo,roleAdmin);
        System.out.println("appStatus = " + appStatus);
//        List<CalendarDTO> appStatus2 = calendarMapper.showMyAppStatus2();
//        List<CalendarDTO> appStatus = new ArrayList<>();
//        appStatus.addAll(appStatus1);
//        appStatus.addAll(appStatus2);
        return appStatus;
    }

    public List<VacationApplicationDTO> vacationStatus(String employeeNo) {
        System.out.println("휴가 신청 했을 때 서비스 왔는지 =================================");
        return calendarMapper.vacationAppMapper(employeeNo);
    }

    @Transactional
    public int vacAppService(VacationApplicationDTO vacationApplicationDTO , String employeeNoString , Long draftNo) {
        System.out.println("서비스 오는지=====================================");
        int employeeNo = Integer.parseInt(employeeNoString); // String 이었으므로 int로

//        int select = calendarMapper.vacAppDayOffCount(vacationApplicationDTO , employeeNo);
//        System.out.println("1 : " + select);
//        int dayOff = select + 1;
//        System.out.println("2 : " + dayOff);

//        vacationApplicationDTO.setDraftNo(dayOff);

//        System.out.println("vacationApplicationDTO : " + vacationApplicationDTO);
        int result1 = calendarMapper.vacAppUpdateMapper(vacationApplicationDTO,employeeNo);
//        System.out.println("result1 : " + result1);
//        int result2 = calendarMapper.vacAppInsertMapper1(vacationApplicationDTO);
//        System.out.println("result2 : " + result2);

//        vacationApplicationDTO.getDraftNo();
        int result2 = calendarMapper.vacAppInsertMapper(vacationApplicationDTO,employeeNo , draftNo);
//        System.out.println("result3 : " + result2);

        int result3 = calendarMapper.vacAppUpdateMapper2(vacationApplicationDTO,employeeNo , draftNo);
//        System.out.println("result3 : " + result3);

//        int result4 = calendarMapper.vacAppUpdateMapper3(vacationApplicationDTO,employeeNo , draftNo);
//        System.out.println("result4 : " + result4);

        return (result1 >= 1) && (result2 >= 1) && (result3 >= 1) ? 1 : 0;
    }

    public List<OverTimeApplicationDTO> overTimeAppService() {
        return calendarMapper.overTimeAppMapper();
    }

    @Transactional
    public int overTimeAppDataService(OverTimeApplicationDTO overTimeDTO , String employeeNos) {
        int employeeNo = Integer.parseInt(employeeNos);
        int result = calendarMapper.overTimeAppDataMapper(overTimeDTO, employeeNo);
        return result >= 1 ? 1 : 0;
    }

    @Transactional
    public int updateDraftState(int draftNo, int newState) {
        int result = calendarMapper.vacStatusUpdateMapper(draftNo , newState);
        System.out.println("Update result: " + result + ", draftNo: " + draftNo + ", newState: " + newState);

        return result >= 1 ? 1 : 0;
    }

    @Transactional
    public void cancelVacation(int draftNo, float usedLeave, int employeeNo) {
        // 1. 휴가 데이터 삭제
        int deleted = calendarMapper.vacDeleteMapper(draftNo);
        if (deleted <= 0) {
            throw new IllegalStateException("휴가 데이터를 삭제하지 못했습니다.");
        }

        // 2. usedLeave 감소
        Map<String, Object> params = new HashMap<>();
        params.put("usedLeave", usedLeave);
        params.put("employeeNo", employeeNo);

        int updated = calendarMapper.vacUsedLeaveMapper(params);
        if (updated <= 0) {
            throw new IllegalStateException("연차 사용량 감소 실패");
        }
    }

    public List<VacationApplicationDTO> vacationLeaderStatus(int roleAdmin) {

        return calendarMapper.vacLeaderSelectMapper(roleAdmin);
    }

    public List<CalendarDTO> calendarService2(int employeeNo, int roleAdmin) {
        return calendarMapper.showCalendarSelect2(employeeNo, roleAdmin);
    }
}
