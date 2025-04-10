package com.semi.lynk.function.human.service;

import com.semi.lynk.function.human.model.dao.EmployeeMapper;
import com.semi.lynk.function.human.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeService {

    private EmployeeMapper mapper;

    @Autowired
    public EmployeeService (EmployeeMapper mapper) {
        this.mapper = mapper;
    }

    public List<EmployeeDTO> employeeList () {

        return mapper.employeeFullList();
    }

    public List<EmpAndDepDTO> joinList() {
        // 기존 조회
        List<EmpAndDepDTO> list = mapper.joinListResult();

        // 각 사원의 이미지 조회 및 설정
        for (EmpAndDepDTO emp : list) {
            List<EmpAndDepDTO> images = mapper.findEmployeeImageByEmployeeNo(emp.getEmployeeNo());
            if (!images.isEmpty()) {
                emp.setImage(images.get(0).getImage()); // 첫 번째 이미지 설정
            } else {
                emp.setImage("/images/default.png"); // 기본 이미지
            }
        }

        return list;
    }


    @Transactional
    public int humanRegist (RegistHumDTO registHumDTO) {

        int result2 = mapper.registMapperhum(registHumDTO);

        return result2 >= 1 ? 1 : 0;
    }

    public List<LookUpDTO> lookupData() {
        return mapper.lookUpMapper();
    }

    @Transactional
    public int modifyService(ModifyDTO modifyDTO) {
        int result = mapper.modifyEmployee(modifyDTO);

        return result >= 1 ? 1 : 0;
    }
}
