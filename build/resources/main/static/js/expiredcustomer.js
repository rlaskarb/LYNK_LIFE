// Spring Boot API에서 데이터 가져오기
async function fetchContract() {
    try {
        // API 호출
        const response = await fetch('/db/api/expiring-contracts'); // URL을 필요에 따라 조정하세요


        if(!response.ok){
            console.log(`API 호출 실패 : ${response.status} ${response.statusText}`);
            throw new Error(`서버 에러발생 :  ${response.status}`);
        }
        const contract = await response.json(); // JSON 형식으로 응답 받기


        const contractList = document.getElementById('contract-list');
        contractList.innerHTML='<p>로딩중....</p>';

        // 기존 내용을 모두 비우기
        contractList.innerHTML = '';

        //데이터가 없을경우
        if(!contract || contract.length === 0 ){
            contractList.innerHTML = '<p>만기 도래 고객이 없습니다.</p>';
            return;
        }



        // 계약 목록을 순회하면서 카드 생성
        contract.forEach(contract => {
            const contractCard = document.createElement('div'); // 새로운 div 생성
            contractCard.classList.add('contract-card'); // 클래스 추가

            // 보험회사 이름 매핑
            const insuranceCompanyName = contract.insuranceCompany;


            // 카드 내용 추가
            contractCard.innerHTML = `
                <p>${insuranceCompanyName}</p>
                <p>${contract.productName}</p>
                <p> 계약자 : ${contract.customerName}</p>
                <p>만기일자 : ${new Date(contract.expiringDate).toLocaleDateString()}</p>
            `;

            // contractNo를 data-* 속성으로 추가
            contractCard.dataset.contractNo = contract.contractNo;

            // 클릭 시 상세 페이지로 이동
            contractCard.addEventListener('click', () => {
                // 컨트롤러의 경로 호출
                window.location.href = `/db/contract/details/${contract.contractNo}`;
            });

            // 계약 카드를 목록에 추가
            contractList.appendChild(contractCard);
        });

    } catch (error) {
        const contractList = document.getElementById('contract-list');
        contractList.innerHTML = '<p>데이터를 불러오는 중 문제가 발생했습니다.</p>';
        console.error('계약 데이터를 가져오는 중 에러 발생:', error);
    }
}

// 페이지 로드 시 계약 데이터를 가져오기
window.onload = fetchContract;