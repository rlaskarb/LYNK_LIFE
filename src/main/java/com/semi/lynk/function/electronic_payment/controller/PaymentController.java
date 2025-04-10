package com.semi.lynk.function.electronic_payment.controller;

import com.semi.lynk.function.electronic_payment.model.dto.*;
import com.semi.lynk.function.electronic_payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("/approvers")
    @ResponseBody // JSON 데이터를 반환하기 위해 추가
    public List<DepAndEmpAndHumDTO> findAllApprovers(){
        return paymentService.findAllApprovers();
    }


    @GetMapping("/department")
    @ResponseBody
    public List<DepartmentDTO> findAllDepartment() {return paymentService.findAllDepartment();}


    @GetMapping("/list")
    public String draft() {
        return "function/electronic_payment/list";
    }

    @GetMapping("/general")
    public String general() {return "function/electronic_payment/general";}


    @PostMapping("/save")
    public ResponseEntity<String> saveDraft(@RequestBody DraftDTO draftDTO){
        paymentService.saveDraft(draftDTO);
        return ResponseEntity.ok("Draft saved successfully!");
    }




}
