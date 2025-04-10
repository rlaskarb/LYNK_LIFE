package com.semi.lynk.function.electronic_payment.model.dao;

import com.semi.lynk.function.electronic_payment.model.dto.DepAndEmpAndHumDTO;
import com.semi.lynk.function.electronic_payment.model.dto.DepartmentDTO;
import com.semi.lynk.function.electronic_payment.model.dto.DraftDTO;
import com.semi.lynk.function.electronic_payment.model.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {

    List<DepAndEmpAndHumDTO> findAllApprover();

    void insertDraft(DraftDTO draftDTO);

    List<DepartmentDTO> findAllDepartment();


}
