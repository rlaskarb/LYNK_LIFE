//ps 사원 조회 자바스크립트

// 사원 검색 버튼 클릭 이벤트 핸들러
// 사원 모달 열기 및 서버에서 데이터 요청
document.getElementById('searchEmployeeBtn').addEventListener('click', () => {
    // 모달 열기
    const modal = new bootstrap.Modal(document.getElementById('employeeModal'));
    modal.show();

    // 서버에서 사원 데이터 요청
    fetch('/db/employees')
        .then(response => response.json()) // 응답 데이터를 JSON으로 파싱
        .then(data => renderEmployeeList(data)) // 데이터 렌더링 함수 호출
        .catch(error => console.error('사원 데이터를 가져오는 중 오류 발생:', error)); // 오류 처리
});

// 검색 입력 필드에 따라 사원 목록 필터링
document.getElementById('employeeSearch').addEventListener('input', function () {
    const searchValue = this.value.toLowerCase(); // 입력값 소문자로 변환
    const employees = document.querySelectorAll('#employeeList li'); // 사원 목록 항목들

    // 검색 조건에 맞는 항목만 표시
    employees.forEach(employee => {
        const text = employee.textContent.toLowerCase();
        employee.style.display = text.includes(searchValue) ? '' : 'none';
    });
});

// 사원 데이터를 렌더링하는 함수
function renderEmployeeList(employeeData) {
    const list = document.getElementById('employeeList');
    list.innerHTML = ''; // 기존 데이터 초기화

    if (employeeData.length === 0) {
        list.innerHTML = '<li class="list-group-item">검색 결과가 없습니다.</li>'; // 검색 결과 없을 시 메시지 표시
        return;
    }

    // 사원 데이터를 기반으로 목록 생성
    employeeData.forEach(employee => {
        const li = document.createElement('li');
        li.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'align-items-center');
        li.textContent = `${employee.employeeName} (${employee.employeeNo})`;
        li.addEventListener('click', () => {
            document.getElementById('employeeName').value = employee.employeeName; // 선택한 사원 이름 설정
            document.getElementById('employeeNo').value = employee.employeeNo; // 선택한 사원 사번 설정
            const modal = bootstrap.Modal.getInstance(document.getElementById('employeeModal'));
            modal.hide(); // 모달 닫기
        });
        list.appendChild(li);
    });
}



//=====================================================================================================================

//ps 고객 조회 버튼 클릭 시 모달 열기

// 고객 검색 버튼 클릭 이벤트 핸들러
document.getElementById('searchCustomerBtn').addEventListener('click', () => {
    const modal = new bootstrap.Modal(document.getElementById('customerModal'));
    modal.show();

    // 서버에서 고객 데이터 요청
    fetch('/db/customers')
        .then(response => response.json()) // 응답 데이터를 JSON으로 파싱
        .then(data => renderCustomerList(data)) // 데이터 렌더링 함수 호출
        .catch(error => console.error('고객 데이터를 가져오는 중 오류 발생:', error)); // 오류 처리
});

// 검색 입력 필드에 따라 고객 목록 필터링
document.getElementById('customerSearch').addEventListener('input', function () {
    const searchValue = this.value.toLowerCase(); // 입력값 소문자로 변환
    const customers = document.querySelectorAll('#customerList li'); // 고객 목록 항목들

    // 검색 조건에 맞는 항목만 표시
    customers.forEach(customer => {
        const text = customer.textContent.toLowerCase();
        customer.style.display = text.includes(searchValue) ? '' : 'none';
    });
});

