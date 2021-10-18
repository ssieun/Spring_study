<%--
  Created by IntelliJ IDEA.
  User: ProsumerLabUser
  Date: 2021-07-08
  Time: 오전 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>select</title>
</head>
<body>

<form>
    <input type="text" name="name"/>
    <input type="button" id="search" value="검색"/>
    <P id="text"></P>
</form>

</body>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>

<script>


    $("#search").click(function(){


        var memberName = $("input[name='name']").val();

        $.ajax({
            type:"get",
            url:"/member/select/"+memberName,
            dataType:"text",
            success:function(result){
                    console.log(result);
                if(result.trim()==""){
                    $("#text").text("없는 회원입니다.");
                }else{
                    console.log(result);
                    $("#text").text(memberName+"님의 비밀번호는"+result+"입니다.");

                }

            },
            error:function () {
                console.log("오류");
            }

            });

        });


</script>
</html>
