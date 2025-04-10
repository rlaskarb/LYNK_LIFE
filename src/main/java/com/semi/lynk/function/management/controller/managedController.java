package com.semi.lynk.function.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semi.lynk.function.management.model.dto.AccountDTO;
import com.semi.lynk.function.management.service.ManagedService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("management/*")
public class managedController {

    @Autowired
    private ManagedService managedService;

    // 관리자 페이지 진입
    // 활성화 계정 목록
    @GetMapping("activeAccountList")
    public String activeAccountListPage(Model model) {
        // 활성화 계정 개수 카운트와 정지 계정 카운트값
        Map<String, Object> counts = managedService.getMemberStatusCounts();
        model.addAttribute("activeAcc", counts.get("activeAcc"));
        model.addAttribute("suspendedAcc", counts.get("suspendedAcc"));

        // 활성화 계정 목록 조회
        List<Map<String, Object>> activeAccs = managedService.getActiveEmployee();
        model.addAttribute("activeAccs", activeAccs);

        return "function/management/activeAccountList";
    }

    // 활성화 계정 목록 - 삭제
    @PostMapping("deleteAccounts")
    public String deactivateAccounts(@RequestParam @Param("empIDs") List<String> empIDs, RedirectAttributes redirectAttributes) {
        System.out.println("controller 단 empIDs = " + empIDs);
        if (empIDs.isEmpty()) {
            System.out.println("empIDs가 비어 있습니다!");
            redirectAttributes.addFlashAttribute("message", "선택된 계정이 없습니다.");
            return "redirect:/management/activeAccountList";
        }

        // 선택된 계정들의 memberStatus를 3으로 업데이트
        managedService.deactivateAccounts(empIDs);

        // 삭제 후 삭제된 계정 목록 페이지로
        redirectAttributes.addFlashAttribute("message", "선택된 계정이 성공적으로 삭제되었습니다.");
        return "redirect:/management/inactiveAccountList";
    }

    // 비활성화 계정 목록
    @GetMapping("inactiveAccountList")
    public String inactiveAccountListPage(Model model) {
        // 비활성화 계정 개수 카운트와 삭제 계정 카운트값
        Map<String, Object> counts = managedService.getMemberStatusCountsInac();
        model.addAttribute("inactiveAcc", counts.get("inactiveAcc"));
        model.addAttribute("removedAcc", counts.get("removedAcc"));

        // 비활성화 계정 목록 조회
        List<Map<String, Object>> inactiveAccs = managedService.getInactiveEmployee();
        model.addAttribute("inactiveAccs", inactiveAccs);

        return "function/management/inactiveAccountList";
    }

    // 활성화 계정 목록 - 복구
    @PostMapping("restoreAccounts")
    public String restoreAccounts(@RequestParam @Param("empIDs") List<String> empIDs, RedirectAttributes redirectAttributes) {
        System.out.println("restoreAccounts controller 단 empIDs = " + empIDs);
        if (empIDs.isEmpty()) {
            System.out.println("empIDs가 비어 있습니다!");
            redirectAttributes.addFlashAttribute("message", "선택된 계정이 없습니다.");
            return "redirect:/management/inactiveAccountList";
        }

        // 선택된 계정들의 memberStatus를 2으로 업데이트
        managedService.restoreAccounts(empIDs);

        // 복구 후 활성화 계정 목록 페이지로
        redirectAttributes.addFlashAttribute("message", "선택된 계정이 성공적으로 복구되었습니다.");
        return "redirect:/management/activeAccountList";
    }

    // 계정 수정
    @GetMapping("editAccount/{empID}")
    public String editAccountPage(@PathVariable("empID") String empID, Model model) {
        // empID를 기반으로 계정 정보를 조회
        AccountDTO accountInfo = managedService.getAccountByEmpID(empID);
//        System.out.println("서비스 리턴 accountInfo: " + accountInfo);

        // 계정 정보를 모델에 추가하여 뷰로 전달
        model.addAttribute("accountInfo", accountInfo);
//        System.out.println("accountInfo 컨트롤러 = " + LocalDateTime.now() + accountInfo);

        // 계정 편집 페이지로 이동
        return "function/management/editAccount";
    }

