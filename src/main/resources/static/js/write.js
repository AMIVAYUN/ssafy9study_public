const { Editor } = toastui;
const { codeSyntaxHighlight } = Editor.plugin;
let editor;

var globe = "#tab01";
$(document).ready(function () {
  initEditor();
  BtnSetting();
});
function initEditor(){
  editor = new Editor({
    el: document.querySelector("#editor"),
    previewStyle: "vertical",
    height: 500,
    plugins: [[codeSyntaxHighlight, { highlighter: Prism }]],
  });
}

function BtnSetting() {
  tabSetting();
  CancelSetting();
  SubmitSetting();
}

function tabSetting() {
  $(".tab ul li a").on("click", function () {
    globe = $(this).attr("href");
  });
}

function CancelSetting() {
  $(".cancel").on("click", function () {
    window.close();
  });
}

function SubmitSetting() {
  let obj = new Object();

  $(".save").on("click", function () {
    obj.subject = $(".subject > input").val();
    obj.title = $(".title > input").val();
    obj.content = editor?.getHTML();
    const currentUrl = window.location.href;
    let urlParts = currentUrl.split("/");
    let house_id = urlParts[5];
    let idx = urlParts[6];
    obj.type = getType(globe);
    obj.idx = idx;
    obj.house_id = house_id;

    obj.answer = getAnswerByType(obj.type);
    if (obj.type == "CODE") {
      alert("아직 미구현 기능입니다.");
      return;
    }

    let url = "/problem/make/" + obj.house_id + "/" + idx + "";

    $.ajax({
      url: url,
      method: "PUT",
      // dataType: "json",
      data: JSON.stringify(obj),
      contentType: "application/json; charset=utf-8",
      success: function (response) {
        alert(" 문제 등록에 성공하였습니다. ");
        const parent = window.opener.document;

        // 문제 상위 문서에 등록

        let found = problemSetting(parent, obj.idx);

        if (found == null) {
          alert("상위 문서 문제 등록간 에러 발생");
        }
        $(found.parentNode).find(".subject").text(obj.subject);
        $(found.parentNode).find(".title").text(obj.title);
        // found.parentNode.querySelector(".subject").innerText( obj.subject );
        // found.parentNode.querySelector(".title").innerText( obj.title );
        window.close();
      },
      error: function (xhr, status, error) {
        console.log(status + error);
        alert(" 문제 등록에 실패하였습니다. 잠시 후에 다시 시도해주세요.");
      },
    });
  });
}

//문제 등록 성공 후 화면에 보내는 메소드
function problemSetting(parent, idx) {
  elements = parent.querySelectorAll("*");
  let found = null;

  elements.forEach((element) => {
    if (element.textContent.trim() === idx) {
      found = element;
    }
  });

  return found;
}

function getAnswerByType(type) {
  let answer = new Object();
  switch (type) {
    case "MULTIPLECHOICE":
      let selectedValue = $(".option:checked").attr("value");
      answer.ans = selectedValue;
      let lst = ["one", "two", "three", "four"];
      let allValue = $(".option_content");
      $.each(allValue, function (idx) {
        answer[lst[idx]] = $(allValue[idx]).val();
      });

      return answer;
    case "SHORTPATH":
      answer.ans = $(".short_answer").val();

      return answer;
  }
}

function getType(type) {
  switch (type) {
    case "#tab01":
      return "MULTIPLECHOICE";
    case "#tab02":
      return "SHORTPATH";
    case "#tab03":
      return "CODE";
  }
}
