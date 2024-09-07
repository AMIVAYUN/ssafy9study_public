
$( document ).ready( function (){

    workbookSetting();
    // examSetting();
    // examSetting();
})


//개별 문제 만들기
function workbookSetting(){
    if( $(".makeProb").length ){
        const currentUrl = window.location.href;
        const lastIndex = currentUrl.lastIndexOf("/");
        const slicedPart = currentUrl.substring(lastIndex + 1);

        let lst = $(".makeProb");
        $.each( lst, function( i, e ) {
            let idx = $( this ).parent().parent().find(".num").text();
            $( e ).on("click", function(){
                if( confirm( idx + "번 문제를 만드시겠습니까? " ) ){
                    $.ajax({
                        url: "/problem/make/" + slicedPart + "/" + idx,
                        method: "POST", // GET 또는 POST 등 HTTP 요청 메서드 설정
                        dataType: "text",
                        success: function() {
                            // Ajax 요청 성공 시 실행할 코드
                            let now = document.location.href + "/" + idx;
                            let popupOptions = "width=1200,height=900,resizable=yes,scrollbars=yes";
                            window.open(now, idx + "번 문제 만들기", popupOptions);
                        },
                        error: function(xhr, status, error) {
                            // Ajax 요청 실패 시 실행할 코드
                            alert(" 내부 서버에 문제가 있습니다. 다시 시도해주세요 " );
                        }
                    });

                }
            })
        })

    }
}

