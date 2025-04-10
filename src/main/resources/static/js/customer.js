async function submitForm() {
    const customerData = {
        customerName: document.getElementById('customerName').value,
        customerSsn: document.getElementById('customerSsn').value,
        customerMobile: document.getElementById('customerMobile').value,
        customerAddr: document.getElementById('customerAddr').value,
        customerEmail: document.getElementById('customerEmail').value
    };

   // 모달 창 표시
    const confirmModal =document.getElementById('confirmModal');
    confirmModal.style.display='block';

    document.getElementById('confirmYes').onclick= async function(){
        confirmModal.style.display = 'none'; // 모달 숨기기
            // 데이터 저장 요청
        await fetch('/db/customer', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(customerData)
        });
        //저장 완료 메시지 표시
        alert('저장되었습니다.');
        fetchCustomerList();
    };
    document.getElementById('confirmNo').onclick = function(){
        confirmModal.style.display = 'none'; // 모달숨기기
    };
}

// ================================================================================

async function fetchCustomerList() {
    const response = await fetch('/db/customer/list');
    const customers = await response.json();

    const tableBody = document.querySelector("#customerTable tbody");
    tableBody.innerHTML = "";
    customers.forEach(customer => {
        const row = `
            <tr>
                <td>${customer.customerNo}</td>
                <td>${customer.customerName}</td>
                <td>${customer.customerSsn}</td>
                <td>${customer.customerMobile}</td>
                <td style="text-align: start;">${customer.customerAddr}</td>
                <td><button onclick="deleteCustomer(${customer.customerNo})" style="color: darkred; border: 1px solid darkred; border-radius: 100px; background-color: rgba(0,0,0,0); font-size: 12px; font-weight: bolder;">X</button></td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}
async function deleteCustomer(customerNo) {
    await fetch(`/db/customer/delete/${customerNo}`, { method: 'DELETE' });
    fetchCustomerList();
}

// Initial load
fetchCustomerList();