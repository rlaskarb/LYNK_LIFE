// 달력에 데이터 띄우눈 애
document.addEventListener('DOMContentLoaded', function () {
    const calendarEl = document.getElementById('calendar');
    const modalEl = new bootstrap.Modal(document.getElementById('eventModal'), {});

    const calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,dayGridWeek,dayGridDay'
        },
        initialDate: '2025-01-01',
        navLinks: true,
        editable: false,
        dayMaxEvents: true,

        // 이벤트 로딩 함수
        events: function (fetchInfo, successCallback, failureCallback) {
            const fetchCalendar1 = fetch('/api/calendar')
                .then((response) => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                });

            const fetchCalendar2 = fetch('/api/calendar2')
                .then((response) => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                });

            // 두 API의 데이터를 병합
            Promise.all([fetchCalendar1, fetchCalendar2])
                .then(([data1, data2]) => {
                    const seenEvents = new Set();
                    const events = [];

                    // 데이터1 처리
                    data1.forEach(item => {
                        const uniqueKey = `${item.employeeDTO?.name}-${item.dayOffDTO?.leaveStartDate}-${item.dayOffDTO?.leaveType}`;
                        if (!seenEvents.has(uniqueKey)) {
                            events.push({
                                id: uniqueKey,
                                title: `${item.employeeDTO?.name || 'Unknown'} ${item.humanDTO?.position || ''}`,
                                start: item.dayOffDTO?.leaveStartDate,
                                backgroundColor: item.dayOffDTO?.leaveType === 2 ? 'green' :
                                    item.dayOffDTO?.leaveType === 1 ? 'lightgreen' : 'orange',
                                extendedProps: {
                                    type: item.dayOffDTO?.leaveType === 2 ? '연차' :
                                        item.dayOffDTO?.leaveType === 1 ? '반차' : '연장근무',
                                    name: item.employeeDTO?.name || 'Unknown',
                                    department: item.departmentDTO?.depName || 'N/A',
                                    position: item.humanDTO?.position || 'N/A',
                                    startDate: item.dayOffDTO?.leaveStartDate,
                                    endDate: item.dayOffDTO?.leaveEndDate
                                }
                            });
                            seenEvents.add(uniqueKey);
                        }
                    });

                    // 데이터2 처리 (scheduleDTO 데이터 처리)
                    data2.forEach(item => {
                        const scheduleKey = `${item.employeeDTO?.name}-${item.scheduleDTO?.scheduleStartDate}-${item.scheduleDTO?.scheduleEndDate}`;
                        if (!seenEvents.has(scheduleKey)) {
                            events.push({
                                title: `${item.employeeDTO?.name || 'Unknown'} ${item.humanDTO?.position || ''} (연장근무)`,
                                start: item.scheduleDTO?.scheduleStartDate,
                                end: item.scheduleDTO?.scheduleEndDate,
                                backgroundColor: 'orange',
                                extendedProps: {
                                    id: item.employeeDTO.employeeNo,
                                    type: '연장근무',
                                    name: item.employeeDTO?.name || 'Unknown',
                                    department: item.departmentDTO?.depName || 'N/A',
                                    position: item.humanDTO?.position || 'N/A',
                                    memo: item.scheduleDTO?.scheduleNote || '없음',
                                    startDate: item.scheduleDTO.scheduleStartDate,
                                    endDate: item.scheduleDTO.scheduleEndDate
                                }
                            });
                            seenEvents.add(scheduleKey);
                        }
                    });

                    successCallback(events);
                })
                .catch((error) => {
                    console.error('Error fetching calendar events:', error);
                    failureCallback(error);
                });
        },

        // 클릭 했을 때 상세 정보 나오는 애
        eventClick: function (info) {
            const props = info.event.extendedProps;

            // 시간 변환 함수
            function formatLocalDateTime(isoString) {
                if (!isoString) return "N/A"; // 데이터가 없을 경우 표시
                const options = {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                    hour: "2-digit",
                    minute: "2-digit",
                    second: "2-digit",
                    hour12: false
                };
                return new Date(isoString).toLocaleString("ko-KR", options);
            }

            // 모달 내용 업데이트
            document.getElementById('eventType').textContent = props.type || '없음';
            document.getElementById('eventName').textContent = props.name || '없음';
            document.getElementById('eventDepartment').textContent = props.department || '없음';
            document.getElementById('eventPosition').textContent = props.position || '없음';

            // 날짜 출력 변환
            document.getElementById('eventStart').textContent = formatLocalDateTime(props.startDate);
            document.getElementById('eventEnd').textContent = formatLocalDateTime(props.endDate);

            // 사유 확인 (사유를 직접 입력하지 않으면 value 값이 넘어와서, 수정이 필요함)
            if (props.type === '연장근무'){
                document.getElementById("eventMemo").textContent = props.memo || '사유';
            } else {
                document.getElementById("eventMemo").textContent = '개인 사유';
            }

            // 모달 표시
            modalEl.show();
        }

    });

    calendar.render();
});


