<%--
  Created by IntelliJ IDEA.
  User: ProsumerLabUser
  Date: 2021-07-07
  Time: 오후 2:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>회원정보</title>
</head>
<body>
<table border="1">

    <tr>
        <th>NUM</th>
        <th>ID</th>
        <th>PW</th>
    </tr>

    <c:forEach var ="list"  items="${list}">

        <tr>
            <td>${list.num}</td>
            <td>${list.memberName}</td>
            <td>${list.memberPw}</td>

            <td>
                <a href="/member/patchPage?memberName=${list.memberName}&num=${list.num}">이름수정</a>
                <a href="/member/putPage?memberName=${list.memberName}&num=${list.num}">전체수정</a>
                <a href="" onclick="deleteClick(${list.num});">삭제</a>
            </td>
        </tr>

    </c:forEach>
</table>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</body>

<script>



        function deleteClick(num) {


            $.ajax({
                type: "delete",
                url: "/member/delete/"+num,
                dataType:"text",
                success: function (result) {
                    if(result.trim()=="ok"){
                        console.log("들어옴");
                       alert("삭제 완료 되었습니다.");
                      location.href="/member/main";
                    }

                },
                error: function () {
                    console.log("오류");
                }

            });

        }


</script>

</html>
