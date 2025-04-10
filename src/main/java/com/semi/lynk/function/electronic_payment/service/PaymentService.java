package com.semi.lynk.function.electronic_payment.service;

import com.semi.lynk.function.electronic_payment.model.dao.PaymentMapper;
import com.semi.lynk.function.electronic_payment.model.dto.DepAndEmpAndHumDTO;
import com.semi.lynk.function.electronic_payment.model.dto.DepartmentDTO;
import com.semi.lynk.function.electronic_payment.model.dto.DraftDTO;
import com.semi.lynk.function.electronic_payment.model.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentMapper paymentMapper;


    @Autowired
    public PaymentService(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }


    // 결재자 목록 조회
    public List<DepAndEmpAndHumDTO> findAllApprovers() {
        return paymentMapper.findAllApprover();
    }


    @Transactional
    public void saveDraft(DraftDTO draftDTO) {
        paymentMapper.insertDraft(draftDTO);
    }


    public List<DepartmentDTO> findAllDepartment() {
        return paymentMapper.findAllDepartment();
    }


}