/////////////////////////////// 휴가 신청 눌렀을 때 동작하는 애
let initialTotalLeave = 0;      // 총 연차
let initialUsedLeave = 0;       // 이미 사용한 연차
let initialRemainingLeave = 0;  // 초기 남은 연차

document.getElementById("vacation-button-id").addEventListener("click", () => {
    fetch(`/employee/vacationSelect`)
        .then((res) => res.json())
        .then((data) => {
            const { employeeNo, employeeName ,vacStatusResult } = data; // 반환된 데이터 구조를 분해하여 사용

            if (vacStatusResult && vacStatusResult.length > 0) {
                const leaveInfo = vacStatusResult[0];

                // 초기 값 저장
                initialTotalLeave = leaveInfo.totalLeave;
                initialUsedLeave = leaveInfo.usedLeave;
                initialRemainingLeave = initialTotalLeave - initialUsedLeave;

                // UI 갱신
                document.getElementById("allLeaveDay").value = initialTotalLeave.toFixed(1);
                document.getElementById("remainingDay").value = initialRemainingLeave.toFixed(1);
                document.getElementById("useDay").value = '';

                // 결재자 목록 추가
                const leaderSelect2 = document.getElementById("leader2");
                leaderSelect2.innerHTML = ""; // 초기화

                // 두 번째 fetch, 담당자 이름 가져오기
                return fetch(`/employee/leaderSelect`);
            } else {
                alert("데이터를 불러오지 못했습니다.");
            }

            console.log("현재 로그인한 사번:", employeeNo);
        })
        .then((res) => res.json())
        .then((leaderData) => {
            if (leaderData && leaderData.employeeName && leaderData.employeeName.length > 0) {
                leaderData.employeeName.forEach(item => {
                    const option = document.createElement("option");
                    option.value = item.employeeNo;
                    option.innerText = item.employeeName;
                    leader2.appendChild(option);
                });
            } else {
                alert("담당자 데이터를 불러오지 못했습니다.");
            }
        })
        .catch((error) => {
            console.error("데이터 로드 실패:", error);
        });

    const modalElement = new bootstrap.Modal(document.getElementById("vacationModal"), {});
    modalElement.show();
});


function getDateTime(divId) {
    const date = document.querySelector(`#${divId} input[type="date"]`).value;
    const time = document.querySelector(`#${divId} select`).value;
    if (date && time) {
        return new Date(`${date}T${time}`); // 'yyyy-MM-ddTHH:mm'
    }
    return null; // 값이 없으면 null 반환
}

document.getElementById("startDay").addEventListener("change", (event) => {
    const startDate = event.target.value;
    if (startDate) {
        document.getElementById("endDay").setAttribute("min", startDate);
    }
});

