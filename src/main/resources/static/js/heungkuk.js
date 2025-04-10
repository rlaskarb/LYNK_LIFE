/*  등록 버튼을 클릭했을 때 실행되는 함수 입력된 데이터로 상품을 등록하며, 중복된 상품이 있는 경우 경고 메시지를 표시 */

async function registerProduct() {
    // 폼 데이터 가져오기
    const formData = {
        productCategory: 34,
        productNo: document.getElementById('productNo').value, // 상품번호
        productName: document.getElementById('productName').value, // 상품이름
    };

    // 입력값 검증: 상품 번호와 이름이 입력되지 않았을 경우 알림창
    if (!formData.productCategory || !formData.productNo || !formData.productName) {
        alert('모든 필드를 입력해주세요!');
        return;
    }

    // 중복 확인을 위해 서버에서 기존 상품 목록을 가져옴
    const response = await fetch('/db/heungkuk/products');
    if (!response.ok) {
        console.error('Failed to fetch products for duplicate check:', response.statusText);
        return;
    }

    const products = await response.json();

    // 중복된 상품 확인 (상품 번호 또는 이름이 같은 경우)
    const isDuplicate = products.some(product =>
        product.productNo === formData.productNo || product.productName === formData.productName
    );

    // 중복된 경우 경고 모달 표시
    if (isDuplicate) {
        const duplicateModal = new bootstrap.Modal(document.getElementById('duplicateModal'));
        duplicateModal.show();
        return;
    }

    // 등록 확인 모달 표시
    const modal = new bootstrap.Modal(document.getElementById('confirmModal'));
    const confirmButton = document.getElementById('confirmRegister');


    // 확인 버튼 클릭 시 데이터 저장 요청
    confirmButton.onclick = async () => {
        const saveResponse = await fetch('/db/heungkuk', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(formData),
        });

        if (!saveResponse.ok) {
            console.error('Failed to register product:', saveResponse.statusText);
            return;
        }
        console.log('Product registered successfully');
        modal.hide();
        loadProducts(); // 등록 후 상품 목록 갱신
    };

    modal.show();}



async function loadProducts() {
    const response = await fetch('/db/heungkuk/products'); // 서버에서 상품 목록 가져오기
    if (!response.ok) {
        console.error('Failed to load products:', response.statusText);
        return;
    }


    const filterCategory = 34;
    const products = await response.json();


    const filteredProducts = products.filter(product => product.productCategory === filterCategory);

    const productList = document.getElementById('productList'); // 상품 목록을 표시할 html 요소
    productList.innerHTML = '';


    // 필터링된 상품 목록을 화면에 추가
    filteredProducts.forEach(product => {
        const li = document.createElement('li');
        li.innerHTML = `보험회사코드: ${product.productCategory}, 
                            상품번호: ${product.productNo}, 
                            상품이름: ${product.productName}
                            <button data-product-no="${product.productNo}" style="color: darkred; border: 0px solid darkred; border-radius: 100px; background-color: rgba(0,0,0,0); font-size: 23px;">x</button> `;

        productList.appendChild(li);
    });
}

// 삭제 버튼 클릭 이벤트 처리 (Event Delegation)
document.getElementById('productList').addEventListener('click', (event) => {
    if (event.target.tagName === 'BUTTON') {
        const productNo = event.target.getAttribute('data-product-no');
        deleteProduct(productNo);
    }
});



/* 상품 삭제 기능  *삭제 버튼 클릭 시 호출 */
async function deleteProduct(productNo) {
    const confirmDelete = confirm('정말로 삭제하시겠습니까?');
    if (!confirmDelete) return;

    try{
        console.log(`Requesting DELETE for: /db/heungkuk/${productNo}`);
        const response = await fetch(`/db/heungkuk/${productNo}`, {
            method: 'DELETE',
        });


        if (!response.ok) {
            console.error('Failed to delete product:', response.statusText);
            return;
        }


        console.log('Product deleted successfully');

        loadProducts(); // 삭제 후 상품 목록 갱신


    } catch (error) {

        console.error(error);

    }

}


// 페이지 로드 시 상품 목록 초기화
loadProducts();
