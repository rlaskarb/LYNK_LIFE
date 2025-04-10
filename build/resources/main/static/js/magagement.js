// 체크박스 스크립트
$(document).ready(function() {
    // 상단 체크시 전체 체크
    $("#checkAll").click(function () {
        if ($("#checkAll").is(":checked"))
            $("input[name=check]").prop("checked", true);
        else
            $("input[name=check]").prop("checked", false);
    });
    // 개별로 전부 체크되면 최상단 헤더의 체크박스도 체크
    $("input[name=check]").click(function() {
        var total = $("input[name=check]").length;
        var checked = $("input[name=check]:checked").length;

        if(total != checked)
            $("#checkAll").prop("checked", false);
        else
            $("#checkAll").prop("checked", true);
    });
});

// 삭제 버튼 클릭 시
$("#deleteButton").click(function() {
    const selectedCheckboxes = $("input[name='check']:checked");

    if (selectedCheckboxes.length === 0) {
        alert("삭제할 항목을 선택하세요.");
        return;
    }

    // 삭제 확인 팝업
    if (confirm("선택한 사원을 삭제하시겠습니까?")) {
        // 선택된 empID 값들을 배열로 가져오기
        const selectedIds = Array.from(selectedCheckboxes).map(checkbox => checkbox.value);

        // 숨겨진 input 태그로 ID들을 폼에 추가
        const form = $("#deleteForm");
        selectedIds.forEach(id => {
            const input = $("<input>").attr("type", "hidden").attr("name", "empIDs").val(id);
            form.append(input);
        });
        console.log("폼에 추가된 empIDs:", selectedIds);
        console.log("폼 내용:", form.serialize());  // 폼 데이터를 직렬화하여 출력

        // 폼 제출
        form.submit();
    }
});

// 복구 버튼 클릭 시
$("#restoreButton").click(function() {
    const selectedCheckboxes = $("input[name='check']:checked");

    if (selectedCheckboxes.length === 0) {
        alert("복구할 항목을 선택하세요.");
        return;
    }

    // 복구 확인 팝업
    if (confirm("선택한 사원을 복구하시겠습니까?")) {
        // 선택된 empID 값들을 배열로 가져오기
        const selectedIds = Array.from(selectedCheckboxes).map(checkbox => checkbox.value);

        // 숨겨진 input 태그로 ID들을 폼에 추가
        const form = $("#restoreForm");
        selectedIds.forEach(id => {
            const input = $("<input>").attr("type", "hidden").attr("name", "empIDs").val(id);
            form.append(input);
        });
        console.log("폼에 추가된 empIDs:", selectedIds);
        console.log("폼 내용:", form.serialize());  // 폼 데이터를 직렬화하여 출력

        // 폼 제출
        form.submit();
    }
});