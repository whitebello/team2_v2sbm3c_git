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


<script type="text/javascript">
</script>
</head> 
 
<body>
<jsp:include page="../menu/top.jsp" />

<div class="py-5">
  
    <DIV class='container c_bottom'> 
        <DIV class='title_line_none'>
          <!-- <a href="../commgrp/list.do" class='title_link'>커뮤니티</a> -->
          <a href="./list_by_commgrpno_search_paging.do?commgrpno=${param.commgrpno }&word=&now_page=1" class='title_link'>${commgrpVO.name}</a>
        </DIV>

        <ASIDE class="aside_right">
          <A href="javascript:location.reload();">새로고침</A>
          <span class='menu_divide' >　</span>
          <A href="./list_by_commgrpno_search_paging.do?commgrpno=${param.commgrpno }&word=${param.word }&now_page=${param.now_page}" title="기본 목록형"><IMG style="width:24px;"src='/css/images/list_gray.png'></A>        
          <span class='menu_divide' >　</span>
          <A href="./list_by_commgrpno_grid_paging.do?commgrpno=${param.commgrpno }&word=&now_page=1"><IMG style="width:24px;"src='/css/images/photo_gray.png'></A>        
<%--                     <A href="./list_by_commgrpno_grid.do?commgrpno=${param.commgrpno }&now_page=${param.now_page}"><IMG style="width:24px;"src='/css/images/photo_gray.png'></A>         --%>
        </ASIDE> 
        
        <DIV class='menu_line'></DIV>
        
        <TABLE class='table table_top_margin'>
          <colgroup>
            <col style='width: 10%;'/>
            <col style='width: 20%;'/>
            <col style='width: 20%;'/> 
            <col style='width: 20%;'/>
            <col style='width: 20%;'/>
            <col style='width: 10%;'/>
          </colgroup>
         
          <thead>  
            <TR class="table_title">
              <TH class="th_bs">회원번호</TH>
              <TH class="th_bs">아이디</TH>
              <TH class="th_bs">이름</TH>
              <TH class="th_bs">가입일</TH>
              <TH class="th_bs">전화번호</TH>
              <TH class="th_bs">등급</TH>
            </TR>
          </thead>
          
          <tbody  style="font-size: 1.08em;">
            <c:forEach var="registerVO" items="${list}" varStatus="status">
              <TR>
                <TD class="td_bs">${registerVO.memberno }</TD>
                <TD class="td_bs">${registerVO.id }</TD>
                <TD class="td_bs">${registerVO.mname}</TD>
                <TD class="td_bs">${registerVO.mdate.substring(0, 10) }</TD>
                <TD class="td_bs">${registerVO.tel }</TD>
                <TD class="td_bs">
                </TD>
               
              </TR>   
            </c:forEach> 
          </tbody>
        </TABLE>

        <div class="content_body_bottom ">
          <button type='button' class="btn" id="btn_create" style="border: solid 1px;" 
                  onclick="b_c_btn(${commgrpno})">글쓰기</button>  
        </div>
          
        <DIV class='content_body_bottom_c_page'>${paging }</DIV>    

        <%-- 검색 --%>
        <div class="content_body_bottom_c_search">
          <form name='frm' id='frm' method='get' action='./list_by_commgrpno_search_paging.do'>
            <input type='hidden' name='commgrpno' value='${param.commgrpno }'>
            <input type='hidden' name='now_page' value='${param.now_page }'>
            
            <%-- 상세 검색 기능 구현 해야함 --%>
            <select class="input_search_word_" style='width:10%;'>
              <option value='all'${param.search_option == "all" ? "selected='selected'":""}>전체 검색</option>
              <option value='title'${param.search_option == "title" ? "selected='selected'":""}>제목</option>
              <option value='bcon'${param.search_option == "bcon" ? "selected='selected'":""}>내용</option>                    
            </select> 
            
            <c:choose>
              <c:when test="${param.word != '' }"> <%-- 검색하는 경우 --%>
                <input class="input_search_word_" type='text' name='word' id='word' value='${param.word }' style='width: 25%;'>
              </c:when>
              <c:otherwise> <%-- 검색하지 않는 경우 --%>
                <input class="input_search_word_" type='text' name='word' id='word' value='' style='width: 25%;'>
              </c:otherwise>
            </c:choose>
            
            
            <button type='submit' class="btn_yelㅣow margin_l_10">검색</button>
            
            <c:if test="${param.word.length() > 0 }">
              <button type='button' class="btn_gray"
                           onclick="location.href='./list_by_commgrpno_search_paging.do?commgrpno=${commgrpVO.commgrpno}&word=&now_page=1'">검색 취소</button>  
            </c:if>    
          </form>
        </div>
        
        <%-- 추가적으로 광고를 넣을 DIV, css 추가 --%>
        <div class="c_bottom_ad">
          광고
        </div>
        
        
    </DIV>
</div>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

