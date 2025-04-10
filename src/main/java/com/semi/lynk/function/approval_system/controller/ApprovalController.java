package com.semi.lynk.function.approval_system.controller;

import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import com.semi.lynk.function.approval_system.model.dto.ApprovalList;
import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import com.semi.lynk.function.approval_system.model.dto.EmployeeDTO;
import com.semi.lynk.function.approval_system.service.ApprovalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/approval/list/ondraft";
    }

    @GetMapping("/credraft")
    public String creDraft(Model model) {
        model.addAttribute("draftDTO", new DraftDTO());
        return "function/approval_system/create";
    }

    @PostMapping("/credraft")
    public String createDraft(@ModelAttribute("draftDTO") DraftDTO draftDTO, HttpSession session, RedirectAttributes redirectAttributes) {
        String empNo = (String) session.getAttribute("empNo");
        draftDTO.setEmployeeNo(empNo);
        draftDTO.setDraftCurrentStep(1);
        draftDTO.setDraftState(8);
        draftDTO.setDraftDate(LocalDateTime.now());
        draftDTO.setDraftLastStep(9);
        Long draftNo = approvalService.createDraft(draftDTO);
        redirectAttributes.addAttribute("draftNo", draftNo.toString() );
        return "redirect:/approval/addApproval";
    }

    @GetMapping("/{draftNo}")
    public String viewDraft(@PathVariable("draftNo") Long draftNo, Model model) {

        DraftDTO draft = approvalService.getDraftByDNO(draftNo);
        List<ApprovalDTO> approvalDTOS = approvalService.getApproval(draftNo);

        model.addAttribute("approvals", approvalDTOS);
        model.addAttribute("draft", draft);

        return "function/approval_system/view";
    }

    @GetMapping("/app/{draftNo}")
    public String viewAppDraft(@PathVariable("draftNo") Long draftNo
            ,@RequestParam("action") String action, Model model) {

        DraftDTO draft = approvalService.getDraftByDNO(draftNo);
        List<ApprovalDTO> approvalDTOS = approvalService.getApproval(draftNo);

        model.addAttribute("approvals", approvalDTOS);
        model.addAttribute("draft", draft);
        model.addAttribute("action", action);

        return "function/approval_system/approvalview";
    }



    @GetMapping("/list/{action}")
    public String draftList(Model model, HttpSession session,
                          @PathVariable String action,
                          @RequestParam(required = false) String keyword,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "12") int size) {
        String state = "(draft_state < 2)";   // 조건에 따라 맞는 쿼리문을 넘기기 위한 변수, 기본값 ondraft

        switch (action) {
            case "findraft" : state = "(draft_state = 2)"; break;
            case "readydraft" : state = "(draft_state = 8)"; break;
            case "dindraft" : state = "(draft_state = 9)"; break;
        }


        String empNo = (String) session.getAttribute("empNo");
        Page<DraftDTO> draftPage = approvalService.getDraftsPaged(empNo, state, page, size, keyword);

        model.addAttribute("drafts", draftPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", draftPage.getTotalPages());
        model.addAttribute("totalItems", draftPage.getTotalElements());
        model.addAttribute("action", action);
        return "function/approval_system/list";
    }

    @GetMapping("/{action}/search")
    public String search(Model model, HttpSession session,
                        @PathVariable String action,
                        @RequestParam(required = false) String keyword,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "12") int size) {
        String state = "(draft_state < 2) and (draft_title LIKE CONCAT('%', #{keyword}, '%'))";
        // 조건에 따라 맞는 쿼리문을 넘기기 위한 변수, 기본값 ondraft

        switch (action) {
            case "findraft" : state = "(draft_state = 2) and (draft_title LIKE CONCAT('%', #{keyword}, '%'))"; break;
            case "readydraft" : state = "(draft_state = 8) and (draft_title LIKE CONCAT('%', #{keyword}, '%'))"; break;
            case "dindraft" : state = "(draft_state = 9) and (draft_title LIKE CONCAT('%', #{keyword}, '%'))"; break;
        }

        String empNo = (String) session.getAttribute("empNo");
        Page<DraftDTO> draftPage = approvalService.getDraftsPaged(empNo, state, page, size, keyword);

        model.addAttribute("drafts", draftPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", draftPage.getTotalPages());
        model.addAttribute("totalItems", draftPage.getTotalElements());
        model.addAttribute("action", action);
        return "function/approval_system/list";
    }

    @GetMapping("/{draftNo}/edit")
    public String editDraft(@PathVariable("draftNo") Long draftNo, Model model, HttpSession session) {
        List<EmployeeDTO> employees = approvalService.getAllEmployees();
        String empNo = (String) session.getAttribute("empNo");

        model.addAttribute("draftNo", Long.valueOf(draftNo));
        model.addAttribute("myEmpNo", empNo);
        model.addAttribute("employees", employees);
        model.addAttribute("approvals", new ApprovalList());
        return "function/approval_system/approval";
    }

    @GetMapping("/addApproval")
    public String showAddApprovalForm(@RequestParam String draftNo, Model model, HttpSession session) {

        List<EmployeeDTO> employees = approvalService.getAllEmployees();
        String empNo = (String) session.getAttribute("empNo");

        model.addAttribute("draftNo", Long.valueOf(draftNo));
        model.addAttribute("myEmpNo", empNo);
        model.addAttribute("employees", employees);
        model.addAttribute("approvals", new ApprovalList());
        return "function/approval_system/approval";
    }

    @PostMapping("/addApproval")
    public String addApproval(@ModelAttribute("approvalList") ApprovalList approvalList,
                              @RequestParam("draftNo") Long draftNo) {

        int lastStep = 1;
        for (ApprovalDTO approval : approvalList.getApprovals()) {
            if (approval.getApprovalStep() > lastStep) {
                lastStep = approval.getApprovalStep();
            }
        }
        approvalService.setDraftState(draftNo, lastStep);
        approvalService.createApproval(approvalList);
        return "redirect:/approval/list/ondraft";
    }
    @GetMapping("/{action}/approval")
    public String findraftList(Model model, HttpSession session,
                               @PathVariable String action,
                               @RequestParam(required = false) String keyword,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "12") int size) {
        String empNo = (String) session.getAttribute("empNo");
        String state = null;
        switch (action) {
            case "fin" : state = "AND approval_state > 1"; break;
            case "do" : state = "AND approval_state < 2 AND a.approval_step = dr.draft_current_step"; break;
        }

        Page<DraftDTO> draftPage = approvalService.getDraftsForAprovalPaged(empNo, page, size, state);

        model.addAttribute("drafts", draftPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", draftPage.getTotalPages());
        model.addAttribute("totalItems", draftPage.getTotalElements());
        model.addAttribute("action", action);
        return "function/approval_system/approval_list";
    }

    @GetMapping("/{draftNo}/{action}")
    public String updateDraft(@PathVariable("draftNo") Long draftNo,@PathVariable("action") String action, HttpSession session) {
        String empNo = (String) session.getAttribute("empNo");

        switch (action) {
            case "check" : approvalService.updateApproval(draftNo,empNo,1);
                return "redirect:/approval/doapproval";
            case "delete" : approvalService.deleteDraft(draftNo);
                return "redirect:/approval/list/readydraft";
            case "approve" : approvalService.approveApproval(draftNo,empNo);break;
            case "reject" : approvalService.updateApproval(draftNo,empNo,9);break;
        }
        return "redirect:/approval/fin/approval";
    }
}





