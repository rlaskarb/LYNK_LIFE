document.addEventListener('DOMContentLoaded', () => {
    // 검색 버튼 이벤트 리스너 추가
    document.getElementById('nameSearchButton').addEventListener('click', () => {
        const name = document.getElementById('nameSearch').value.trim();
        fetchContracts({ name });
    });

    document.getElementById('plannerSearchButton').addEventListener('click', () => {
        const plannerName = document.getElementById('plannerSearch').value.trim();
        fetchContracts({ plannerName });
    });

    // Fetch API로 데이터 가져오기 및 테이블 렌더링
    function fetchContracts(params) {
        const query = new URLSearchParams(params).toString();
        const url = `/db/inquiry/json?${query}`;

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                renderTable(data);
            })
            .catch(error => {
                console.error('Fetch error:', error); // 네트워크 또는 응답 관련 에러 처리
            });
    }

    // 테이블 렌더링 함수
    function renderTable(data) {
        const tbody = document.getElementById('contractTableBody');
        if (!Array.isArray(data)) {
            console.error('Unexpected data format:', data);
            tbody.innerHTML = '<tr><td colspan="7">No data available</td></tr>';
            return;
        }

        // 데이터로 테이블 생성
        tbody.innerHTML = data.map(contract => `
            <tr data-contract-no="${contract.contractNo}">
                <td>${contract.contractMngNo || '-'}</td>
                <td>${contract.contractName || '-'} / ${contract.insuredName || '-'}</td>
                <td>${contract.insuranceCompany || '-'}</td>
                <td>${contract.productName || '-'}</td>
                <td>${contract.contractNo || '-'}</td>
                <td>${contract.departmentName || '-'} / ${contract.employeeName || '-'} / ${contract.employeeNo || '-'}</td>
            </tr>
        `).join('');

        // 각 행에 클릭 이벤트 바인딩
        const rows = tbody.querySelectorAll('tr');
        rows.forEach(row => {
            row.addEventListener('click', () => {
                const contractNo = row.dataset.contractNo;
                redirectToContract(contractNo);
            });
        });
    }

    // 특정 계약으로 리다이렉트
    function redirectToContract(contractNo) {
        console.log("Redirecting to contract:", contractNo);
        if (contractNo) {
            window.location.href = `/db/contract/details/${contractNo}`;
        } else {
            alert("계약 정보를 찾을 수 없습니다.");
        }
    }

    // 초기 데이터 로드
    fetchContracts({
        name: 'test',
        plannerName: 'John'
    });
});
