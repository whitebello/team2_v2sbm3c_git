<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="shrink-to-fit=no initial-scale=1, width=device-width" /> 
<meta name="description" content="" />
<meta name="author" content="" /> 

<title>Team2</title>
 
<!-- /static 기준, Core theme CSS (includes Bootstrap)-->
<link href="../css/style.css" rel="stylesheet" type="text/css">
 
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<!-- Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />

</head>
 
<body>
<jsp:include page="../menu/top.jsp" flush='false' />
<div class="py-5">
  
    <DIV class='container'> 
      <DIV class="title_line">
        등록된 모든 댓글
      </DIV>
      <ASIDE class='aside_left'>
        <A href='./commgrp/list.do'>카테고리 그룹</A> > 
        <A href='./list.do'>모든 댓글</A>
      </ASIDE>
      <ASIDE class='aside_right'>
        <A href="javascript:location.reload();">새로고침</A>
    <!--     <span class='menu_divide' > | </span> -->
    
      </ASIDE>
       
      <DIV class='menu_line' style='clear: both;'></DIV>
      
      <div style='width: 100%;'>
        <table class="table table-striped" style='width: 100%;'>
          <colgroup>
            <col style="width: 5%;"></col>
            <col style="width: 5%;"></col>
            <col style="width: 5%;"></col>
            <col style="width: 70%;"></col>
            <col style="width: 10%;"></col>
            <col style="width: 5%;"></col>
            
          </colgroup>
          <%-- table 컬럼 --%>
          <thead>
            <tr>
              <th style='text-align: center;'>댓글<br>번호</th>
              <th style='text-align: center;'>글<br>번호</th>
              <th style='text-align: center;'>회원<br>번호</th>
              <th style='text-align: center;'>내용</th>
              <th style='text-align: center;'>등록일</th>
              <th style='text-align: center;'>기타</th>
            </tr>
          
          </thead>
          
          <%-- table 내용 --%>
          <tbody>
            <c:forEach var="replyVO" items="${list }">
              <c:set var="boardno" value="${replyVO.boardno }" />
              <c:set var="replyno" value="${replyVO.replyno }" />
              
              <tr style='height: 50px;'> 
                <td style='text-align: center; vertical-align: middle;'>
                  ${replyVO.replyno }
                </td> 
                <td style='text-align: center; vertical-align: middle;'>
                  <A href='/board/read.do?boardno=${boardno }'>${boardno}</A>
                </td>
                <td style='text-align: center; vertical-align: middle;'>
                  <A href='/member/read.do?memberno=${replyVO.memberno }'>${replyVO.memberno}</A>
                </td>
                <td style='text-align: left; vertical-align: middle;'>${replyVO.boardno}</td>
                <td style='text-align: center; vertical-align: middle;'>
                  ${replyVO.replydate.substring(0, 10)}
                </td>
                <td style='text-align: center; vertical-align: middle;'>
                  <a href="./delete.do?replyno=${replyVO.replyno}"><img src="./images/delete.png" title="삭제"  border='0' /></a>
                </td>
              </tr>
            </c:forEach>
            
          </tbody>
        </table>
        <br><br>
      </div>
    </DIV>
</div>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>
   