// 고객 데이터를 렌더링하는 함수
function renderCustomerList(customerData) {
    const list = document.getElementById('customerList');
    list.innerHTML = ''; // 기존 데이터 초기화

    if (customerData.length === 0) {
        list.innerHTML = '<li class="list-group-item">검색 결과가 없습니다.</li>'; // 검색 결과 없을 시 메시지 표시
        return;
    }


    // 고객 데이터를 기반으로 목록 생성
    customerData.forEach(customer => {
        const li = document.createElement('li');
        li.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'align-items-center');
        li.textContent = `${customer.customerName} (${customer.customerNo})`;

        li.addEventListener('click', () => {
            document.getElementById('customerName').value = customer.customerName; // 선택한 고객 이름 설정
            document.getElementById('customerSsn').value = customer.customerSsn; // 선택한 고객 주민번호 설정
            document.getElementById('customerEmail').value = customer.customerEmail; // 선택한 고객 이메일 설정
            document.getElementById('customerMobile').value = customer.customerMobile; // 선택한 고객 전화번호 설정
            document.getElementById('customerNo').value = customer.customerNo; // 선택한 고객 번호 설정


            const modal = bootstrap.Modal.getInstance(document.getElementById('customerModal'));
            modal.hide(); // 모달 닫기
        });


        li.addEventListener('click', () => {
            const customerSsnField = document.getElementById('customerSsn');
            const customerNoField = document.getElementById('customerNo');


            if (customerSsnField) {
                customerSSnField.value = customer.customerSsn; // 고객 주민번호 설정
            }


            if(customerNoField){
                customerNoField.value = customer.customerNo // 숨겨진 필드에 고객번호 설정
                console.log("Hidden customerNo set :", customer, customerNo);
            }

            const modal = bootstrap.Modal.getInstance(document.getElementById('customerModal'));
            if (modal) {
                modal.hide(); // 모달 닫기
            }
        });
        list.appendChild(li);
    });
}

//======================================================================================================================

// 보험 회사 드롭다운 데이터를 채우는 함수
document.addEventListener('DOMContentLoaded', function () {
    fetch('/db/insuranceCodes')
        .then(response => response.json())
        .then(data => populateInsuranceDropdown(data)) // 데이터 렌더링 함수 호출
        .catch(error => console.error('보험 회사 데이터를 가져오는 중 오류 발생:', error)); // 오류 처리
});

// 보험 회사 드롭다운 데이터 렌더링 함수
function populateInsuranceDropdown(codes) {
    const dropdown = document.getElementById('insuranceCode');
    dropdown.innerHTML = '<option value="">보험회사명을 선택하세요</option>'; // 기본 옵션 추가
    codes.forEach(code => {
        const option = document.createElement('option');
        option.value = code.code; // 보험 회사 코드 설정
        option.textContent = `${code.name}`; // 보험 회사 이름 설정
        dropdown.appendChild(option);
    });
}

//======================================================================================================================

let modalInstance = null;