function calculateLeave() {
    const startDateTime = getDateTime("startDateTime");
    const endDateTime = getDateTime("endDateTime");

    if (!startDateTime || !endDateTime) {
        resetFields(); // 필드 초기화
        return;
    }

    if (endDateTime < startDateTime) {
        alert("종료일과 시간이 시작일과 시간보다 빠를 수 없습니다.");
        resetFields();
        return;
    }

    const diffInMs = endDateTime - startDateTime;
    const diffInDays = Math.floor(diffInMs / (1000 * 60 * 60 * 24)); // 날짜 차이
    const remainingHours = (diffInMs % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60); // 남은 시간

    let leaveDays = diffInDays; // 기본 날짜 수

    if (remainingHours > 0) {
        if (remainingHours <= 5) {
            leaveDays += 0.5; // 반차
        } else if (remainingHours <= 9) {
            leaveDays += 1.0; // 하루
        } else {
            leaveDays += Math.ceil(remainingHours / 9); // 9시간 단위로 추가
        }
    }

    const remainingLeave = initialRemainingLeave - leaveDays; // 남은 연차 계산
    if (remainingLeave < 0) {
        alert("사용 연차가 총 연차를 초과할 수 없습니다.");
        resetFields();
        return;
    }

    document.getElementById("useDay").value = leaveDays.toFixed(1);
    document.getElementById("remainingDay").value = remainingLeave.toFixed(1);
}


["startDay", "startTime", "endDay", "endTime"].forEach((id) => {
    document.getElementById(id).addEventListener("change", calculateLeave);
});


function resetFields() {
    document.getElementById("useDay").value = '';
    document.getElementById("remainingDay").value = initialRemainingLeave.toFixed(1);
}



//////////////////////////////////////////////////////////

//// 제출 버튼 누르면 서버에 데이터 저장하는애
document.getElementById("vacationApp").addEventListener("click", () => {

    // const name = document.getElementById("leader2").value;
    // const scheduleDate = document.getElementById("startDateTime").value;

    const startDay = document.getElementById("startDay").value;
    const startTime = document.getElementById("startTime").value || "00:00";

    const endDay = document.getElementById("endDay").value;
    const endTime = document.getElementById("endTime").value || "23:59";

    // `Date` 객체 생성
    const leaveStartDate = new Date(`${startDay}T${startTime}:00`);
    const leaveEndDate = new Date(`${endDay}T${endTime}:00`);

    // 9시간 빼기였는데 안 뺌
    const leaveStartAdjusted = new Date(leaveStartDate.getTime()); // 9시간을 밀리초로 계산해서 뺌
    const leaveEndAdjusted = new Date(leaveEndDate.getTime());
    console.log("leaveStartAdjusted : " + leaveStartAdjusted); // 얘가 알맞게 출력
    console.log("leaveEndAdjusted : " + leaveEndAdjusted); // 얘가 알맞게 출력

    // ISO 8601 형식으로 변환 (얘는 날짜 제대로 안 나오지만 이거 주석하면 제출이 안 됨)
    const leaveStartIso = leaveStartAdjusted.toISOString(); // 'yyyy-MM-ddTHH:mm:ssZ'
    const leaveEndIso = leaveEndAdjusted.toISOString(); // 'yyyy-MM-ddTHH:mm:ssZ'
    console.log("leaveStartIso : " + leaveStartIso);
    console.log("leaveEndIso : " + leaveEndIso);

    // 이렇게 ISO8601 형식으로 타입 맞춰줘야함
    // const leaveStartDate = `${startDay}T${startTime}`;
    // const leaveEndDate = `${endDay}T${endTime}`;

    // const usedLeave = parseFloat(document.getElementById("useDay").value);
    const usedLeave = parseFloat(document.getElementById("useDay").value);
    if (isNaN(usedLeave) || usedLeave <= 0) {
        alert("총 사용 개수를 확인하고 제출해주세요.");
        return;
    }

    // if (isNaN(usedLeave) || usedLeave <= 0) {
    //     alert("총 사용 개수를 확인하고 제출해주세요.");
    //     return;
    // }

    const leaveType = usedLeave === 0.5 ? 1 : 2;

    const vacationApplicationDTO = {
        // name: name,
        // scheduleStartDate: scheduleStartDate,
        // scheduleEndDate: scheduleEndDate,
        leaveStartDate: leaveStartIso,
        leaveEndDate: leaveEndIso,
        usedLeave: usedLeave,
        leaveType: leaveType, // 반차 구분 지으려고 추가
    }; // 서버로 보낼 데이터

    // 데이터 전송
    fetch("/employee/vacAppResult", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(vacationApplicationDTO),
    })
        .then(res => res.json()) // JSON 형태로 응답 파싱
        .then(data => {
            if (data.status === "success") {
                alert(data.message);
                location.reload(); // 페이지 새로고침으로 업데이트된 데이터 표시
            } else {
                alert(data.message);
            }
        })
        .catch(err => console.error("휴가 신청 실패:", err));

    // // 모달 닫기
    // const myModal = document.getElementById("myModal");
    // myModal.style.display = "none";
    // 모달 닫기
    const myModal = new bootstrap.Modal(document.getElementById("vacationModal"), {});
    myModal.hide();
});



