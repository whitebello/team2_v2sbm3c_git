<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>주문</title>
<style type="text/css">
  .basket_label {
    font-family: Malgun Gothic;
    font-size: 28px;
    float: left;
    text-align: left;
  }
  
  .basket_price {
    font-size: 33px;
    color: #ff3333;
    text-align: right;
  }
  
  .title_line {
    text-align: left;
    border-bottom: solid 3px #555555;
    width: 100%;
    margin: 20px auto;
    font-size: 20px;    
  }
  
    .content_body {
    width: 90%;
    margin: 10px auto; // margin 해결
  }
  
    .aside_right {
    float: right;
    font-size: 0.9em;
  }
  
  
</style>


<link href="/css/style.css" rel="Stylesheet" type="text/css">

<script type="text/JavaScript"
          src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<script type="text/javascript">
  $(function() { // 자동 실행
    $('#btn_DaumPostcode').on('click', DaumPostcode); // 다음 우편 번호
    $('#btn_my_address').on('click', my_address); 
    $('#btn_payment').on('click', send);
  });

  // 나의 주소 가져오기, jQuery ajax 요청
  function my_address() {
    // $('#btn_close').attr("data-focus", "이동할 태그 지정");
    
    // var frm = $('#frm'); // id가 frm인 태그 검색
    // var id = $('#id', frm).val(); // frm 폼에서 id가 'id'인 태그 검색
    var params = '';
    var msg = '';

    $.ajax({
      url: '/register/read_ajax.do', // spring execute
      type: 'get',  // post
      cache: false, // 응답 결과 임시 저장 취소
      async: true,  // true: 비동기 통신
      dataType: 'json', // 응답 형식: json, html, xml...
      data: params,      // 데이터
      success: function(rdata) { // 서버로부터 성공적으로 응답이 온경우
        // alert(rdata);
        var msg = "";

        $('#realname').val(rdata.realname);
        $('#phone').val(rdata.phone);
        $('#postcode').val(rdata.postcode);
        $('#address').val(rdata.address);
        $('#detaddress').val(rdata.detaddress);
        
      },
      // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
      error: function(request, status, error) { // callback 함수
        console.log(error);
      }
    });

  }

  function check_null(str) {
    var sw = false;
    if (str == "" || str.trim().length == 0 || str == null || str == undefined || typeof str == "object") {
      sw = true;  // 값이 없는 경우  
    }
    return sw;
  }
  
  function send() {
    if (check_null($('#realname').val()))  {
      alert('수취인 성명을 입력해주세요.');
      $('#realname').focus();
      return;
    } 

    if (check_null($('#phone').val()))  {
      alert('수취인 전화번호를 입력해주세요.');
      $('#phone').focus();
      return;
    } 

    if (check_null($('#postcode').val()))  {
      alert('우편번호를 입력해주세요.');
      $('#postcode').focus();
      return;
    }    

    if (check_null($('#address').val()))  {
      alert('주소를 입력해주세요.');
      $('#address').focus();
      return;
    }     
     
    if (check_null($('#detaddress').val()))  {
      alert('상세 주소를 입력해주세요. 내용이 없으면 수취인 성명을 입력주세요.');
      $('#detaddress').focus();
      return;
    }     

    frm.submit();
  }
</script>
</head> 


