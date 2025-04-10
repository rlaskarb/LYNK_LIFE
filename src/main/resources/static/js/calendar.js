document.addEventListener("DOMContentLoaded", () => {
    const today = new Date();
    const currentYear = today.getFullYear();
    const currentMonth = today.getMonth(); // 현재 월 (0부터 시작)

    // 월 이름 배열
    const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

    // 해당 월의 첫째 날과 마지막 날짜 계산
    const firstDay = new Date(currentYear, currentMonth, 1);
    const lastDate = new Date(currentYear, currentMonth + 1, 0).getDate();

    // 달력 제목 업데이트
    const calendarHeader = document.getElementById("calendar-header");
    calendarHeader.textContent = `${monthNames[currentMonth]} ${currentYear}`;

    // 요일 배열
    const dayNames = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];

    // 달력 생성
    const calendarDays = document.getElementById("calendar-days");
    calendarDays.innerHTML = ""; // 기존 내용을 초기화

    // 첫째 날의 요일을 기준으로 공백 추가
    for (let i = 0; i < firstDay.getDay(); i++) {
        const emptyCell = document.createElement("div");
        calendarDays.appendChild(emptyCell);
    }

    // 날짜와 요일 표시
    for (let date = 1; date <= lastDate; date++) {
        const day = new Date(currentYear, currentMonth, date).getDay(); // 요일 계산
        const button = document.createElement("button");
        button.innerHTML = `${dayNames[day]}<br>${date}`;

        // 오늘 날짜 강조 스타일
        if (date === today.getDate() && currentMonth === today.getMonth()) {
            button.style.backgroundColor = "#2D2D2D"; // 강조 색상 - 배경
            button.style.color = "#FFFFFF"; // 강조 색상 - 글씨
            button.style.fontWeight = "bold";
        }

        const dayCell = document.createElement("div");
        dayCell.appendChild(button);
        calendarDays.appendChild(dayCell);
    }
});