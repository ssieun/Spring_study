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
    <title>put</title>
</head>
<body>
<div>
    <form id="putForm" action="/put" method="post">
        <input type="text" name="memberName" value="${memberName}"/>
        <input type="text" name="memberPw"/>
        <input type="hidden" name="num" value="${num}"/>

  <%--   <button type="submit">전체수정</button>--%>
       <input type="button" value="전체수정" id="put">
    </form>
</div>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
</body>
<script>


        $("#put").click(function(){

            //var form =$("#putForm").serialize();

            var memberName =$("input[name=memberName]").val();
            var memberPw =$("input[name=memberPw]").val();
            var num =$("input[name=num]").val();

     /*       var data = {
                "memberName":memberName,
                "memberPw":memberPw,
                "num":num
            };
*/

            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json;charset=UTF-8'
                },
                type:"put",
                url:"/member/put",
                data:JSON.stringify({
                    "memberName":memberName,
                    "memberPw":memberPw,
                     "num":num
                }), //json를 String화 해주기 위해
                dataType:"text",
                success:function (result) {
                    if(result.trim()=="ok"){
                        alert("수정이 완료 되었습니다.");
                        location.href="/member/main";
                    }
                },
                error:function (request,error) {
                    console.log(memberName);
                    console.log("오류");
                    console.log("code",request.status, "error:",error);
                }

            });

        });



</script>
</html>
