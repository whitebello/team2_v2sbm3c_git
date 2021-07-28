<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<DIV class='container_main'> 
  <%-- 화면 상단 메뉴 --%>

  <!-- Navigation-->
  <nav class="navbar navbar-expand-lg navbar-light bg-light" style="margin-bottom: 0px;">
    <div class="container px-4 px-lg-5"> 
      <a class="navbar-brand" href="/index.do">Used Book Market </a>
      
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
      
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
          <li class="nav-item"><a class="nav-link active" aria-current="page" href="/index.do" style="margin-right: 10px;">Home</a></li>
          <c:choose>
            <c:when test="${sessionScope.id == null}"> <%-- 로그인 안 한 경 우 --%>
              <li class="nav-item"><a class="nav-link" href="/register/login.do" style="margin-right: 10px;">로그인</a></li>
              <li class="nav-item"><a class="nav-link" href="/register/create.do" style="margin-right: 10px;">회원가입</a></li>
            </c:when>
            <c:otherwise>
              <li class="nav-item"><a class="nav-link" href="/register/logout.do" style="margin-right: 10px;">로그아웃</a></li>
            </c:otherwise>
          </c:choose>
          
          <%-- 도서거래: 정권님 담당 개발~ --%>   
          <li class="nav-item dropdown" style="margin-right: 10px;">
            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">도서거래</a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" href="/bookgrp/list.do" >전공도서분류</a></li>
              <li><hr class="dropdown-divider" /></li>
              <li><a class="dropdown-item" href="../book/list_member.do?bookgrpno=1">공과대학전공</a></li>
              <li><a class="dropdown-item" href="../book/list_member.do?bookgrpno=2">자연대학전공</a></li>
              <li><a class="dropdown-item" href="../book/list_member.do?bookgrpno=3">경영대학전공</a></li>
              <li><a class="dropdown-item" href="../book/list_member.do?bookgrpno=4">사범대학전공</a></li>
              <li><a class="dropdown-item" href="../book/list_member.do?bookgrpno=5">문과대학전공</a></li>
              <li><a class="dropdown-item" href="../book/list_member.do?bookgrpno=6">사회과학대학전공</a></li>
              <li><a class="dropdown-item" href="../book/list_member.do?bookgrpno=7">의과대학전공</a></li>
              <li><a class="dropdown-item" href="../book/list_member.do?bookgrpno=8">예술체육대학전공</a></li>
              <li><a class="dropdown-item" href="../book/list_member.do?bookgrpno=9">외국어전공</a></li>
              <li><a class="dropdown-item" href="../book/list_member.do?bookgrpno=10">기타전공도서</a></li>
            </ul>
          </li>
          
          <%-- 커뮤니티: 숙호님 담당 개발~ --%>   
          <li class="nav-item dropdown" style="margin-right: 10px;">
            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">커뮤니티</a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" href="/commgrp/list.do" >커뮤니티 그룹</a></li>
              <li><hr class="dropdown-divider" /></li>
              <li><a class="dropdown-item" href="/board/list_by_commgrpno_search_paging.do?commgrpno=1&word=&now_page=1" >커뮤니티</a></li>
              <li><a class="dropdown-item" href="/board/list_by_commgrpno_notice_search_paging.do?commgrpno=2&word=&now_page=1">공지사항</a></li>
              <li><a class="dropdown-item" href="/board/list_by_commgrpno_qna_search_paging.do?commgrpno=3&word=&now_page=1">QnA</a></li>
            </ul>
          </li>
          
          <%-- 프로필/시간표: 종환님 담당 개발~ --%>   
          <li class="nav-item"><a class="nav-link" aria-current="page" href="/schedule/schedule.do" style="margin-right: 10px;">시간표</a></li>
     
          <%-- 이벤트 공지 --%>
          <li class="nav-item"><a class="nav-link" aria-current="page" href="/cookie/notice.do" style="margin-right: 10px;">이벤트공지</a></li>
          
          <li class="nav-item dropdown" style="margin-right: 10px;">
            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">마이페이지</a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <%-- 프로필 개발 --%>
              <li><a class="dropdown-item" href="#!" >나의 계정</a></li>
              <li><hr class="dropdown-divider" /></li>
              <li><a class="dropdown-item" href="/payment/list_by_memberno.do?memberno=${memberno }">구입 목록</a></li>
              <li><a class="dropdown-item" href="/selling/list_all.do?memberno=${memberno }">판매 목록</a></li>
              <li><a class="dropdown-item" href="/review/list_by_memberno.do?memberno=${sessionScope.memberno}">리뷰</a></li>
              <li><a class="dropdown-item" href="/message/list.do?memberno=${sessionScope.memberno }">쪽지</a></li>
            </ul>
          </li>
               
          <%-- 관리자: 담당보류~ (게시글 CRUD / 도서분류 CRUD / 도서판매 RD / 회원목록 CRUD / 로그관리 R /) --%>
          <c:choose>
            <%-- 관리자일 경우에만 표시 --%>
            <c:when test="${sessionScope.grade <=10 }">
              <li class="nav-item dropdown" style="margin-right: 10px;">
                <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">관리자</a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <%-- 프로필 개발 --%>
                    <li><a class="dropdown-item" href="#!" >관리자</a></li>
                    <li><hr class="dropdown-divider" /></li>
                    <li><a class="dropdown-item" href="#!">회원 관리</a></li>
                    <li><a class="dropdown-item" href="/commgrp/list_ajax.do">커뮤니티 관리</a></li>
                    <li><a class="dropdown-item" href="/bookgrp/list_ajax.do">도서분류 관리</a></li>
                    <li><a class="dropdown-item" href="/book/list_by_bookgrpno.do?bookgrpno=1">전공도서 관리</a></li>
                    <li><a class="dropdown-item" href="/product/list_by_bookno_search_paging.do">전공도서상품 관리</a></li>
                    <li><a class="dropdown-item" href="/review/list.do">리뷰 관리</a></li>
                    <li><a class="dropdown-item" href="#!">로그 관리</a></li>
                </ul>
              </li>
            </c:when>  
          </c:choose>
        </ul>
        
        
        <%-- 장바구니: 주경님 담당 개발~ --%>
        <form class="d-flex" action="/cart/list.do" method='get'>
          <c:choose>
            <c:when test="${sessionScope.id == null}"> <%-- 로그인 안 한 경 우 --%>
              <button class="btn btn-outline-dark" style="border: solid 1px;" type="submit">
                <i class="bi-cart-fill me-1"></i>
                  Cart
                <%--<span class="badge bg-dark text-white ms-1 rounded-pill">0</span>--%>
              </button>
            </c:when>
            <c:otherwise> <%-- 로그인 한 경 우 --%>
              <button class="btn btn-outline-dark" style="border: solid 1px; margin-right:5px;" type="submit">${sessionScope.id }</button> 
              <button class="btn btn-outline-dark" style="border: solid 1px;" type="submit">
                <i class="bi-cart-fill me-1"></i>
                  Cart
                <%-- 나중에 장바구니에 도서갯수 카운트해서 숫자뜨게 해주세요~ 
                <span class="badge bg-dark text-white ms-1 rounded-pill">{}</span>--%>
              </button>
            </c:otherwise>
          </c:choose>
        </form>
      </div>
    </div>
  </nav>
  
  <div class="bg-dark py-5">
    <div class="container px-4 px-lg-5 my-5">
      <div class="text-center text-white" style="back">
          <h1 class="display-4 fw-bolder">Shop in style</h1>
          <p class="lead fw-normal text-white-50 mb-0">With this shop hompeage template</p>
      </div>
    </div>
  </div>            
  
  <%-- 내용 --%> 
  <DIV class='content'>