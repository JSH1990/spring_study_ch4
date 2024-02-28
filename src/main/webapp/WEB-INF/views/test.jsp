<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
<h2>commentTest</h2>
comment : <input type="text" name="comment"><br>
<button id="sendBtn" type="button">SEND</button>
<button id="modBtn" type="button">수정</button>
<h2>Data From Server :</h2>
<div id="commentList"></div>
<div id="replyForm" style="display: none">
    <input type="text" name="replyComment">
    <button id="wrtRepBtn" type="button">등록</button>
</div>

<script>
    $(document).ready(function() {
        let bno = 195;

        // 댓글 목록을 불러와 화면에 표시하는 함수
        let showList = function (bno) {
            $.ajax({
                type: 'GET',
                url: '/ch4/comments?bno='+bno,
                success: function (result) {
                    $("#commentList").html(toHtml(result));
                },
                error: function () {
                    alert("error");
                }
            });
        };

        // 댓글 전송 버튼 클릭 시 처리
        $("#sendBtn").click(function () {
            let comment = $("input[name=comment]").val();

            if (comment.trim() == '') {
                alert("댓글을 입력해주세요.");
                $("input[name=comment]").focus();
                return;
            }

            $.ajax({
                type: 'POST',
                url: '/ch4/comments?bno=' + bno,
                headers: {"content-type": "application/json"},
                data: JSON.stringify({bno: bno, command: comment}),
                success: function (result) {
                    alert(result);
                    showList(bno);
                },
                error: function () {
                    alert("error");
                }
            });
        });

        // 수정 버튼 클릭 시 처리
        $("#modBtn").click(function () {
            let cno = $(this).attr("data-cno");
            let comment = $("input[name=comment]").val();

            if (comment.trim() == '') {
                alert("댓글을 입력해주세요.");
                $("input[name=comment]").focus();
                return;
            }

            $.ajax({
                type: 'PATCH',
                url: '/ch4/comments/' + cno,
                headers: {"content-type": "application/json"},
                data: JSON.stringify({cno: cno, comment: comment}),
                success: function (result) {
                    alert(result);
                    showList(bno);
                },
                error: function () {
                    alert("error");
                }
            });
        });

        $("#commentList").on("click", ".replyBtn", function() {
            //1.replyForm을 옮기고
            $("#replyForm").appendTo($(this).parent());
            //2.답글을 입력할 폼을 보여주고.
            $("#replyForm").css("display", "block");
        });

        // 수정 버튼 클릭 시 해당 댓글 내용을 input에 표시하고 cno를 저장
        $("#commentList").on("click", ".modBtn", function() {
            let cno = $(this).parent().attr("data-cno");
            let comment = $("span.comment", $(this).parent()).text();

            $("input[name=comment]").val(comment);
            $("#modBtn").attr("data-cno", cno);
        });

        // 삭제 버튼 클릭 시 처리
        $("#commentList").on("click", ".delBtn", function () {
            let cno = $(this).parent().attr("data-cno");
            let bno = $(this).parent().attr("data-bno");

            $.ajax({
                type: 'DELETE',
                url: '/ch4/comments/' + cno + '?bno=' + bno,
                success: function (result) {
                    alert(result);
                    showList(bno);
                },
                error: function () {
                    alert("error");
                }
            });
        });

        // 서버로부터 받은 댓글 목록을 HTML로 변환
        let toHtml = function (comments) {
            let tmp = "<ul>";

            comments.forEach(function (comment) {
                tmp += '<li data-cno=' + comment.cno +
                    ' data-pcno=' + comment.pcno +
                    ' data-bno=' + comment.bno + '>';
                tmp += ' commenter=<span class="commenter">' + comment.commenter + '</span>';
                tmp += ' comment=<span class="comment">' + comment.comment + '</span>';
                tmp += ' up_date=' + comment.up_date;
                tmp += '<button class="delBtn">삭제</button>';
                tmp += '<button class="modBtn">수정</button>';
                tmp += '<button class="replyBtn">답글</button>';
                tmp += '</li>';
            });

            return tmp + "</ul>";
        };

        // 초기 댓글 목록 표시
        showList(bno);
    });
</script>
</body>
</html>
