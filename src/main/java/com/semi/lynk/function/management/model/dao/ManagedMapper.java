package com.semi.lynk.function.management.model.dao;

import com.semi.lynk.function.management.model.dto.AccountDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ManagedMapper {

    //활성화 계정
    Map<String, Object> getMemberStatusCounts();
    List<Map<String, Object>> getActiveEmployee();

    //활성화 계정 삭제
    void deactivateAccounts(@Param("empIDs") List<String> empIDs);

    //비활성화 계정
    Map<String, Object> getMemberStatusCountsInac();
    List<Map<String, Object>> getInactiveEmployee();

    // 삭제 계정 복구
    void restoreAccounts(@Param("empIDs") List<String> empIDs);

    //계정 편집
    AccountDTO getAccountByEmpID(String empID);

    void updateAccount(String empID, String empName, int deptNo, String position, String email, String image, Integer roleAdmin);

    //계정 권한
    List<Map<String, Object>> getActiveAccountRole();

//    void updateRoles(List<Map<String, Object>> roles);
    void updateRoles(List<AccountDTO> roles);

    List<Map<String, Object>> getLatestAccessList();

    List<AccountDTO> getAccessDetailByEmpID(String empID);
}
