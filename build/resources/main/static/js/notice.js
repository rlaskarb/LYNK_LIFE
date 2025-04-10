async function fetchNotice() {
    try {
        const response = await fetch('/notice/main/noticeList');

        if (!response.ok) {
            console.log(`API 호출 실패 : ${response.status} ${response.statusText}`);
            throw new Error(`서버 에러발생 : ${response.status}`);
        }
        const notices = await response.json();

        const noticeList = document.getElementById('mainNotice');

        // 기존 내용을 모두 비우기
        noticeList.innerHTML = '';

        // 데이터가 없을 경우
        if (!notices || notices.length === 0) {
            noticeList.innerHTML = '<p>고정된 공지사항이 없습니다.</p>';
            return;
        }

        // 테이블 생성
        const table = document.createElement('table');
        table.classList.add('mainNotice');

        // 테이블 본문 생성
        const tbody = document.createElement('tbody');
        notices.forEach(notice => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${new Date(notice.noticeDate).toLocaleDateString()}</td>
                <td>${notice.noticeTitle}</td>
                <td>${notice.employeeName}</td>
            `;

            // 계약 번호를 data-* 속성으로 추가
            row.dataset.noticeNo = notice.noticeNo;

            // 클릭 시 상세 페이지로 이동
            row.addEventListener('click', () => {
                window.location.href = `/notice/${notice.noticeNo}`;
            });

            tbody.appendChild(row);
        });
        table.appendChild(tbody);

        // 테이블을 목록에 추가
        noticeList.appendChild(table);

    } catch (error) {
        const noticeList = document.getElementById('mainNotice');
        noticeList.innerHTML = '<p>데이터를 불러오는 중 문제가 발생했습니다.</p>';
        console.error('공지사항을 가져오는 중 에러 발생:', error);
    }
}

document.addEventListener('DOMContentLoaded', fetchNotice);