// 상품 검색 버튼 클릭 이벤트 핸들러
document.getElementById('searchProductBtn').addEventListener('click', () => {
    const insuranceCodeElement = document.getElementById('insuranceCode');
    const modalElement = document.getElementById('productModal');
    const productList = document.getElementById('productList');

    if (!insuranceCodeElement || !modalElement || !productList) {
        console.error('필수 HTML 요소가 누락되었습니다.');
        alert('상품 조회를 위해 필요한 요소가 없습니다. 관리자에게 문의하세요.');
        return;
    }

    //ps 보험 코드값 확인
    const insuranceCode = insuranceCodeElement.value.trim();
    if (!insuranceCode) {
        alert('보험 코드를 입력하거나 선택하세요.');
        return;
    }

    //ps 모달창 생성 및 표시
    if (!modalInstance) {
        modalInstance = new bootstrap.Modal(modalElement);
    }
    modalInstance.show();


    // 상품 데이터 요청
    fetch(`/db/products?insuranceCode=${insuranceCode}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`서버 응답 오류: ${response.status}`);
            }
            return response.json();

            // 상품 목록 렌더링
        })
        .then(data => {
            renderProductList(data);

            //오류처리
        })
        .catch(error => {
            console.error('상품 데이터를 가져오는 중 오류 발생:', error);
            productList.innerHTML = '<li class="list-group-item text-danger">상품 데이터를 로드하는 중 문제가 발생했습니다. 관리자에게 문의하세요.</li>';
        });
});

// ===================================================================================================================

// 상품 데이터를 렌더링하는 함수
function renderProductList(productData) {
    const productList = document.getElementById('productList');

    productList.innerHTML = ''; // 기존 데이터 초기화

    if (!productData || productData.length === 0) {
        productList.innerHTML = '<li class="list-group-item">검색 결과가 없습니다.</li>';
        return;
    }

    // 검색결과가 없는 경우 처리
    productData.forEach(product => {
        // 데이터 유효성 검증
        const productName = product.productName || '알 수 없음';
        const productNo = product.productNo || '없음';

        // 리스트 항목 생성
        const li = document.createElement('li');
        li.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'align-items-center');
        li.textContent = `${productName}/${productNo}`;

        // 상품 선택 이벤트 등록
        li.addEventListener('click', () => {
            // 상품 이름과 상품 번호 필드를 가져옴
            const productNameField = document.getElementById('productName');
            const productNoField = document.getElementById('productNo');

            // 데이터 유효성 검증 및 설정
            if (productNameField && product.productName) {
                productNameField.value = `${product.productName} (${product.productNo})`; // 상품 이름과 번호 표시
            } else {
                console.error("productNameField is missing or productName is invalid.");
            }

            if (productNoField && product.productNo) {
                productNoField.value = product.productNo; // 상품 번호 설정
                console.log("Selected productNo:", product.productNo); // 설정된 값 로그 출력
            } else {
                console.error("productNoField is missing or productNo is invalid.");
            }

            // 모달 창 닫기
            const modal = bootstrap.Modal.getInstance(document.getElementById('productModal'));
            if (modal) {
                modal.hide();
            }

        });
        // 리스트에 항목 추가
        productList.appendChild(li);

    });
}



//======================================================================================================================

// 등록 버튼 클릭으로 db 저장 생성하기

document.getElementById('registerContractBtn').addEventListener('click', function () {
    const confirmModal = new bootstrap.Modal(document.getElementById('confirmModal'));
    confirmModal.show();

    const confirmYesButton = document.getElementById('confirmYes');
    const confirmNoButton = document.getElementById('confirmNo');

    const handleYesClick = function () {
        const contractData = {
            customerSsn: document.getElementById('customerSsn').value,
            contractNo: document.getElementById('contractNo').value,
            contractDate: document.getElementById('contractDate').value,
            contractDuration: document.getElementById('contractDuration').value,
            eachPayment: document.getElementById('eachPayment').value,
            basicPayWith: document.getElementById('paymentMethod').value,
            paymentTerm: document.getElementById('paymentTerm').value,
            paymentDay: document.getElementById('paymentDay').value,
            insuredName: document.getElementById('insuredName').value,
            insuredSsn: document.getElementById('insuredSsn').value,
            otherMatters: document.getElementById('otherMatters').value,
            productNo: document.getElementById('productNo').value,
            customerNo: document.getElementById('customerNo').value,
            employeeNo: document.getElementById('employeeNo').value
        };

        console.log("js에 넘어온 data", contractData);

        // 서버로 데이터 전송
        fetch('/db/contract', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(contractData)
        })
            .then(response => response.json())
            .then(data => {
                confirmModal.hide(); // 확인 모달 닫기
                if (data.status === 'success') {
                    showCompletionModal(data.message); // 성공 메시지 모달 표시
                } else {
                    showErrorModal(data.message); // 오류 메시지 모달 표시
                }
            })
            .catch(error => {
                console.error('Error saving contract:', error);
                showErrorModal('계약 저장 중 오류가 발생했습니다.');
            });

        confirmYesButton.removeEventListener('click', handleYesClick);
        confirmNoButton.removeEventListener('click', handleNoClick);
    };


    const handleNoClick = function () {
        confirmModal.hide();
        confirmYesButton.removeEventListener('click', handleYesClick);
        confirmNoButton.removeEventListener('click', handleNoClick);
    };

    confirmYesButton.addEventListener('click', handleYesClick);
    confirmNoButton.addEventListener('click', handleNoClick);
});

// 성공 메시지 모달 표시 함수
function showCompletionModal(message) {
    const resultModal = new bootstrap.Modal(document.getElementById('resultModal'));
    const resultMessage = document.getElementById('resultMessage');
    resultMessage.textContent = message;

    resultModal.show();

    // "확인" 버튼 클릭 시 이전 페이지로 돌아가기
    document.getElementById('completionConfirm').addEventListener('click', function () {
        resultModal.hide();
        window.history.back(); // 이전 페이지로 돌아가기
    },{once:true});
}

// 오류 메시지 모달 표시 함수
function showErrorModal(message) {
    const errorModal = new bootstrap.Modal(document.getElementById('errorModal'));
    const errorMessage = document.getElementById('errorMessage');
    errorMessage.textContent = message;

    errorModal.show();
}
