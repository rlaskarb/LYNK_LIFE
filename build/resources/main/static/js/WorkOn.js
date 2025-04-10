document.addEventListener('DOMContentLoaded', () => {
    const outsideOnButton = document.getElementById('outsideOnButton');
    const outsideOffButton = document.getElementById('outsideOffButton');
    const workOnButton = document.getElementById('workOnButton');
    const workOffButton = document.getElementById('workOffButton');
    const confirmationModal = document.getElementById('confirmationModal');
    const modalMessage = document.getElementById('modalMessage');
    const confirmButton = document.getElementById('confirmButton');
    const cancelButton = document.getElementById('cancelButton');

    // 버튼을 토글하는 함수
    const toggleButtons = () => {
        const outsideOnDisplay = getComputedStyle(outsideOnButton).display;
        const outsideOffDisplay = getComputedStyle(outsideOffButton).display;

        outsideOnButton.style.display = outsideOnDisplay === 'none' ? 'inline-block' : 'none';
        outsideOffButton.style.display = outsideOffDisplay === 'none' ? 'inline-block' : 'none';
    };

    // 모달을 띄우고 확인 버튼 동작을 설정하는 함수
    const showModal = (message, action) => {
        modalMessage.textContent = message;  // 메시지 업데이트
        confirmationModal.style.display = 'flex';  // 모달 보이기

        // 확인 버튼 클릭 시 실행할 동작 설정
        confirmButton.onclick = () => {
            action();  // 전달된 액션 실행
            confirmationModal.style.display = 'none';  // 모달 숨기기
        };

        // 취소 버튼 클릭 시 모달 숨기기
        cancelButton.onclick = () => {
            confirmationModal.style.display = 'none';  // 모달 숨기기
        };
    };

    // 성공 메시지 표시
    const showSuccessMessage = (message) => {
        showModal(message, () => {
            // 성공 후 추가적인 동작이 필요 없을 때
            console.log(message);
        });
    };

    // 출근 버튼 클릭
    workOnButton.addEventListener('click', () => {
        showModal('출근하시겠습니까?', () => {
            const now = new Date();
            const workOn = now.toISOString();
            const workDate = now.toISOString().slice(0, 10);

            fetch('/work-on', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    empNo: sessionStorage.getItem('empNo'),
                    workOn: workOn,
                    workDate: workDate
                })
            })
                .then(response => response.json())
                .then(data => {
                if (data.success) {
                        showSuccessMessage("출근 처리되었습니다.");
                    } else {
                        showSuccessMessage("출근 처리 실패");
                    }
                })
                .catch(error => console.error("Error:", error));
        });
    });

    // 퇴근 버튼 클릭
    workOffButton.addEventListener('click', () => {
        showModal('퇴근하시겠습니까?', () => {
            const now = new Date();
            const workOff = now.toISOString();
            const workDate = now.toISOString().slice(0, 10);

            fetch('/work-off', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    empNo: sessionStorage.getItem('empNo'),
                    workOff: workOff,
                    workDate: workDate
                })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        showSuccessMessage("퇴근 처리되었습니다.");
                    } else {
                        showSuccessMessage("퇴근 처리 실패");
                    }
                })
                .catch(error => console.error("Error:", error));
        });
    });

    // 외근 시작 버튼 클릭
    outsideOnButton.addEventListener('click', () => {
        showModal('외근 시작하시겠습니까?', () => {
            toggleButtons();
            const now = new Date();
            const workOnOutside = now.toISOString();
            const workDate = now.toISOString().slice(0, 10);

            fetch('/work-outside-on', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    empNo: sessionStorage.getItem('empNo'),
                    workOnOutside: workOnOutside,
                    workDate: workDate,
                })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        showSuccessMessage("외근 시작 처리 성공");
                        toggleButtons();
                    } else {
                        showSuccessMessage("처리 실패");
                    }
                })
                .catch(error => console.error("Error:", error));
        });
    });

    // 외근 종료 버튼 클릭
    outsideOffButton.addEventListener('click', () => {
        showModal('외근 종료하시겠습니까?', () => {
            toggleButtons();
            const now = new Date();
            const workOffOutside = now.toISOString();
            const workDate = now.toISOString().slice(0, 10);

            fetch('/work-outside-off', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    empNo: sessionStorage.getItem('empNo'),
                    workOffOutside: workOffOutside,
                    workDate: workDate,
                })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        showSuccessMessage("외근 종료 처리 성공");
                        toggleButtons();
                    } else {
                        showSuccessMessage("처리 실패");
                    }
                })
                .catch(error => console.error("Error:", error));
        });
    });
});