    @PostMapping("updateAccount")
    public String updateAccount(@ModelAttribute AccountDTO accountInfo,
                                @RequestParam("profileImage") MultipartFile file) {
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/profile/";
        String imagePath = null;

        try {
            if (file != null && !file.isEmpty()) {
                String fileType = file.getContentType();
                if (!fileType.startsWith("image/")) {
                    throw new IllegalArgumentException("Only image files are allowed.");
                }

                // 디렉토리 생성
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // 고유 파일명 생성
                String sanitizedFileName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
                String fileName = System.currentTimeMillis() + "_" + sanitizedFileName;
//                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);

                // 파일 저장
                file.transferTo(filePath.toFile());

                // 저장된 경로를 설정
                imagePath = "/profile/" + fileName;

                System.out.println("File saved at: " + filePath);
                System.out.println("Accessing file at: " + imagePath);
            }

            // 이미지 경로를 DTO에 설정
            accountInfo.setImage(imagePath);

            // 서비스 호출
            managedService.updateAccount(
                    accountInfo.getEmpID(),
                    accountInfo.getEmpName(),
                    accountInfo.getDeptNo(),
                    accountInfo.getPosition(),
                    accountInfo.getEmail(),
                    accountInfo.getImage(),
                    accountInfo.getRoleAdmin()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 업데이트 후 목록 페이지로 리디렉션
        return "redirect:/management/activeAccountList";
    }

    // 권한 관리 페이지
    @GetMapping("accountRoleSetting")
    public String accountRoleSettingPage(Model model) {
        // 계정 목록 조회
        List<Map<String, Object>> accountRoles = managedService.getActiveAccountRole();
        model.addAttribute("accountRoles", accountRoles);

        return "function/management/accountRoleSetting";
    }

    @PostMapping("updateRoles")
    public String updateRoles(@RequestBody List<Map<String, Object>> roles) {
        try {
            List<AccountDTO> processedRoles = new ArrayList<>();

            for (Map<String, Object> role : roles) {
                if (role.containsKey("roles")) {
                    Map<String, Object> nestedRoles = (Map<String, Object>) role.get("roles");
                    AccountDTO dto = new AccountDTO();

                    dto.setEmpID((String) role.get("empID"));
                    dto.setRoleDraft((Integer) nestedRoles.getOrDefault("roleDraft", 0));
                    dto.setRoleLeave((Integer) nestedRoles.getOrDefault("roleLeave", 0));
                    dto.setRoleDepartment((Integer) nestedRoles.getOrDefault("roleDepartment", 0));
                    dto.setRoleNotice((Integer) nestedRoles.getOrDefault("roleNotice", 0));
                    dto.setRoleSchedule(Integer.parseInt((String) nestedRoles.getOrDefault("roleSchedule", 0)));

                    processedRoles.add(dto);
                }
            }

            // 서비스 호출
            managedService.updateRoles(processedRoles);
            return "function/management/accountRoleSetting";
        } catch (Exception e) {
            e.printStackTrace();
            return "function/management/accountRoleSetting";
        }
    }

    // 최근 접속 목록
    @GetMapping("latestAccessList")
    public String latestAccessListPage(Model model) {
        // 활성화 계정 목록 조회
        List<Map<String, Object>> AccountList = managedService.getLatestAccessList();
        model.addAttribute("AccountList", AccountList);

        return "function/management/latestAccessList";
    }

    @GetMapping("accessDetail/{empID}")
    public String accessDetailPage(@PathVariable("empID") String empID, Model model) {
//        System.out.println("컨트롤러 empID = " + empID);
        List<AccountDTO> accessDetail = managedService.getAccessDetailByEmpID(empID);

//        System.out.println("accessDetail = " + accessDetail);
        model.addAttribute("accessDetail", accessDetail);
//        System.out.println("accessDetail.getClass().getName() = " + accessDetail.getClass().getName());
        return "function/management/accessDetail";
    }


//    @GetMapping("accessDetail/{empID}")
//    public String accessDetailPage(@PathVariable("empID") String empID, Model model) {
//        System.out.println("컨트롤러 empID = " + empID);
//        List<AccountDTO> accessDetail = managedService.getAccessDetailByEmpID(empID);
//        System.out.println("서비스 리턴 accessDetail: " + accessDetail);
//
//        // 계정 정보를 모델에 추가하여 뷰로 전달
//        model.addAttribute("accessDetail", accessDetail);
//        System.out.println("accessDetail 컨트롤러 = " + LocalDateTime.now() + accessDetail);
//
//        return "function/management/accessDetail";
//    }

}
