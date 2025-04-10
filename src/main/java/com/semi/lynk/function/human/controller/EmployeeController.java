package com.semi.lynk.function.human.controller;

import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import com.semi.lynk.function.approval_system.service.ApprovalService;
import com.semi.lynk.function.human.model.calendar.CalendarDTO;
import com.semi.lynk.function.human.model.calendar.OverTimeApplicationDTO;
import com.semi.lynk.function.human.model.calendar.VacationApplicationDTO;
import com.semi.lynk.function.human.model.dto.*;
import com.semi.lynk.function.human.service.CalendarService;
import com.semi.lynk.function.human.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/employee/*")
public class EmployeeController {

    private EmployeeService employeeService;
    private CalendarService calendarService;
    private ApprovalService approvalService;

    private static final Logger logger = LogManager.getLogger(EmployeeController.class);
    private final MessageSource messageSource;

    @Autowired
    public EmployeeController (EmployeeService employeeService
                               , CalendarService calendarService
                                , MessageSource messageSource
                                ,ApprovalService approvalService) {
        this.employeeService = employeeService;
        this.calendarService = calendarService;
        this.messageSource = messageSource;
        this.approvalService = approvalService;
    }


    @GetMapping("lookup")
    public String employeeList (Model model) {

        List<EmployeeDTO> list = employeeService.employeeList();

        for (EmployeeDTO emp : list) {
            System.out.println(emp);
        }
        model.addAttribute("list", list);

        return "function/human/attendance";
    }

    @GetMapping (value = "lookUpList" , produces = "application/json; charset=UTF-8")
    @ResponseBody // 인사 조회 시 불려올 데이터
    public List<LookUpDTO> lookUpList () {

        List<LookUpDTO> result = employeeService.lookupData();
//        logger.info("인사 조회 시 불려올 데이터 :" + result);

        return result;
    }


    @GetMapping ("list")
    public String look () {
        return "function/human/attendance";
    }

    @GetMapping ("inform")
    public String humanInform () {
        return "function/human/lookUp";
    }

//    @PostMapping("modify") // 인사 수정 메서드 (update)
//    public String modifyMethod (@ModelAttribute ModifyDTO modifyDTO
//                                , RedirectAttributes rtt, Locale locale) {
//        int result = employeeService.modifyService(modifyDTO);
//
//        if (result == 1){
//            rtt.addFlashAttribute("modifyMessage"
//                    ,messageSource.getMessage("modifySuccess",new Object[]{modifyDTO.getName()} , locale));
//            return "redirect:/employee/inform";
//        } else {
//            return "function/human/lookup";
//        }
//    }

    @PostMapping("modify")
    @ResponseBody // JSON 응답으로 변환!!
    public Map<String, Object> modifyMethod(@RequestBody ModifyDTO modifyDTO) {
        System.out.println("수신된 DTO: " + modifyDTO);
        int result = employeeService.modifyService(modifyDTO);
        Map<String, Object> response = new HashMap<>();

        if (result == 1) {
            response.put("status", "success");
            response.put("message", "직원 정보가 성공적으로 수정되었습니다.");
        } else {
            response.put("status", "error");
            response.put("message", "직원 정보 수정에 실패했습니다.");
        }
        return response;
    }


    // 인사 등록 창에 인사 등록 안 된 애들 조회해주는 거
    @GetMapping("join")
    public ModelAndView joinList (ModelAndView mv) {

        List<EmpAndDepDTO> list = employeeService.joinList();
//        for (EmpAndDepDTO emp : list) {
//            System.out.println(emp);
//        }

        mv.addObject("list" , list);
        mv.setViewName("function/human/registPage");
        return mv;
    }

    @GetMapping("regist")
    public String moveRegistPage () {
        return "forward:/employee/join";
    }
    // forward를 이용해서 join에 처리 위임
    // forward 안 쓰고 function/human/registPage 그대로 쓰면 값이 안 담겨 있고,
    // 값 담으려면 중복되는 값을 또 넣어줘야 하지만 forward로 끝.


    @PostMapping ("regist") // 사용자가 form태그의 등록 눌렀을 때 동작
    public String humanRegist (@ModelAttribute RegistHumDTO registHumDTO
                               ,EmpAndDepDTO empAndDepDTO
                               ,RedirectAttributes rtt
                                ,Locale locale) {

        System.out.println("registHumDTO: " + registHumDTO);
        int result = employeeService.humanRegist(registHumDTO);

        if (result == 1) {
            rtt.addFlashAttribute("successMessage"
                    ,messageSource.getMessage("registSuccess",new Object[]{empAndDepDTO.getName()} , locale));
            return "redirect:/employee/regist";
            // redirect는 url이 동작 , view 페이지 입력이 아니라, 핸들러 메서드를 지정해야 함!!
            // 클릭 시  @GetMapping("regist")
            //    public String moveRegistPage () {
            //        return "function/human/registPage";
            //    }가 동작하며 registPage로 옴!
        } else {
            return "redirect:/employee/main";
        }
    }

    @GetMapping ("attendance")
    public String attendanceMethod () {
        return "function/human/attendance";
    }

    @GetMapping ("appStatus") // 관리자 결재 현황 페이지 반환하는 애
    public String appStatusPage () {
        return "function/human/myApplicationStatus";
    }

    // json이 default이므로 produces 안 써도 되지만, 가독성을 위해 쓰는 게 좋음
    @GetMapping(value = "appStatusList", produces = "application/json; charset=UTF-8")
    @ResponseBody // fetch 보내는 애
    public List<CalendarDTO> appStatusList (HttpSession session) {
        String empNoo = (String) session.getAttribute("empNo");

        DraftDTO draftDTO = new DraftDTO();
        draftDTO.setDraftCompletionTime(null);

        int employeeNo = Integer.parseInt(empNoo);
        System.out.println("employeeNo = " + employeeNo);
        int roleAdmin = (int) session.getAttribute("roleAdmin");
        System.out.println("roleAdmin = " + roleAdmin);
//        System.out.println("appStatus = " + appStatus);
        return calendarService.myAppStatusService(employeeNo,roleAdmin);
    }

    // 연차 사용 계획서 작성에 본인 연차 정보 들어가는 애
    @GetMapping(value = "vacationSelect", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, Object> vacationSelect(HttpSession session) {
        String employeeNo = (String) session.getAttribute("empNo");
//        String employeeName = (String) session.getAttribute("empName");

        if (employeeNo == null) {
            throw new IllegalArgumentException("사번 정보가 없습니다. 다시 로그인해주세요.");
        }

        List<VacationApplicationDTO> vacStatusResult = calendarService.vacationStatus(employeeNo);

        // 반환 데이터에 employeeNo 추가
        Map<String, Object> response = new HashMap<>();
        response.put("employeeNo", employeeNo); // 사번 정보 추가
        response.put("vacStatusResult", vacStatusResult); // 기존 연차 데이터 추가
//        response.put("employeeName" , employeeName); // 이름

        return response;
    }

    // 연차 사용 계획서 작성 담당자 이름 불러오는 애
    @GetMapping(value = "leaderSelect", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, Object> vacationLeaderSelect(HttpSession session) {
//        String employeeNo = (String) session.getAttribute("empNo");
        int roleAdmin = (int) session.getAttribute("roleAdmin");

        List<VacationApplicationDTO> leaderName = calendarService.vacationLeaderStatus(roleAdmin);

        // 반환 데이터에 employeeNo 추가
        Map<String, Object> response = new HashMap<>();

        response.put("employeeName" , leaderName); // 이름

        return response;
    }


    // 연차 사용 계획서 제출 시에 update 되는 애
    // 글고 ResponseBody로 제출 완료 / 실패 여부 확인함
    @PostMapping(value = "vacAppResult", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, Object> vacAppResult(@RequestBody VacationApplicationDTO vacationApplicationDTO
                                    , Principal principal , HttpSession session) {
        DraftDTO draftDTO = new DraftDTO();
        draftDTO.setDraftNo(null);
        draftDTO.setEmployeeNo((String) session.getAttribute("empNo"));
        draftDTO.setDraftTitle("연차");
        draftDTO.setDraftDate(LocalDateTime.now());
        draftDTO.setDraftCompletionTime(null);
        draftDTO.setDraftRetentionSchedule(1);
        draftDTO.setDraftState(0);
        draftDTO.setDraftCost(0);
        draftDTO.setDraftLastStep(1);
        draftDTO.setDraftCurrentStep(1);
        draftDTO.setDraftMemo("없음");
        draftDTO.setEmployeeName((String) session.getAttribute("empName"));
        Long draftNo = approvalService.createDraft(draftDTO);

        String employeeNo = principal.getName();
        int result = calendarService.vacAppService(vacationApplicationDTO, employeeNo,draftNo);


        Map<String, Object> map = new HashMap<>();

        if (result == 1) {
            map.put("status", "success");
            map.put("message", "휴가 신청이 완료되었습니다.");
        } else {
            map.put("status", "error");
            map.put("message", "휴가 신청에 실패하였습니다..");
        }
        return map;
    }

    // 모달 시도하려 했던 애. 모달 안 돼서 일단 얼러트로 바꿈.
//    @PostMapping(value = "vacAppResult", produces = "application/json; charset=UTF-8")
//    public String vacAppResult (@RequestBody VacationApplicationDTO vacationApplicationDTO
//                                 ,EmpAndDepDTO empAndDepDTO // 신청 하고 이름 뜨게 할라고
//                                 , RedirectAttributes rttr, Locale locale) {
//
//        System.out.println("vacationApplicationDTO: " + vacationApplicationDTO);
//
//        int result = calendarService.vacAppService(vacationApplicationDTO);
//        if (result == 1){
//            rttr.addFlashAttribute("vacAppMessage",
//                    messageSource.getMessage("vacAppSuccess",
//                            new Object[]{empAndDepDTO.getName()} ,locale));
//            return "redirect:/employee/attendance";
//        } else {
//            return "redirect:/employee/attendance";
//        }
//    }

    @GetMapping (value = "overTimeAppSelect", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<OverTimeApplicationDTO> overTimeSelect () {

        List<OverTimeApplicationDTO> approver = calendarService.overTimeAppService();

        return approver;
    }

    @PostMapping (value = "overTimeAppResult", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, Object> overTimeResult (@RequestBody OverTimeApplicationDTO overTimeDTO
    ,HttpSession session) {
        String employeeNo = (String) session.getAttribute("empNo");
        Map<String, Object> map = new HashMap<>();

        int result = calendarService.overTimeAppDataService(overTimeDTO , employeeNo);
        if (result == 1) {
            map.put("status" , "overTimeAppSuccess");
            map.put("message" , "연장 근무 신청이 완료되었습니다.");
        } else {
            map.put("status" , "overTimeAppFail");
            map.put("message" , "연장 근무 신청에 실패하였습니다.");
        }
        return map;
    }

    @PostMapping (value = "updateDraftState", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, Object> updateDraftState(@RequestParam("draftNo") int draftNo, @RequestParam("newState") int newState) {
        Map<String, Object> response = new HashMap<>();

        System.out.println("Received draftNo: " + draftNo +
                            ", newState: " + newState);

        try {
            int result = calendarService.updateDraftState(draftNo, newState);
            if (result > 0) {
                response.put("success", true);
            } else {
                response.put("success", false);
                response.put("message", "상태 업데이트에 실패했습니다.");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "서버 오류: " + e.getMessage());
        }
        return response;
    }

    @PostMapping(value = "cancelVacation", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, Object> cancelVacation(
            @RequestParam int draftNo,
            @RequestParam float usedLeave,
            @RequestParam int employeeNo) {

        Map<String, Object> response = new HashMap<>();
        try {
            calendarService.cancelVacation(draftNo, usedLeave, employeeNo);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

}
