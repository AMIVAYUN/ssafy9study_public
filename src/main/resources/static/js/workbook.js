$( document ).ready( function(){
    BtnSetting();
})


function BtnSetting(){
    $("#submit").on( "click", function(){

        if( confirm( "문제를 정말로 제출하시겠습니까? ") ){
            let obj = new Object();
            const currentUrl = window.location.href;
            let urlParts = currentUrl.split("/");
            let house_id = urlParts[ 5 ];
            //verification 필요 나중에 추가
            obj.publisher = $("#userID").val();
            obj.title = $("#probtitle").val();

            let url = "/problem/make/"+house_id;
            $.ajax({
                url: url,
                method: "PUT",
                // dataType: "json",
                data: JSON.stringify(obj),
                contentType: "application/json; charset=utf-8",
                success: function(response) {
                    alert(" 문제집 등록에 성공하였습니다. ");
                    window.location.href="/problem"
                },
                error: function(xhr, status, error) {
                    console.log( status + error );
                    alert(" 문제집 등록에 실패하였습니다. 잠시 후에 다시 시도해주세요.");
                }
            });
        }
    })
}