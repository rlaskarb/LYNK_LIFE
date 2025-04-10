package com.semi.lynk.function.management.service;

import com.semi.lynk.function.management.model.dao.ManagedMapper;
import com.semi.lynk.function.management.model.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ManagedService {

    //****************************************************************
    // 관리자 페이지
    //****************************************************************

    @Autowired
    private ManagedMapper managedMapper;

    //****************************************************************
    // 활성화 / 비활성화 계정 조회 페이지
    //****************************************************************

    // 활성화 계정 수 카운트
    public Map<String, Object> getMemberStatusCounts() {
        return managedMapper.getMemberStatusCounts();
    }

    // 비활성화 계정 수 카운트
    public Map<String, Object> getMemberStatusCountsInac() {
        return managedMapper.getMemberStatusCountsInac();
    }

    // 활성화 계정 리스트
    public List<Map<String, Object>> getActiveEmployee() {
        return managedMapper.getActiveEmployee();
    }

    // 비활성화 계정 리스트
    public List<Map<String, Object>> getInactiveEmployee() {
        return managedMapper.getInactiveEmployee();
    }

    // 계정 삭제
    public void deactivateAccounts(List<String> empIDs) {
        System.out.println("서비스단 empIDs = " + empIDs);
        if (empIDs == null || empIDs.isEmpty()) {
            System.out.println("empIDs가 비어 있습니다!");
        }
        managedMapper.deactivateAccounts(empIDs);
    }

    // 계정 복구
    public void restoreAccounts(List<String> empIDs) {
        if (empIDs == null || empIDs.isEmpty()) {
            System.out.println("empIDs가 비어 있습니다!");
        }
        managedMapper.restoreAccounts(empIDs);
    }

    //****************************************************************

    public AccountDTO getAccountByEmpID(String empID) {
        return managedMapper.getAccountByEmpID(empID);
    }

    public void updateAccount(String empID, String empName, int deptNo, String position, String email, String image, Integer roleAdmin) {
        System.out.println("image = " + image);
        managedMapper.updateAccount(empID, empName, deptNo, position, email, image, roleAdmin);
    }

    // 계정 권한
    public List<Map<String, Object>> getActiveAccountRole() {
        return managedMapper.getActiveAccountRole();
    }

    @Transactional
    public void updateRoles(List<AccountDTO> roles) {
        for (AccountDTO role : roles) {
            managedMapper.updateRoles(Collections.singletonList(role));
        }

    }

    public List<Map<String, Object>> getLatestAccessList() {
        return managedMapper.getLatestAccessList();
    }

//    public List<AccountDTO> getAccessDetailByEmpID(String empID) {
//        List<Map<String, Object>> result = managedMapper.getAccessDetailByEmpID(empID);
//        List<AccountDTO> dtoList = new ArrayList<>();
//
//        for (Map<String, Object> map : result) {
//            AccountDTO dto = new AccountDTO();
//            dto.setEmpID((String) map.get("EMPLOYEE_NO"));
//            dto.setEmpName((String) map.get("EMPLOYEE_NAME"));
//            dto.setDeptName((String) map.get("DEPARTMENT_NAME"));
//            dto.setPosition((String) map.get("POSITION"));
//            dto.setEmail((String) map.get("EMAIL"));
//            dto.setLoginTime((Date) map.get("LOGIN_TIME"));
//            dto.setLoginStatus((Integer) map.get("LOGIN_STATUS"));
//            dtoList.add(dto);
//        }
//
//        return dtoList;
//    }

    public List<AccountDTO> getAccessDetailByEmpID(String empID) {
        System.out.println("서비스 empID = " + empID);
        List<AccountDTO> result = managedMapper.getAccessDetailByEmpID(empID);
        System.out.println("result = " + result);
        return result;
    }


}
