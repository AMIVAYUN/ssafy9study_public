$( document ).ready( function(){
    ReadProblems();
    ClickSetting();
});

function ReadProblems(){
    $.ajax({
        url: "/problem/all",
        method: "GET", // GET 또는 POST 등 HTTP 요청 메서드 설정
        dataType: "json",
        success: function( data ) {

            $.each(data, function() {
                // console.log(this); // 현재 항목의 정보를 확인하기 위한 로그
                let tags = '<tr class="board-posted">' +
                    '<td class="num">' + this.house_id + '</td>' +
                    '<td class="title">' + this.title + '</td>' +
                    '<td class="user">' + this.publisher + '</td>' +
                    '<td class="date">' + String( this.date ).slice( 0, 10 ) + '</td>' +
                    '</tr>';
                $("#problems").append(tags);

            });
            // Ajax 요청 성공 시 실행할 코드
        },
        error: function(xhr, status, error) {
            // Ajax 요청 실패 시 실행할 코드
            alert(" 내부 서버에 문제가 있습니다. 다시 시도해주세요 " );
        }
    });
}

function ClickSetting(){

    $(document).on( "click", ".board-posted", function (){
        if( confirm(" 문제를 푸시겠습니까?" ) ){
            let url = "/problem/read/" + $( this ).find(".num").text()
            window.location.href = url ;
        }
    })
}