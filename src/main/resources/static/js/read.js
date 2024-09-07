imgPath = "../Spinner.gif";
const { Editor } = toastui;
const { codeSyntaxHighlight } = Editor.plugin;

let viewer;
$(document).ready(function () {
  ReadProblem();
  BtnSetting();
});

function LoadingWithMask(gif) {
  //화면의 높이와 너비를 구합니다.
  var maskHeight = $(document).height();
  var maskWidth = window.document.body.clientWidth;

  //화면에 출력할 마스크를 설정해줍니다.
  var mask =
    "<div id='mask' style='position:absolute; z-index:9000; background-color:#000000; display:none; left:0; top:0;'></div>";
  var loadingImg = "";

  loadingImg +=
    " <img src='" + gif + "' style='position: absolute; display: block; margin: 0px auto;'/>";

  //화면에 레이어 추가
  $("body").append(mask);

  //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
  $("#mask").css({
    width: maskWidth,
    height: maskHeight,
    opacity: "0.3",
  });

  //마스크 표시
  $("#mask").show();

  //로딩중 이미지 표시
  $("#loadingImg").append(loadingImg);
  $("#loadingImg").show();
}

function closeLoadingWithMask() {
  $("#mask, #loadingImg").hide();
  $("#mask, #loadingImg").empty();
}

function initViewer(content) {
  viewer = new Editor({
    el: document.querySelector("#viewer"),
    previewStyle: "vertical",
    height: 500,
    initialValue: `${content}`,
    viewer: true,
  });
}

function ReadProblem() {
  LoadingWithMask(imgPath);
  const currentUrl = window.location.href;
  let urlParts = currentUrl.split("/");
  let house_id = urlParts[5];
  // console.log( urlParts );
  let problem_id = urlParts[6];
  $.ajax({
    url: "/customproblem?house_id=" + house_id + "&problem_id=" + problem_id,
    method: "GET", // GET 또는 POST 등 HTTP 요청 메서드 설정
    dataType: "json",
    success: function (data) {
      $("#title").text(data.title);
      $("#subject").text(data.subject);
      initViewer(data.content);

      const type = data.type;
      const answer = data.answer;

      switch (type) {
        case "MULTIPLECHOICE":
          $("#shortpathanswer").css("display", "none");
          $("#answer_list").empty();
          $("#answer_list").append(
            '<label id = "1" class="l_option"><input class="option ansVal" type="radio" name="check"  value="1">' +
              answer.one +
              "</label>"
          );
          $("#answer_list").append(
            '<label id = "2" class="l_option"><input class="option ansVal" type="radio" name="check"  value="1">' +
              answer.two +
              "</label>"
          );
          $("#answer_list").append(
            '<label id = "3" class="l_option"><input class="option ansVal" type="radio" name="check"  value="1">' +
              answer.three +
              "</label>"
          );
          $("#answer_list").append(
            '<label id = "4" class="l_option"><input class="option ansVal" type="radio" name="check"  value="1">' +
              answer.four +
              "</label>"
          );
          break;
        case "SHORTPATH":
          $(".l_option").css("display", "none");
          break;
      }

      closeLoadingWithMask();
      // Ajax 요청 성공 시 실행할 코드
    },
    error: function (xhr, status, error) {
      // Ajax 요청 실패 시 실행할 코드
      alert(" 내부 서버에 문제가 있습니다. 다시 시도해주세요 ");
      window.close();
    },
  });
}
function BtnSetting() {
  $("#submit").on("click", function () {
    const currentUrl = window.location.href;
    const lst = currentUrl.split("/");
    const lastIndex = lst[6];

    const parent = window.opener.document;
    let found = problemSetting(parent, lastIndex);

    if (found == null) {
      alert("상위 문서 문제 등록간 에러 발생");
      return;
    }
    //4지선다 else 단답
    let answer;

    if ($(".l_option").css("display") != "none") {
      // let idx = $('input[name=check]:checked').parent().attr("id");
      // answer = /*idx + " " + */
      answer = $("input[name=check]:checked").parent().attr("id");
    } else {
      answer = $("#shortpathanswer").val();
      // console.log( answer );
    }
    if (answer == null) {
      alert(" 정답을 입력해주세요. ");
      return;
    }

    if (confirm("제출하시겠습니까?")) {
      $(found).find(".answer").text(answer);
      $(found).find(".qzinput").css("visibility", "visible");
      $(found).find(".check").css("visibility", "visible");
      window.close();
    }
  });
}

function problemSetting(parent, idx) {
  elements = parent.querySelectorAll(".board-posted");
  // console.log( elements );
  idx -= 1;

  return elements[idx];
}
