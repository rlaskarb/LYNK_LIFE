function formatPaymentDay(paymentDay) {
    switch(paymentDay) {
        case 1:
            return "일시납";
        case 5:
            return "5일";
        case 10:
            return "10일";
        case 15:
            return "15일";
        case 20:
            return "20일";
        case 25:
            return "25일";
        default:
            return "잘못된 납입일";
    }
}

// 납입일 변환 및 출력
document.addEventListener("DOMContentLoaded", function () {
    const paymentDayElement = document.getElementById("paymentDay");
    if (paymentDayElement) {
        const rawPaymentDay = parseInt(paymentDayElement.textContent.trim(), 10); // 원래 값 가져오기
        const formattedPaymentDay = formatPaymentDay(rawPaymentDay);
        paymentDayElement.textContent = formattedPaymentDay; // 변환된 값 설정
    }
});
