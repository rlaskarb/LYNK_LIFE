package com.semi.lynk.function.approval_system.model.dao;

import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import com.semi.lynk.function.approval_system.model.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApprovalMapper {
    DraftDTO selectDraftByDNO(Long draftNo);

    void insertDraft(DraftDTO draftDTO);

    int insertApproval(ApprovalDTO approvalDTO);

    int getDraftsCount(String empno, String state, String keyword);

    List<EmployeeDTO> getAllEmployees();

    List<DraftDTO> getDrafts(String empno, String state, int page, int count, String keyword);

    void setDraftStepAndState(Long draftNo, Integer lastStep, Integer state);

    void setDraftStateAndTime(Long draftNo,int state, String currentTime);

    List<DraftDTO> selectForApproval(String empNo, int start, int size, String state);

    int getApprovalsCount(String empNo);

    void updateApproval(Long draftNo, String empNo, ApprovalDTO approvalDTO);

    int countCurrentStep(Long draftNo, int currentStep);

    void setDraftCurrentStep(Long draftNo, Integer curStep);

    void deleteApprovalByDNO(Long draftNo);

    void deleteDraftByDNO(Long draftNo);

    List<ApprovalDTO> selectApprovals(Long draftNo);
}
