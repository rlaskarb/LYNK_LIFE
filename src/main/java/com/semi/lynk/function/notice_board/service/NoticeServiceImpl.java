package com.semi.lynk.function.notice_board.service;

import com.semi.lynk.function.notice_board.model.dao.NoticeMapper;
import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Page<NoticeDTO> getNoticesPaged(int page, int size) {
        int pinedCount = noticeMapper.getPinedCount(); // 고정목록글 갯수
        int normalCount = noticeMapper.getNormalCount(); // 고정이 아닌글 갯수
        int start = page * size; // 해당페이지의 시작글번호

        if(pinedCount>start) {
            int end = Math.min(pinedCount, start+size)-start;
            List<NoticeDTO> allNotices = noticeMapper.getPinedNotices(start, end);
            if(end!=12) {
                List<NoticeDTO> normalNotices = noticeMapper.getNormalNotices(0, 12 - (end % 12));
                allNotices.addAll(normalNotices);
            }
            return new PageImpl<>(allNotices, PageRequest.of(page, size), pinedCount+normalCount);
        } else {
            System.out.println(start-pinedCount);
            List<NoticeDTO> allNotices = noticeMapper.getNormalNotices(start-pinedCount, 12);
            return new PageImpl<>(allNotices, PageRequest.of(page, size), pinedCount+normalCount);
        }
        // 고정글을 앞에 넣는 작업
        // count(notice_hide==2)가 12 초과이면 
        // offset으로 12개씩 받아오게 한다면, notice_hide == 2인 목록의 전체 카운트를 받아와서 12 이하면 출력 후
        // notic_hide == 0인 글을 12-count(notice_hide==2)갯수만큼 출력
    }

    @Override
    public Page<NoticeDTO> getAllNotices(int page, int size) {
        int pinedCount = noticeMapper.getPinedCount(); // 고정목록글 갯수
        int allCount = noticeMapper.getAllCount(); // 고정이 아닌글 갯수
        int start = page * size; // 해당페이지의 시작글번호

        if(pinedCount>start) {
            int end = Math.min(pinedCount, start+size)-start;
            List<NoticeDTO> allNotices = noticeMapper.getPinedNotices(start, end);
            if(end!=12) {
                List<NoticeDTO> otherNotices = noticeMapper.getAllNotices(0, 12 - (end % 12));
                allNotices.addAll(otherNotices);
            }
            return new PageImpl<>(allNotices, PageRequest.of(page, size), allCount);
        } else {
            System.out.println(start-pinedCount);
            List<NoticeDTO> allNotices = noticeMapper.getAllNotices(start-pinedCount, 12);
            return new PageImpl<>(allNotices, PageRequest.of(page, size), allCount);
        }
    }

    @Override
    public Page<NoticeDTO> searchNotices(int searchType, String keyword, int page, int size){
        int start = page * size;

        if(searchType==1) {
            try {
                int searchNoCount = noticeMapper.getSearchNoCount(Integer.parseInt(keyword));
                int end = Math.min(searchNoCount, start+size)-start;
                List<NoticeDTO> allNotices = noticeMapper.searchByNoticeNo(keyword, start, end);
                return new PageImpl<>(allNotices, PageRequest.of(page, size), searchNoCount);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("숫자만 입력 가능합니다.");
            }
        }else if(searchType==2){
            int searchTitleCount = noticeMapper.getSearchTitleCount(keyword);
            int end = Math.min(searchTitleCount, start+size)-start;
            List<NoticeDTO> allNotices = noticeMapper.searchByNoticeTitle(keyword, start, end);
            return new PageImpl<>(allNotices, PageRequest.of(page, size), searchTitleCount);
        }else {
            int searchEmpCount = noticeMapper.getSearchEmpCount(keyword);
            int end = Math.min(searchEmpCount, start+size)-start;
            List<NoticeDTO> allNotices = noticeMapper.searchByNoticeEmp(keyword, start, end);
            return new PageImpl<>(allNotices, PageRequest.of(page, size), searchEmpCount);
        }
    }



    @Override
    public void createNotice(NoticeDTO noticeDTO) {
        noticeMapper.insertNotice(noticeDTO);
    }


    @Override
    public NoticeDTO getNoticeByNNO(Long noticeNo) {
        return noticeMapper.selectNoticeByNNO(noticeNo);
    }

    @Override
    public void updateNotice(Long noticeNo) {
        noticeMapper.updateNotice(noticeNo);
    }

    @Override
    public void deleteNotice(Long noticeNo) {
        noticeMapper.updateNotice(noticeNo);
    }

    @Override
    public void updateViewCnt(Long noticeNo) {
        noticeMapper.updateViewCnt(noticeNo);
    }

    @Override
    public List<NoticeDTO> getNotices(){
        return noticeMapper.getPinedNotices(0,4);
    }
}