/////////////////////////////////////////////
//
// 연장 근무 신청 버튼 클릭 시 동작하는 애
document.getElementById("overtime-button-id").addEventListener("click", () => {
    fetch("/employee/overTimeAppSelect")
        .then(res => res.json())
        .then(data => {
            console.log("결재자 목록 가져왔나~?", data);
            const leaderSelect = document.getElementById("leader");

            leaderSelect.innerHTML = ""; // 옵션 초기화

            // 새 옵션 추가
            data.forEach(item => {
                const option = document.createElement("option");
                option.value = item.id;
                option.innerText = item.name;
                leaderSelect.appendChild(option); // 차일드로 박아 넣음
            });
        })
        .catch(err => console.error("결재자 목록 가져오기 실패:", err));

    // 모달 창 뜨게
    const modalElement = new bootstrap.Modal(document.getElementById("overTimeModal"), {});
    modalElement.show();
});

// 날짜 및 시간 변경 시 동작
const startOverDay = document.getElementById("startOverDay");
const endOverDay = document.getElementById("endOverDay");
const startOverTime = document.getElementById("startOverTime");
const endOverTime = document.getElementById("endOverTime");
const allOverTime = document.getElementById("allOverTime");

// 종료일은 시작일과 같게 강제
startOverDay.addEventListener("change", () => {
    endOverDay.value = startOverDay.value; // 종료일 = 시작일
    endOverDay.setAttribute("min", startOverDay.value); // 종료일 최소값 설정
});

// 연장 근무 시간 계산
[startOverTime, endOverTime].forEach(el => el.addEventListener("change", () => {
    if (startOverTime.value && endOverTime.value) {
        const start = new Date(`1970-01-01T${startOverTime.value}:00`);
        const end = new Date(`1970-01-01T${endOverTime.value}:00`);
        const hours = (end - start) / (1000 * 60 * 60); // 시간 차이 계산

        if (hours > 0) {
            allOverTime.value = hours.toFixed(1); // 총 시간 표시
        } else {
            alert("종료 시간이 시작 시간보다 빠를 수 없습니다.");
            allOverTime.value = "";
        }
    }
}));

// 사유 선택 시 동작
const overTimeReason = document.getElementById("overTimeReason");
const selfReasonInput = document.getElementById("selfReasonInput");

overTimeReason.addEventListener("change", () => {
    if (overTimeReason.value === "selfInput") {
        selfReasonInput.style.display = "block"; // 직접 입력 필드 표시
    } else {
        selfReasonInput.style.display = "none"; // 숨김
        selfReasonInput.value = ""; // 입력값 초기화
    }
});

// 오늘보다 이전 날짜 선택 방지
function preventPastDate(inputId) {
    const today = new Date().toISOString().split("T")[0];
    document.getElementById(inputId).setAttribute("min", today);
}

// 시작일과 종료일 초기화
preventPastDate("startOverDay");
preventPastDate("endOverDay");

preventPastDate("startDay");
preventPastDate("endDay");

// 모달 열릴 때 초기화
document.getElementById("overtime-button-id").addEventListener("click", () => {
    const startDay = document.getElementById("startOverDay");
    const endDay = document.getElementById("endOverDay");
    const startTime = document.getElementById("startOverTime");
    const endTime = document.getElementById("endOverTime");

    // 시작일과 종료일 초기값 설정
    const today = new Date().toISOString().split("T")[0];
    startDay.value = today;
    endDay.value = today;
    endDay.setAttribute("min", today); // 종료일 최소값 추가
    endDay.setAttribute("max", today); // 종료일 최대값 추가 (시작일과 동일)

    // 시작 시간 초기값 설정
    startTime.value = "18:00";

    // 종료 시간 초기값 설정 (1시간 이후)
    endTime.value = "19:00";

    calculateOverTime(); // 시간 계산 초기화
});

