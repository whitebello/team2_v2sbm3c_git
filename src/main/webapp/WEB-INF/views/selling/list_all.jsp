<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Team2</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">
 
<script type="text/JavaScript"
          src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
 
 
<script type="text/javascript">
  $(function(){
 
  });
</script>
</head> 
 
<body>
<jsp:include page="../menu/top.jsp" flush='false' />
<div class="py-5">
  
    <DIV class='container c_bottom_10' style=""> 
      <DIV class='title_line'>
        ${sessionScope.mname }님 판매 내역
      </DIV>
    
      <DIV class='content_body' style='width: 100%;'>
    
        <ASIDE class="aside_right">
          <A href="javascript:location.reload();">새로고침</A> |
          <A href="/selling/list_all.do">전체 판매 내역</A> |
          <A href="/selling/list_selling.do">판매 중 상품</A> |
          <A href="/selling/list_sold.do">판매 완료 상품</A>      
        </ASIDE> 
       
        <div class='menu_line'></div>
       
       
        <table class="table table_top_margin" style='width: 100%;'>
        <colgroup>
          <col style='width: 20%'/>
          <col style='width: 40%;'/>
          <col style='width: 10%;'/>
          <col style='width: 10%'/>
          <col style='width: 10%;'/>
          <col style='width: 10%;'/>
        </colgroup>
        <TR class="table_title">
          <TH class='th_bs'>상품 이미지</TH>
          <TH class='th_bs'>제목</TH>
          <TH class='th_bs'>판매 수량</TH>
          <TH class='th_bs'>판매 가격</TH>
          <TH class='th_bs'>판매 상태</TH>
          <TH class='th_bs'>게시글<br>등록일</TH>
        </TR>
       <c:choose>
        <c:when test="${list.size() > 0 }">
          <c:forEach var="sellingVO" items="${list }">
            <c:set var="memberno" value ="${sellingVO.memberno}" />
            <c:set var="productno" value ="${sellingVO.productno}" />
            <c:set var="rdate" value ="${sellingVO.rdate}" />
            <c:set var="title" value ="${sellingVO.title}" />
            <c:set var="price" value ="${sellingVO.price}" />
            <c:set var="thumb1" value ="${sellingVO.thumb1}" />
            <c:set var="stateno" value ="${sellingVO.stateno}" />
            <c:set var="cnt" value ="${sellingVO.cnt}" />
            
               
             
          <TR>
           <c:choose>
            <c:when test="${productno != '' }"> <%-- 상품이 존재할 때 --%>
              <%--  <TD class=td_basic><A href="/register/read.do?memberno=${memberno}">${title}</A></TD> --%>
              <td style='vertical-align: middle; text-align: center;'>
              
                <c:choose>
                  <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}">
                    <%-- /static/contents/storage/ --%>
                    <a href="/product/read.do?productno=${productno}"><IMG src="/product/storage/${thumb1 }" style="width: 120px; height: 80px;"></a> 
                  </c:when>
                  <c:otherwise> <!-- 이미지가 아닌 일반 파일 -->
                    ${product.file1}
                  </c:otherwise>
                </c:choose>
                
              </td> 
              <TD class="td_basic"><A href="/product/read.do?productno=${productno }">${title}</A></TD> 
              <TD class=td_basic>${cnt}</TD>
              <TD class='td_basic'><fmt:formatNumber value="${price }" pattern="#,###" /></TD>
              <TD class='td_basic'>
                <c:choose>
                  <c:when test="${stateno == 1}">판매 중</c:when>
                  <c:when test="${stateno == 2}">판매 완료</c:when>
                </c:choose>
              </TD>
              <TD class='td_basic'>${rdate.substring(0,16) }</TD>
            </c:when>
            <c:when test="${productno == '' }"> <%-- 상품이 삭제되었을 때 --%>
              <TD class="td_basic"></TD>
              <TD class="td_basic">상품이 존재하지 않습니다</TD>
              <TD class="td_basic"></TD>
              <TD class="td_basic"></TD>
              <TD class="td_basic"></TD>
              <TD class="td_basic"></TD>
            </c:when>
           
           </c:choose>  
           
                 
            
          </TR>
          </c:forEach>
          </c:when>
                <c:otherwise>
                  <tr>
                    <td colspan="6" style="text-align: center; font-size: 1.3em;">아직 판매한 상품이 없습니다</td>
                  </tr>
                </c:otherwise>
              </c:choose>
      </TABLE>
       
    <!--   <DIV class='bottom_menu'>
        <button type='button' onclick="location.reload();" class="btn btn-primary">새로 고침</button>
      </DIV> -->
    </DIV>
  </DIV>
</div>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>