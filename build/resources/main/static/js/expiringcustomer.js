document.addEventListener("DOMContentLoaded", () => {
    // 조회 버튼 이벤트 설정
    const nameSearchButton = document.getElementById("nameSearchButton");
    const employeeSearchButton = document.getElementById("employeeSearchButton");

    if (nameSearchButton) {
        nameSearchButton.addEventListener("click", async () => {
            const name = document.getElementById("nameSearch").value;
            if (name) await fetchData({ customerName: name });
            console.log("계약자", name);
        });
    }

    if (employeeSearchButton) {
        employeeSearchButton.addEventListener("click", async () => {
            const employee = document.getElementById("employeeSearch").value;
            if (employee) await fetchData({ employeeName: employee });
            console.log("설계사", employee);
        });
    }
});

// 월별 조회 드롭다운 이벤트
const monthDropdown = document.getElementById("monthDropdown");
monthDropdown?.addEventListener("change", async () => {
    const selectedMonth = monthDropdown.value;
    if (selectedMonth) {
        const year = new Date().getFullYear();
        await fetchData({ year, month: selectedMonth });
    }
});

// 월별 데이터 요청 함수 추가
async function fetchData(params) {
    const filteredParams = Object.fromEntries(
        Object.entries(params).filter(([_, v]) => v != null && v !== "")
    ); // null 또는 빈 값 제거

    const query = new URLSearchParams(filteredParams).toString();
    const url = `/db/expiringcustomer/search?${query}`; // API 엔드포인트 확인

    console.log("요청 URL:", url);

    try {
        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (!response.ok) {
            throw new Error(`HTTP 상태 코드: ${response.status}, 메시지: ${response.statusText}`);
        }

        const data = await response.json();
        console.log("응답 데이터:", data);
        updateTable(data);
    } catch (error) {
        console.error("데이터 조회 오류:", error);
    }
}

// 테이블 클릭 이벤트 위임
document.getElementById("resultTable").addEventListener("click", (event) => {
    const targetRow = event.target.closest("tr"); // 클릭된 요소의 부모 행 찾기
    if (!targetRow || !targetRow.dataset.contractNo) return; // 유효한 행인지 확인

    // contractNo를 가져와 상세 페이지로 이동
    const contractNo = targetRow.dataset.contractNo;
    window.location.href = `/db/contract/details/${contractNo}`;
});


// 결과 테이블 업데이트
function updateTable(data) {
    const tableBody = document.getElementById("resultTable");
    tableBody.innerHTML = "";

    if (!data.length) {
        tableBody.innerHTML = "<tr><td colspan='8'>조회 결과가 없습니다.</td></tr>";
        return;
    }

    data.forEach(item => {
        let expiringDate;
        if (item.contractDate && item.contractDuration) {
            const contractDate = new Date(item.contractDate);
            const calculatedExpiringDate = new Date(contractDate);
            calculatedExpiringDate.setDate(
                contractDate.getDate() + (item.contractDuration * 365)
            );
            expiringDate = calculatedExpiringDate.toISOString().split('T')[0];
        } else {
            expiringDate = item.expiringDate || 'N/A';
        }

        const row = `
            <tr data-contract-no="${item.contractNo || ""}">
                <td>${item.contractMngNo || "N/A"}</td>
                <td>${item.customerName || "N/A"} / ${item.insuredName || "N/A"}</td>
                <td>${item.insuranceCompany || "N/A"}</td>
                <td>${item.productName || "N/A"}</td>
                 <td>${expiringDate}</td>
                <td>${item.contractNo || "N/A"}</td>
               <td>${item.departmentName || "N/A"} / ${item.employeeName || "N/A"} / ${item.employeeNo || "N/A"}</td>
            </tr>
        `;

        tableBody.innerHTML += row;
    });
}
// =====================================================================================================

function handleMonthChange() {
    const selectedMonth = parseInt(document.getElementById("monthDropdown").value);
    if (selectedMonth) {
        const year = new Date().getFullYear();

        // 시작일: YYYY-MM-01
        const startDate = `${year}-${String(selectedMonth).padStart(2, '0')}-01`;

        // 마지막날 계산: new Date(year, month, 0) = 해당 월의 마지막 날
        const lastDay = new Date(year, selectedMonth, 0).getDate();
        const endDate = `${year}-${String(selectedMonth).padStart(2, '0')}-${lastDay}`;

        fetchData({ startDate, endDate });
    }
}
