package com.semi.lynk.function.notice_board.service;

import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoticeService {
    void createNotice(NoticeDTO noticeDTO);

    NoticeDTO getNoticeByNNO(Long noticeNo);
    void updateNotice(Long noticeNo);
    void deleteNotice(Long noticeNo);
    void updateViewCnt(Long noticeNo);

    Page<NoticeDTO> getNoticesPaged(int page, int size);

    Page<NoticeDTO> getAllNotices(int page, int size);

    Page<NoticeDTO> searchNotices(int searchType, String keyword, int page, int size);

    List<NoticeDTO> getNotices();
}
