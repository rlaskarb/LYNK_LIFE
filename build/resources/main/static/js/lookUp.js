
// 페이지 입장 시, 전체 데이터 출력

let allEmployees = []; // 데이터를 저장할 변수

// 페이지 로드 시 데이터를 가져와서 출력
document.addEventListener("DOMContentLoaded", () => {
    fetch("/employee/lookUpList")
        .then(res => res.json())
        .then(data => {
            allEmployees = data; // 데이터 저장
            renderTable(allEmployees); // 전체 데이터를 테이블에 출력
            console.log("전체 데이터 불러오기 성공:", allEmployees);
        })
        .catch(err => console.error("전체 데이터 불러오기 실패:", err));
});

// 테이블 데이터를 출력하는 함수
function renderTable(data) {
    const tableBody = document.getElementById("employee-table-body");
    tableBody.innerHTML = ""; // 테이블 초기화

    if (data.length === 0) { // 검색 결과가 없을 때
        const noDataRow = `<tr><td colspan="6">검색 결과가 없습니다.</td></tr>`;
        tableBody.innerHTML = noDataRow;
        return;
    }

    data.forEach(item => {
        const row = document.createElement("tr");

        // 24-12-23 추가 , 얘는 상세 정보 클릭 했을 때만 출력되는 애.
        row.setAttribute("data-join-date",item.humanDTO.joinDate);
        // row.setAttribute("data-image" , item.image);
        row.setAttribute("data-department", item.departmentDTO.depName || "");

        row.innerHTML = ` <!-- 얘는 페이지 들어오면 나오는 애 -->
            <td>${item.id}</td>
            <td>${item.name}</td>
            <td>${item.departmentDTO.depName || "N/A"}</td>
            <td>${item.humanDTO.position}</td>
            <td>${item.humanDTO.employeementStatus}</td>
            <td>${item.humanDTO.phoneNumber}</td>
        `;
        tableBody.appendChild(row);
    });
}

// 조회 기능 (필터링해서 출력)
function performSearch() {
    const type = document.getElementById("lookupSelect").value; // 부서/이름/고용구분 선택
    const keywordInput = document.getElementById("lookupInput");
    const keyword = keywordInput.value.trim(); // 입력 키워드 (trim으로 공백 제거)

    if (!keyword) {
        alert("검색어를 입력해 주세요.");
        return;
    }

    // 조건에 맞게 데이터 필터링
    const filteredEmployees = allEmployees.filter(employee => {
        if (type === "부서명") {
            return employee.departmentDTO.depName.includes(keyword);
        } else if (type === "이름") {
            return employee.name.includes(keyword);
        } else if (type === "고용 구분") {
            return employee.humanDTO.employeementStatus.includes(keyword);
        }
    });

    // 필터링된 데이터를 테이블에 출력
    renderTable(filteredEmployees);

    // 입력 필드 초기화
    keywordInput.value = "";
}

// 버튼 클릭 이벤트
document.getElementById("select-button-id").addEventListener("click", performSearch);

// Enter 키 이벤트 추가 (검색 입력창)
document.getElementById("lookupInput").addEventListener("keyup", (event) => {
    if (event.key === "Enter") {
        performSearch(); // 엔터 키 입력 시 조회 기능 실행
    }
});


// 클릭 했을 때 수정 가능한 모달창 띄우는 거
document.getElementById("employee-table-body").addEventListener("click", (event) => {
    const row = event.target.closest("tr"); // 클릭된 행 가져오기
    if (row) {
        const cells = row.getElementsByTagName("td"); // 클릭한 행 모든 td 가져오기

        // 행의 ID 및 Join Date 가져오기
        const employeeId = cells[0].textContent; // 사번
        const joinDate = row.getAttribute("data-join-date");
        // 24-12-23 추가한 애 / data-join-date 속성 읽기
        // const image = row.getAttribute("data-image");
        const depName = row.getAttribute("data-department") || "";

        // 모달 창에 데이타 삽입
        document.getElementById("editId").value = employeeId;
        document.getElementById("editName").value = cells[1].textContent;
        document.getElementById("editDepNo").value = depName;
        document.getElementById("editPosition").value = cells[3].textContent;
        document.getElementById("editStatus").value = cells[4].textContent;
        document.getElementById("editPhone").value = cells[5].textContent;
        document.getElementById("editJoinDate").value = joinDate || ""; // Join Date 데이터 설정 (없으면 빈 값)
        // document.getElementById("editImage").value = image || "image 없음";

        // 모달 초기화 해야 다른 거 눌렀을 때 수정 버튼 계속 출력됨
        document.querySelectorAll("#myModal .modal-right input, #myModal .modal-right select")
            .forEach(field => field.setAttribute("disabled", "true"));
        document.getElementById("enableEditBtn").style.display = "inline-block"; // 수정 버튼
        document.getElementById("saveChanges").style.display = "none"; // 저장 버튼 숨기기
    }

    // Bootstrap 모달 표시
    const modalElement = new bootstrap.Modal(document.getElementById("myModal"), {});
    modalElement.show(); // 모달 열기
});

// 최초 모달창 켜졌을 때 저장버튼 숨겨버리기 (없어도 숨겨짐)
// document.addEventListener("DOMContentLoaded" , () => {
//     document.getElementById("saveChanges").style.display = "none";
// })

// 수정 버튼 눌러야 수정할 수 있게
document.getElementById("enableEditBtn").addEventListener("click" , () => {

    document.querySelectorAll("#myModal .modal-right input, #myModal .modal-right select")
        .forEach(field => {
            if (field.id !== "editId") { // 수정버튼 눌러도 사번만 disabled 안 풀리게
                field.removeAttribute("disabled");
            }
        });

    // 수정버튼 숨기고 저장 버튼 표시하기
    document.getElementById("enableEditBtn").style.display = "none";
    document.getElementById("saveChanges").style.display = "inline-block";
});


// 클릭 시 수정 가능 모달
// 직원 테이블에서 행 클릭 이벤트
document.getElementById("saveChanges").addEventListener("click", () => {
    const updatedEmployee = {
        image: document.getElementById("editImage").value,
        id: document.getElementById("editId").value,
        name: document.getElementById("editName").value,
        depNo: document.getElementById("editDepNo").value, // depNo보내기, value값으로 보낸다
        humanDTO: { // HumanDTO 객체를 포함
            position: document.getElementById("editPosition").value,
            employeementStatus: document.getElementById("editStatus").value,
            phoneNumber: document.getElementById("editPhone").value,
            joinDate: document.getElementById("editJoinDate").value // 24-12-23 입사 일자 추가
        }
    };

    // 서버에 저장하는 로직
    console.log("전송할 데이터:", updatedEmployee);

    fetch("/employee/modify", {
        method: "POST", // HTTP 메서드
        headers: {
            "Content-Type": "application/json" // JSON 형식으로 데이터 전송
        },
        body: JSON.stringify(updatedEmployee) // 객체를 JSON 문자열로 변환
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
        .catch(err => console.error("직원 수정 실패:", err))
        .finally(() => {
            const myModal = document.getElementById("myModal");
            myModal.style.display = "none"; // 서버 응답하고 모달 닫기
        })
});