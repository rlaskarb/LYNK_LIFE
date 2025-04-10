package com.semi.lynk.function.approval_system.service;

import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import com.semi.lynk.function.approval_system.model.dto.ApprovalList;
import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import com.semi.lynk.function.approval_system.model.dto.EmployeeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApprovalService {
    void createApproval(ApprovalList approvalList);

    Long createDraft(DraftDTO draftDTO);

    Page<DraftDTO> getDraftsPaged(String empNo, String state, int page, int size, String keyword);

    DraftDTO getDraftByDNO(Long draftNo);

    List<EmployeeDTO> getAllEmployees();

    void setDraftState(Long draftNo, int lastStep);

    Page<DraftDTO> getDraftsForAprovalPaged(String empNo, int page, int size, String state);

    void approveApproval(Long draftNo, String empNo);

    void updateApproval(Long draftNo, String empNo, int i);

    void deleteDraft(Long draftNo);

    List<ApprovalDTO> getApproval(Long draftNo);
}