<body>
<jsp:include page="../menu/top.jsp" flush='false' />
  <DIV class='title_line'>
    주문, 결재
  </DIV>

  <DIV class='content_body'>

  <ASIDE class="aside_left">
    주문 상품
  </ASIDE> 

  <div class='menu_line'></div>
  
  <table class="table table-striped" style='width: 100%;'>
    <colgroup>
      <col style="width: 20%;"></col>
      <col style="width: 40%;"></col>
      <col style="width: 20%;"></col>
      <col style="width: 20%;"></col>
    </colgroup>
   
    <%-- table 내용 --%>
    <tbody>
        <c:forEach var="interestedVO" items="${list }">
          <c:set var="interestedno" value="${interestedVO.interestedno }" />
          <c:set var="productno" value="${interestedVO.productno }" />
          <c:set var="title" value="${interestedVO.title }" />
          <c:set var="thumb1" value="${interestedVO.thumb1 }" />
          <c:set var="price" value="${interestedVO.price }" />
          <c:set var="memberno" value="${interestedVO.memberno }" />
          <c:set var="sdate" value="${interestedVO.sdate }" />
        
        <tr> 
          <td style='vertical-align: middle; text-align: center;'>
            <c:choose>
              <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}">
                <%-- /static/contents/storage/ --%>
                <%-- <a href="/product/read.do?productno=${productno}"><IMG src="/board/storage/${thumb1 }" style="width: 120px; height: 80px;"></a>  --%>
              </c:when>
              <c:otherwise> <!-- 이미지가 아닌 일반 파일 -->
                ${productVO.file1}
              </c:otherwise>
            </c:choose>
          </td>  
          <td style='vertical-align: middle;'>
            <a href="/product/read.do?productno=${productno}"><strong>${title}</strong></a> 
          </td> 
          <td style='vertical-align: middle; text-align: center;'>
            <fmt:formatNumber value="${price }" pattern="#,###" /><br>
          </td>
              
          <td style='vertical-align: middle; text-align: center;'>
            <A href="../interested/list.do"><span class="glyphicon glyphicon-remove" title="쇼핑카트로 이동합니다."></A>
          </td>
        </tr>
      </c:forEach>
      
    </tbody>
  </table>
  
  <form name='frm' id='frm' style='margin-top: 50px;' action="/payment/create.do" method='post'>
    <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }">  
    <input type="hidden" name="amount" value=" ${tot_sum }">   <%-- 전체 주문 금액 --%>
    
    
  <ASIDE class="aside_left">
    배송 정보<span style="font-size: 0.7em;">(*: 필수 입력)</span>
    <button type="button" id="btn_my_address" class="btn btn-light" style="margin-bottom: 2px;">나의 주소 가져오기</button> 
    <button type="reset" id="btn_reset" class="btn btn-light" style="margin-bottom: 2px;">주소 지우기</button>
  </ASIDE> 

  <div class='menu_line'></div>

    <div class="form-group">
      <label for="realname" class="col-md-2 control-label" style='font-size: 0.9em;'>수취인 성명*</label>    
      <div class="col-md-10">
        <input type='text' class="form-control" name='realname' id='realname' 
                   value='' required="required" style='width: 30%;' placeholder="수취인 성명">
      </div>
    </div>   

    <div class="form-group">
      <label for="tel" class="col-md-2 control-label" style='font-size: 0.9em;'>수취인 전화번호*</label>    
      <div class="col-md-10">
        <input type='text' class="form-control" name='phone' id='phone' 
                   value='' required="required" style='width: 30%;' placeholder="수취인 전화번호"> 예) 010-0000-0000
      </div>
    </div>   

    <div class="form-group">
      <label for="zipcode" class="col-md-2 control-label" style='font-size: 0.9em;'>우편번호</label>    
      <div class="col-md-10">
        <input type='text' class="form-control" name='postcode' id='postcode' 
                   value='' style='width: 30%;' placeholder="우편번호">
        <input type="button" id="btn_DaumPostcode" value="우편번호 찾기" class='btn btn-dark text-white' >
      </div>
    </div>  

    <div class="form-group">
      <label for="address1" class="col-md-2 control-label" style='font-size: 0.9em;'>주소</label>    
      <div class="col-md-10">
        <input type='text' class="form-control" name='address' id='address' 
                   value='' style='width: 80%;' placeholder="주소">
      </div>
    </div>   

    <div class="form-group">
      <label for="address2" class="col-md-2 control-label" style='font-size: 0.9em;'>상세 주소</label>    
      <div class="col-md-10">
        <input type='text' class="form-control" name='detaddress' id='detaddress' 
                   value='' style='width: 80%;' placeholder="상세 주소">
      </div>
    </div>   

    <div class="form-group">
      <div class="col-md-12">

