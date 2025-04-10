package com.semi.lynk.function.human.controller;

import com.semi.lynk.function.human.model.calendar.CalendarDTO;
import com.semi.lynk.function.human.service.CalendarService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/*")
public class CalendarController {

    private final CalendarService calendarService;

    @Autowired
    public CalendarController (CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping(value = "calendar", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<CalendarDTO> calendarList(Principal principal, HttpSession session) {
//        System.out.println("calendar 컨트롤러 : " + calendarService.calendarService());

        int roleAdmin = (int) session.getAttribute("roleAdmin");
        // 로그인한 사용자의 사번 가져오기
        String employeeNo = principal.getName(); // 사번이 Principal 객체에서 가져온다고 가정

        // 디버깅용 로그
        System.out.println("로그인된 사용자의 사번: " + employeeNo);

        return calendarService.calendarService(Integer.parseInt(employeeNo),roleAdmin);
    }

    @GetMapping(value = "calendar2", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<CalendarDTO> calendarList2(Principal principal, HttpSession session) {
//        System.out.println("calendar 컨트롤러 : " + calendarService.calendarService());

        int roleAdmin = (int) session.getAttribute("roleAdmin");
        // 로그인한 사용자의 사번 가져오기
        String employeeNo = principal.getName(); // 사번이 Principal 객체에서 가져온다고 가정

        // 디버깅용 로그
        System.out.println("로그인된 사용자의 사번: " + employeeNo);

        return calendarService.calendarService2(Integer.parseInt(employeeNo),roleAdmin);
    }

}
