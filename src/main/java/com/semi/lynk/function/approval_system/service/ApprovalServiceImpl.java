package com.semi.lynk.function.approval_system.service;

import com.semi.lynk.function.approval_system.model.dao.ApprovalMapper;
import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import com.semi.lynk.function.approval_system.model.dto.ApprovalList;
import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import com.semi.lynk.function.approval_system.model.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ApprovalServiceImpl implements ApprovalService {
    @Autowired
    private ApprovalMapper approvalMapper;

    @Override
    public void setDraftState(Long draftNo, int lastStep){
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        approvalMapper.setDraftStepAndState(draftNo, lastStep,0);
    };

    @Override
    public List<EmployeeDTO> getAllEmployees()
    {
        List<EmployeeDTO> employees = approvalMapper.getAllEmployees();
        System.out.println("employees = " + employees);
        return employees;
    }

    @Override
    public void createApproval(ApprovalList approvalList){
        List<ApprovalDTO> approvals = approvalList.getApprovals();
        int i=0;
        for (ApprovalDTO approval : approvals) {
            i+=approvalMapper.insertApproval(approval);
        }
    };

    @Override
    public Long createDraft(DraftDTO draftDTO){
        approvalMapper.insertDraft(draftDTO);
        return draftDTO.getDraftNo();
    }

    @Override
    public Page<DraftDTO> getDraftsPaged(String empNo, String state, int page, int size, String keyword){
        // 여기서 state는 결재중, 결재완료, 반려의 상태에 따라 쿼리문이 변경됨
        int count = approvalMapper.getDraftsCount(empNo, state, keyword);    // 페이징을 하기위해 먼저 전체 갯수 받아옴
        int start = page * size; // 해당페이지의 시작글번호
        List<DraftDTO> drafts = approvalMapper.getDrafts(empNo, state, start, size, keyword);
        return new PageImpl<>(drafts, PageRequest.of(page, size), count);
    }

    @Override
    public Page<DraftDTO> getDraftsForAprovalPaged(String empNo, int page, int size, String state){
        int count = approvalMapper.getApprovalsCount(empNo);    // 페이징을 하기위해 먼저 전체 갯수 받아옴
        int start = page * size; // 해당페이지의 시작글번호
        List<DraftDTO> approvals = approvalMapper.selectForApproval(empNo, start, size,state);
        return new PageImpl<>(approvals, PageRequest.of(page, size), count);
    }

    @Override
    public DraftDTO getDraftByDNO(Long draftNo){
        return approvalMapper.selectDraftByDNO(draftNo);
    }

    @Override
    public void approveApproval(Long draftNo, String empNo){
        DraftDTO draftDTO = approvalMapper.selectDraftByDNO(draftNo);
        int lastStep = draftDTO.getDraftLastStep(); // 결재 최종 단계
        int curStep = draftDTO.getDraftCurrentStep(); // 현재 결재 단계

        ApprovalDTO approvalDTO = new ApprovalDTO();
        approvalDTO.setApprovalCompletionTime(LocalDateTime.now());
        approvalDTO.setApprovalState(2);
        approvalMapper.updateApproval(draftNo,empNo,approvalDTO);

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        int count;
        count = approvalMapper.countCurrentStep(draftNo,curStep);

        while(count==0){
            if(lastStep==curStep){
                approvalMapper.setDraftStepAndState(draftNo, lastStep,2);
                break;
            }else {
                curStep++;
                count = approvalMapper.countCurrentStep(draftNo, curStep);
            }
        }
        approvalMapper.setDraftCurrentStep(draftNo, curStep);
        approvalMapper.setDraftStateAndTime(draftNo,1, currentTime);
    }
    @Override
    public void updateApproval(Long draftNo, String empNo, int state){
        ApprovalDTO approvalDTO = new ApprovalDTO();
        approvalDTO.setApprovalCompletionTime(LocalDateTime.now());
        approvalDTO.setApprovalState(state);

        approvalMapper.updateApproval(draftNo,empNo,approvalDTO);

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        approvalMapper.setDraftStateAndTime(draftNo,state, currentTime);
    }

    @Override
    public void deleteDraft(Long draftNo){
        approvalMapper.deleteApprovalByDNO(draftNo);
        approvalMapper.deleteDraftByDNO(draftNo);
    }

    @Override
    public List<ApprovalDTO> getApproval(Long draftNo){
        List<ApprovalDTO> approvalDTOS = approvalMapper.selectApprovals(draftNo);
        return approvalDTOS;
    }
}
