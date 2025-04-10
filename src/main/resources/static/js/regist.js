
document.addEventListener("DOMContentLoaded", () => {
    // 기존 폼 관련 데이터 처리 코드
    const rows = document.querySelectorAll(".clickable-row");

    rows.forEach(row => {
        row.addEventListener("click", () => {
            const picture = row.getAttribute("data-picture");
            const id = row.getAttribute("data-id");
            const name = row.getAttribute("data-name");
            const department = row.getAttribute("data-department");
            const email = row.getAttribute("data-email");

            // 사진 표시
            const photoElement = document.getElementById("formPhoto");
            if (picture) {
                // `img` 태그를 동적으로 추가
                photoElement.innerHTML = `<img src="${picture}" alt="사진" style="width: 100%; height: 100%; object-fit: cover; border-radius: 10px;">`;
            } else {
                // 사진이 없는 경우 기본 텍스트 유지
                photoElement.textContent = "사진 없음";
            }

            document.getElementById("formId").value = id || "";
            document.getElementById("formName").value = name || "";
            document.getElementById("formDepartment").value = department || "";
            document.getElementById("formEmail").value = email || "";
        });
    });
});

document.addEventListener("DOMContentLoaded", () => {
    console.log("DOMContentLoaded event fired."); // 기본 확인용 로그

    // DOM 변경 관찰
    const targetNode = document.body;
    const observer = new MutationObserver(() => {
        const successMessageElement = document.getElementById("successMessage");

        if (successMessageElement) {
            console.log("Success message element found:", successMessageElement.textContent.trim());
            const successMessage = successMessageElement.textContent.trim();

            if (successMessage) {
                console.log("Success message present:", successMessage);

                const modalElement = document.getElementById("myModal");
                if (modalElement) {
                    console.log("Modal element found.");
                    const modalMessage = modalElement.querySelector(".modal-body");
                    if (modalMessage) {
                        modalMessage.textContent = successMessage;
                        const myModal = new bootstrap.Modal(modalElement);
                        myModal.show();
                    } else {
                        console.error("Modal message body not found.");
                    }
                } else {
                    console.error("Modal element not found.");
                }

                // 관찰 중지
                observer.disconnect();
            }
        }
    });
    observer.observe(targetNode, { childList: true, subtree: true });
});

document.addEventListener("DOMContentLoaded", () => {
    // 취소 버튼 이벤트 추가
    const cancelButton = document.querySelector(".cancle-button");
    cancelButton.addEventListener("click", () => {
        // 사진 초기화
        const photoElement = document.getElementById("formPhoto");
        photoElement.innerHTML = "사 진"; // 초기 텍스트로 복구

        // 입력 필드 초기화
        document.getElementById("formId").value = "";
        document.getElementById("formName").value = "";
        document.getElementById("formDepartment").value = "";
        document.getElementById("formEmail").value = "";
        // 추가 필드 초기화 필요시 아래 항목들 추가
        document.getElementById("position").value = "인턴"; // 초기값 설정
    });
});
