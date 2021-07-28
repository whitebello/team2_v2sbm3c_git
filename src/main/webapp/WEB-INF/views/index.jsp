<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<%
String windowOpen = "open"; // 이벤트등 공지사항 출력 여부를 결정

Cookie[] cookies = request.getCookies(); // 브러우저에 저장된 Cookie를 가져옴
Cookie cookie = null;

if (cookies != null) { // 쿠키가 존재 한다면
  for (int index=0; index < cookies.length; index++) { // 쿠키 갯수만큼 순환
    cookie = cookies[index];  // 쿠키 목록에서 쿠키 추출
    if (cookie.getName().equals("windowOpen")) { // 이름 비교
      windowOpen = cookie.getValue();  // 쿠키 값, close
    }
  }
}
%>
 
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" /> 

<title>Team 2</title>

<!-- /static 기준 -->
<!-- Core theme CSS (includes Bootstrap)-->
<link href="/css/style.css" rel="stylesheet" type="text/css">
 
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<!-- Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />

<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />

<script type="text/javascript">

window.onload = function() {
  if ('<%=windowOpen %>' == 'open') {
    openNotice();  // 창 열기 
  }  
}

function openNotice(){
  var url = './cookie/notice.do';
  var win = window.open(url, '공지사항', 'width=500px, height=560px');
  
  var x = (screen.width - 500) / 2;
  var y = (screen.height - 560) / 2;
  
  win.moveTo(x, y); // 화면 중앙으로 이동
}

<%-- 찜하기에 상품 추가 --%>
function cart_ajax(productno, stateno, bookno) {
  var f = $('#frm');
  $('#productno', f).val(productno); // 쇼핑카트 등록시 사용할 상품 번호를 저장.
  $('#bookno',f).val(bookno);

  //stateno = 2;
  console.log('-> productno:' +$('#productno', f).val());
  console.log('-> stateno:' + stateno);
  
  console.log('-> only productno:' + productno);

  if (stateno == 2 ){ // 판매 되었을 경우
    
    msg="이미 판매가 완료된 상품입니다";
    window.confirm(msg);
    return;
    
  } else {

    if('${sessionScope.id}'=='') {// 로그인이 안 되어 있는 경우
      // location.href='/register/login.do';
       $('#div_login').show();    // 로그인폼 출력 
     } else {// 로그인이 되어 있는 경우
       cart_ajax_post();
     } // if session end
  }// if stateno end
}// cart_ajax end
 
 <%-- 찜하기에 상품 등록 --%>
 function cart_ajax_post() {
   var f = $('#frm');
   var productno = $('#productno', f).val();
   var bookno = $('#bookno',f).val();
 
   console.log('->cart productno:' + productno);
   console.log('->bookno:' + bookno);
   
   var params = "";
   params += 'productno=' + productno;
   //params += 'bookno=' + bookno;
   console.log("-> cart_ajax_post: "+params);
 
   $.ajax(
       {
         url: '/cart/create.do',
         type: 'post',  // get, post
         cache: false, // 응답 결과 임시 저장 취소
         async: true,  // true: 비동기 통신
         dataType: 'json', // 응답 형식: json, html, xml...
         data: params,      // 데이터
         success: function(rdata) { // 응답이 온경우
           var str='';
           console.log('->cart_ajax_post cnt:'+rdata.cnt); // 1: 찜하기 등록 성공
 
           if (rdata.cnt == 1) {
             var sw = confirm('선택한 상품을 찜하는데 성공하였습니다. \n 찜하기 목록으로 이동하시겠습니까?');
             if (sw == true) {
               // 찜하기로 이동
               location.href='/cart/list.do?bookno='+bookno;
             }
           } else {
             alert('선택한 상품을 찜하는데 실패했습니다. \n 잠시 후 다시 시도해주세요.')
           } // if end
         },//success
         // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
         error: function(request, status, error) { // callback 함수
           console.log(error);
         }
       }
     );// $.ajax end 
 }

 function p_score(){
   console.log("준비");
    
 }
</script>

</head>
<body leftmargin="0" topmargin="0">
<jsp:include page="./menu/top.jsp" flush='false' />
                                 
<!-- Section-->
<section class="py-5">
    <div class="container px-4 px-lg-5 mt-5">
        <div id='body' class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
           <c:forEach var="productVO" items="${load }">
              <div class="col mb-5">
              
                    <div class="card h-90">
                        <!-- Product image-->
                        <c:choose>
                          <c:when test="${productVO.thumb1.endsWith('jpg') || productVO.thumb1.endsWith('png') || productVO.thumb1.endsWith('gif')}">
                            <%-- /static/product/storage/ --%>
                            <a style="width: 100%; height: 63%;" href="/product/read.do?productno=${productVO.productno}&now_page=1&word=${productVO.word }&stateno=${productVO.stateno}">
                              <IMG src="/product/storage/${productVO.thumb1 }" style="width: 95%; height: 90%; padding-top: 5%; padding-left: 15px; border:none;"></a> 
                          </c:when>
                          <c:otherwise> <!-- 이미지가 아닌 일반 파일 -->
                            <a style="width:100%; height:63%;" href="/product/read.do?productno=${productVO.productno}&now_page=1&word=${productVO.word }&stateno=${productVO.stateno}">
                              <img class="card-img-top" src="/product/storage/noimage.png" alt="..." style="width: 100%; height: 90%;" />
                            </a>
                          </c:otherwise>
                        </c:choose>
                        
                        <!-- Product details-->
                        <div style="text-align: left; margin: 5%; padding-bottom: 10px;">
                            <div class="">
                                <!-- Product name-->
                                <span class="">${productVO.title }</span><br>
                                <!-- Product price-->
                                <span style="float: 48%; font-weight: bold; font-size: 1.3em;">￦<fmt:formatNumber value="${productVO.price}" pattern="##,###" /></span>
                                <!-- Product 판매여부-->                          
                                <c:choose>
                                  <c:when test="${productVO.stateno ==1 }">
                                    <span style=' width:50%; float:right; text-align:right; margin-left:1%; font-size: 1.3em;'>판매중</span>
                                  </c:when>
                                  <c:otherwise>
                                    <span style='color: #FF0000; font-size: 1.3em;' class="font-weight-bold">판매 완료</span>
                                  </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        
                        <!-- Product actions-->
                        <div class="card-footer pt-0 border-top-0 bg-transparent" style="text-align: center;">
                          <form name='frm' id='frm' method="POST" action=''>
                            <input type="hidden" name="productno" id="productno" value="${productVO.productno }">
                            <input type='hidden' name='bookno' id = 'bookno' value='${productVO.bookno }'>
                            <c:choose>
                              <c:when test="${productVO.memberno == sessionScope.memberno }">
                                <button type='button' id='btn_mypost' class="btn btn-danger" style='margin-bottom: 2px;'
                                        onclick="alert('내가쓴 게시글은 찜할수 없습니다.');">찜하기</button><br>
                              </c:when>
                              <c:otherwise>
                                <button type='button' id='btn_cart' class="btn btn-danger" style='margin-bottom: 2px;'
                                        onclick="cart_ajax(${productVO.productno}, ${productVO.stateno }, ${productVO.bookno })">찜하기</button><br>
                              </c:otherwise>
                            </c:choose>
                          </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div><!-- body END -->
    </div><!-- container END-->
</section>
 
<jsp:include page="./menu/bottom.jsp" flush='false' />
 
</body>
</html>