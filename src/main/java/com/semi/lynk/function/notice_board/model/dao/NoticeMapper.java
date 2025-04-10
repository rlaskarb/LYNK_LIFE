package com.semi.lynk.function.notice_board.model.dao;

import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    void insertNotice(NoticeDTO noticeDTO);
    NoticeDTO selectNoticeByNNO(Long noticeNo);
    void updateNotice(Long noticeNo);
    void deleteNotice(Long noticeNo);
    void updateViewCnt(Long noticeNo);

    List<NoticeDTO> getPinedNotices(int pageNo, int count);

    List<NoticeDTO> getNormalNotices(int pageNo, int count);

    List<NoticeDTO> getAllNotices(int pageNo, int count);

    int getPinedCount();

    int getNormalCount();

    int getAllCount();

    List<NoticeDTO> searchByNoticeNo(String keyword, int page, int count);

    int getSearchNoCount(int keyword);

    List<NoticeDTO> searchByNoticeTitle(String keyword, int page, int count);

    int getSearchTitleCount(String keyword);

    List<NoticeDTO> searchByNoticeEmp(String keyword, int page, int count);

    int getSearchEmpCount(String keyword);
}