<!-- ------------------------------ DAUM 우편번호 API 시작 ------------------------------ -->
<div id="wrap" style="display:none;border:1px solid;width:500px;height:300px;margin:5px 110px;position:relative">
  <img src="//i1.daumcdn.net/localimg/localimages/07/postcode/320/close.png" id="btnFoldWrap" style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1" onclick="foldDaumPostcode()" alt="접기 버튼">
</div>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
    // 우편번호 찾기 화면을 넣을 element
    var element_wrap = document.getElementById('wrap');

    function foldDaumPostcode() {
        // iframe을 넣은 element를 안보이게 한다.
        element_wrap.style.display = 'none';
    }

    function DaumPostcode() {
        // 현재 scroll 위치를 저장해놓는다.
        var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
        new daum.Postcode({
            oncomplete: function(data) {
                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = data.address; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 기본 주소가 도로명 타입일때 조합한다.
                if(data.addressType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                $('#postcode').val(data.zonecode); //5자리 새우편번호 사용 ★
                $('#address').val(fullAddr);  // 주소 ★

                // iframe을 넣은 element를 안보이게 한다.
                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                element_wrap.style.display = 'none';

                // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를 되돌린다.
                document.body.scrollTop = currentScroll;
                
                $('#detaddress').focus(); //  ★
            },
            // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는 부분. iframe을 넣은 element의 높이값을 조정한다.
            onresize : function(size) {
                element_wrap.style.height = size.height+'px';
            },
            width : '100%',
            height : '100%'
        }).embed(element_wrap);

        // iframe을 넣은 element를 보이게 한다.
        element_wrap.style.display = 'block';
    }
</script>
<!-- ------------------------------ DAUM 우편번호 API 종료 ------------------------------ -->

      </div>
    </div>
  
  <div style='margin-top: 20px; width: 100%; clear: both;'> </div>  
  <ASIDE class="aside_left">
    결재 정보<br>
  </ASIDE> 

  <div class='menu_line'></div>
  <div style=" text-align: left;">
    <label style="cursor: pointer;"><input type="radio" name="paytype" id="paytype" value="1" checked="checked"> 신용 카드</label>&nbsp;&nbsp;
    <label style="cursor: pointer;"><input type="radio" name="paytype" id="paytype" value="2"> 모바일</label>&nbsp;&nbsp;
    <label style="cursor: pointer;"><input type="radio" name="paytype" id="paytype" value="3"> 계좌 이체</label>&nbsp;&nbsp;
    <label style="cursor: pointer;"><input type="radio" name="paytype" id="paytype" value="4"> 직접 입금</label>&nbsp;&nbsp;
  </div>
  
  <table class="table table-striped" style='margin-top: 20px; margin-bottom: 50px; width: 100%; clear: both;'>
    <tbody>
      <tr>
        <td style='width: 50%;'>
          <div class='basket_label' style='font-size: 2.0em;'>상품 금액</div>
          <div class='basket_price' style='font-size: 2.0em;'><fmt:formatNumber value="${tot_sum }" pattern="#,###" /> 원</div>
          
          <div class='basket_label' style='font-size: 2.0em;'>배송비</div>
          <div class='basket_price' style='font-size: 2.0em;'><fmt:formatNumber value="${delivery }" pattern="#,###" /> 원</div>
          
        </td>
        <td style='width: 50%;'>
          <div class='basket_label' >전체 주문 금액</div>
          <div class='basket_price' ><fmt:formatNumber value="${tot_order }" pattern="#,###" /> 원</div>
        </td>
        <td style='width: 50%;'>
          <br>
           <button type='button' id='btn_payment' class="btn btn-outline-dark float-right" style='font-size: 1.5em;'>결재하기</button>
        </td>
      </tr>
    </tbody>
  </table>   
     
  </FORM>
  </DIV>

<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>

</html>
