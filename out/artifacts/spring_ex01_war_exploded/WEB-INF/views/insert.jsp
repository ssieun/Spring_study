<%--
  Created by IntelliJ IDEA.
  User: ProsumerLabUser
  Date: 2021-07-08
  Time: 오전 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>insert</title>
</head>
<body>
    <form name="insertForm" action="/member/insert" method="post">
    <input type="text" name="memberName"/>
    <input type="text" name="memberPw"/>
        <button type="submit">등록</button>
    </form>
</body>
</html>