// 시작일 변경 시 종료일 강제 동기화
document.getElementById("startOverDay").addEventListener("change", () => {
    const startDay = document.getElementById("startOverDay").value;
    const endDay = document.getElementById("endOverDay");

    if (startDay) {
        endDay.value = startDay; // 종료일 = 시작일로 강제
        endDay.setAttribute("min", startDay); // 종료일 최소값 설정
        endDay.setAttribute("max", startDay); // 종료일 최대값 설정
        calculateOverTime(); // 계산 함수 호출
    }
});

// 종료일 변경 시 유효성 검증 (비정상적인 값 방지)
document.getElementById("endOverDay").addEventListener("change", () => {
    const startDay = document.getElementById("startOverDay").value;
    const endDay = document.getElementById("endOverDay").value;

    if (endDay !== startDay) {
        alert("종료일은 시작일과 같아야 합니다.");
        document.getElementById("endOverDay").value = startDay; // 강제 동기화
    }
    calculateOverTime(); // 계산 함수 호출
});

// 시간 변경 이벤트 핸들러 등록
[document.getElementById("startOverTime"), document.getElementById("endOverTime")].forEach(el =>
    el.addEventListener("change", calculateOverTime)
);

// 총 연장 근무 시간 계산
function calculateOverTime() {
    const startTime = document.getElementById("startOverTime").value;
    const endTime = document.getElementById("endOverTime").value;
    const totalTime = document.getElementById("allOverTime");

    if (startTime && endTime) {
        const start = new Date(`1970-01-01T${startTime}:00`);
        const end = new Date(`1970-01-01T${endTime}:00`);
        const hours = (end - start) / (1000 * 60 * 60); // 시간 계산

        if (hours > 0) {
            totalTime.value = hours.toFixed(1); // 유효한 시간 차이
        } else {
            totalTime.value = ""; // 잘못된 경우
            alert("종료 시간이 시작 시간보다 빠를 수 없습니다.");
        }
    } else {
        totalTime.value = ""; // 값 비우기
    }
}

/// 연장 근무 계획서 작성 후, 제출 누를 때 서버에 저장하는 애
document.getElementById("overTimeApp").addEventListener("click", () => {

    // 입력 데이터 가져오기
    const leaderId = document.getElementById("leader").value; // 담당자 사번
    const startDay = document.getElementById("startOverDay").value; // 시작일
    const startTime = document.getElementById("startOverTime").value; // 시작 시간
    const endDay = document.getElementById("endOverDay").value; // 종료일
    const endTime = document.getElementById("endOverTime").value; // 종료 시간
    const scheduleNote = document.getElementById("overTimeReason").value === "selfInput"
        ? document.getElementById("selfReasonInput").value // 직접 입력값
        : document.getElementById("overTimeReason").value; // 선택된 사유

    const totalHours = document.getElementById("allOverTime").value;

    // 필수 값 체크 (다 입력해야 제출 됨)
    if (!leaderId || !startDay || !startTime || !endDay || !endTime || !scheduleNote) {
        alert("모든 필수 값을 입력해주세요.");
        return;
    }

    // dto로 모음
    const overTimeDTO = {
        id: leaderId, // 담당자 사번 / 얜 나중에 쓸 듯.
        startOverDay: startDay,
        // startOverTime: `${startDay}T${startTime}:00`, // LocalDateTime 형식으로 변환
        scheduleStartDate: `${startDay}T${startTime}:00`, // 일단은 얘만 보냄. 나중에 수정 해야 함.
        // endOverDay: endDay,
        // endOverTime: `${endDay}T${endTime}:00`,
        scheduleEndDate: `${endDay}T${endTime}:00`,
        scheduleNote: scheduleNote, // 메모 (사유)

        totalHours: totalHours,
    };

    console.log("보낼 데이터:", overTimeDTO); // 디버깅용 로그

    // 서버로 데이터 전송
    fetch("/employee/overTimeAppResult", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(overTimeDTO),
    })
        .then((res) => res.json())
        .then((data) => {
            if (data.status === "overTimeAppSuccess") {
                alert(data.message);
                location.reload();
            } else {
                alert(data.message);
            }
        })
        .catch((err) => console.error("연장 근무 신청 실패:", err));
});
