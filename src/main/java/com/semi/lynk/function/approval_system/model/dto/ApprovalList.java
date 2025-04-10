package com.semi.lynk.function.approval_system.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApprovalList {
    private List<ApprovalDTO> approvals = new ArrayList<>();
}
