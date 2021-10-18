<%--
  Created by IntelliJ IDEA.
  User: ProsumerLabUser
  Date: 2021-07-07
  Time: 오후 4:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>patch</title>
</head>
<body>
<div>
    <form name="patch" action="/patch" method="patch">
        <input type="text" name="memberName" value="${memberName}"/>
        <input type="hidden" name="num" value="${num}"/>

        <%--<button type="submit">이름수정</button>--%>
        <input type="button" value="이름수정" id="patch">
    </form>
</div>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
</body>
<script>


    $("#patch").click(function(){

        var memberName =$("input[name='memberName']").val();
        var num =$("input[name='num']").val();

        console.log(memberName)

        $.ajax({
            type:"patch",
            url:"/member/patch/"+num,
            data:memberName, //({"memberName":memberName})
            dataType:"text",
            success:function (result) {
                if(result.trim()=="ok"){
                    alert("수정이 완료 되었습니다.");
                    location.href="/member/main";

                }else{
                    alert("수정실패");
                }
            },
            error:function () {
                console.log("오류");
            }

        });

    });



</script>
</html>
