const currentUrl = window.location.href;
const lst = currentUrl.split("/");
const house_id = lst[ 5 ];
$( document ).ready( function(){
    getHouseInfo();
    BtnSetting();
})

function getHouseInfo(){

    const lastIndex = currentUrl.lastIndexOf("/");
    const slicedPart = currentUrl.substring(lastIndex + 1);
    let idx = 1;
    $.ajax({
        url: "/problem/house/" + slicedPart,
        method: "GET", // GET 또는 POST 등 HTTP 요청 메서드 설정
        contentType: "application/json; charset=utf-8",

        success: function( data ) {
            $("#title").text( data.title );

            $.each(data['problems'], function() {

                let tags = '<tr class="board-posted">' +
                    '<td class="num">' + idx++ + '</td>' +
                    '<td class="subject">' + this.subject + '</td>' +
                    '<td class="title">' + this.title + '</td>' +
                    '<td class="check"><i class="fa-regular fa-circle-check" style="color: #7cfc00;"></i>'+
                    '<td class="qzinput"><a class="btn solve">문제 풀기</a></td>' +
                    '<td class="answer"></td>'+
                    '</tr>';
                $("#problems").append(tags);
            });

            examSetting();
            // Ajax 요청 성공 시 실행할 코드
        },
        error: function(xhr, status, error) {
            // Ajax 요청 실패 시 실행할 코드
            alert(" 내부 서버에 문제가 있습니다. 다시 시도해주세요 " );
        }
    });
}

function examSetting(){
    if( $(".solve").length ){
        const lastIndex = currentUrl.lastIndexOf("/");
        const slicedPart = currentUrl.substring(lastIndex + 1);

        let lst = $(".solve");
        $.each( lst, function( i, e ) {
            let idx = $( this ).parent().parent().find(".num").text();
            $( e ).on("click", function(){
                if( confirm( idx + "번 문제를 푸시겠습니까? " ) ){
                    let now = document.location.href + "/" + idx;
                    let popupOptions = "width=1200,height=900,resizable=yes,scrollbars=yes";
                    window.open(now, idx + "번 문제 풀기", popupOptions);

                }
            })
        })

    }
}

function BtnSetting(){
    $( ".submit").on( "click", function(){
        if( !confirm( "정말로 제출하시겠습니까?" ) ){
            return;
        }
        let obj = new Object();
        obj.house_id = house_id;
        let solved = $(".board-posted");
        obj.answers = [];
        $.each( solved, function( idx ){
            let subobject = new Object();
            subobject.num = $( solved[ idx ] ).find(".num").text();
            subobject.answer = $( solved[ idx ] ).find(".answer").text();
            obj.answers.push( subobject );
        })
        // url /problem/result

        $.ajax({
            url: "/problem/result",
            method: "POST", // GET 또는 POST 등 HTTP 요청 메서드 설정
            dataType: "text",
            data: JSON.stringify(obj),
            contentType: "application/json; charset=utf-8",
            success: function(response) {
                let data = JSON.parse( response );
                console.log( data , data.corrects, data.wrongs );
                alert("채점 성공 ");
                let next = "/problem/result?corrects=" + data.corrects +"&wrong="+data.wrongs;
                let popupOptions = "width=1200,height=900,resizable=yes,scrollbars=yes";
                window.open( next, "채점 결과", popupOptions);
            },
            error: function(xhr, status, error) {
                console.log( status + error );
                alert(" 채점 실패 ");
            }
        })

    })
}