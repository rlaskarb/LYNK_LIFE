package com.semi.lynk.function.notice_board.controller;

import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import com.semi.lynk.function.notice_board.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/notice/list";
    }

    @GetMapping("/list")
    public String listNotices(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "12") int size) {
        Page<NoticeDTO> noticePage = noticeService.getNoticesPaged(page, size);

        model.addAttribute("notices", noticePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", noticePage.getTotalPages());
        model.addAttribute("totalItems", noticePage.getTotalElements());

        return "function/notice_board/list";
    }

    @GetMapping("/search")
    public String searchNotices(@RequestParam(required = false) int searchType,
                                @RequestParam(required = false) String keyword,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "12") int size,
                                Model model) {
        try {
            Page<NoticeDTO> noticePage = noticeService.searchNotices(searchType, keyword, page, size);

            model.addAttribute("notices", noticePage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", noticePage.getTotalPages());
            model.addAttribute("searchType", searchType);
            model.addAttribute("keyword", keyword);

            return "function/notice_board/search";
        } catch (IllegalArgumentException e) {
            // 예외 처리는 @ExceptionHandler 메서드에서 수행됩니다
            throw e;
        }
    }

    @GetMapping("/manager")
    public String manageNotices(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "12") int size) {
        Page<NoticeDTO> noticePage = noticeService.getAllNotices(page, size);

        model.addAttribute("notices", noticePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", noticePage.getTotalPages());
        model.addAttribute("totalItems", noticePage.getTotalElements());

        return "function/notice_board/manager";
    }

    @GetMapping("/create")
    public String createNoticeForm(Model model) {
        model.addAttribute("noticeDTO", new NoticeDTO());
        return "function/notice_board/create";
    }

    @PostMapping("/create")
    public String createNotice(@ModelAttribute("noticeDTO") NoticeDTO noticeDTO, HttpSession session) {
        String empNo = (String) session.getAttribute("empNo");
        noticeDTO.setNoticeDate(LocalDateTime.now());
        noticeDTO.setEmployeeNo(empNo);
        noticeDTO.setViewerCount(1);
        noticeService.createNotice(noticeDTO);
        return "redirect:/notice/list";
    }

    @GetMapping("/{noticeNo}")
    public String viewNotice(@PathVariable("noticeNo") Long noticeNo, Model model, HttpSession session) {
        noticeService.updateViewCnt(noticeNo);
        NoticeDTO currentNotice = noticeService.getNoticeByNNO(noticeNo);
        model.addAttribute("notice", currentNotice);
        model.addAttribute("currentUser", session.getAttribute("empNo"));

        if (currentNotice!=null && currentNotice.getNoticePreNo() != null) {
            NoticeDTO previousNotice = noticeService.getNoticeByNNO(currentNotice.getNoticePreNo());
            model.addAttribute("preNotice", previousNotice);
        }

        return "function/notice_board/view";
    }

    @GetMapping("/{noticeNo}/edit")
    public String editNoticeForm(@PathVariable Long noticeNo, Model model) {
        NoticeDTO noticeDTO = noticeService.getNoticeByNNO(noticeNo);
        model.addAttribute("noticeDTO", noticeDTO);
        return "function/notice_board/edit";
    }

    @PostMapping("/{noticeNo}/edit")
    public String editNotice(@PathVariable Long noticeNo, @ModelAttribute("noticeDTO") NoticeDTO noticeDTO, HttpSession session) {
        String empNo = (String) session.getAttribute("empNo");
        NoticeDTO existingNotice = noticeService.getNoticeByNNO(noticeNo);

        if (!existingNotice.getEmployeeNo().equals(empNo)) {
            // 권한 없음 처리
            return "redirect:/notice/list";
        }

        noticeDTO.setEmployeeNo(empNo);
        noticeDTO.setNoticeDate(LocalDateTime.now());
        noticeDTO.setViewerCount(1);
        noticeDTO.setNoticePreNo(noticeNo);
        noticeService.updateNotice(noticeNo);
        noticeService.createNotice(noticeDTO);
        return "redirect:/notice/list";
    }

    @GetMapping("/{noticeNo}/delete")
    public String deleteNotice(@PathVariable("noticeNo") Long noticeNo) {
        noticeService.deleteNotice(noticeNo);
        return "redirect:/notice/list";
    }

    @GetMapping("/main/noticeList")
    public ResponseEntity<List<NoticeDTO>> mainNoticesList() {
        try{
            List<NoticeDTO> notices = noticeService.getNotices();
            return ResponseEntity.ok(notices);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);        }
    }
}