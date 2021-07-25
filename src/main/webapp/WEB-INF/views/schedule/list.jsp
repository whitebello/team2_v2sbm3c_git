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
 
<!-- /static 기준 -->
<!-- Core theme CSS (includes Bootstrap)-->
<link href="../css/style.css" rel="stylesheet" type="text/css">
 
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<!-- Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />

<!-- Favicon-->
<link rel="icon" type="/image/x-icon" href="assets/favicon.ico" />
    
<script type="text/javascript">
  var classno_arr = [];
  var classname_arr = [];
  var starttime_arr = [];
  var endtime_arr = [];
  var cday_arr = [];
  var params = "";
  
  params = 'memberno=' + <%=(int)session.getAttribute("memberno")%>
  // console.log("params", params);
  
  $(function() {
    $('#btn_update_cancel').on('click', cancel);
    $('#btn_delete_cancel').on('click', cancel);
  });

  function cancel() {
    $('#panel_create').css("display","");  
    $('#panel_update').css("display","none"); 
    $('#panel_delete').css("display","none");
  }
  
  window.addEventListener('load', load(params));  // 페이지 이동되자마자 load함수실행
  function load(params){
    $.ajax(
        {
          url: '/schedule/load.do',
          type: 'get',  // get, post
          cache: false, // 응답 결과 임시 저장 취소
          async: true,  // true: 비동기 통신
          dataType: 'json', // 응답 형식: json, html, xml...
          data: params,      // 데이터
          success: function(rdata) {
            for (key in rdata){
              classno_arr[key] = rdata[key].classno;
              classname_arr[key] = rdata[key].classname;
              starttime_arr[key] = rdata[key].starttime;
              endtime_arr[key] = rdata[key].endtime;
              cday_arr[key] = rdata[key].cday;
            }
            
          },
          // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
          error: function(request, status, error) { // callback 함수
            console.log(error);
          }
        }
      );  //  $.ajax END
  }
  
  function create(memberno) {
    // console.log("create 함수진입 시발 왜안되냐고옴니ㅏ비ㅏ절 ;")
    var frm_create = $('#frm_create');
    
    var memberno = $('#memberno', frm_create).val();
    var cday = $('#cday', frm_create).val();
    var starttime = $('#starttime', frm_create).val();
    var endtime = $('#endtime', frm_create).val();
    var professor = $('#professor', frm_create).val();
    var textbook = $('#textbook', frm_create).val();
    var classname = $('#classname', frm_create).val();   
 
    isOverlap(starttime, endtime);  // 시간교차판단 함수

    
    alert("강의가 등록되었습니다. 시간표 불러오기를 통해 확인해주세요.");
    frm_create.submit();
  }
  
  function isOverlap(starttime, endtime) {
    if (Number(starttime) >= Number(endtime)) {
      alert('시간을 다시 설정해주세요');
      return false;
    }
  }

  function convCday(cday){
    switch(cday){
      case "월요일":
        cday = 0;
        break;
      case "화요일":
        cday = 1;
        break;
      case "수요일":
        cday = 2;
        break;
      case "목요일":
        cday = 3;
        break;
      case "금요일":
        cday = 4;
        break;
    }
    return cday;
  }
  
  // 수정폼
  function read_update_ajax(bookgrpno) {
    $('#panel_create').css("display","none"); // hide, 태그를 숨김 
    $('#panel_delete').css("display","none"); // hide, 태그를 숨김
    $('#panel_update').css("display",""); // show, 숨겨진 태그 출력 
    
    // console.log('-> bookgrpno:' + bookgrpno);
    var params = "";
    // params = $('#frm').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
    params = 'bookgrpno=' + bookgrpno; // 공백이 값으로 있으면 안됨.

    $.ajax(
      {
        url: '/bookgrp/read_ajax.do',
        type: 'get',  // get, post
        cache: false, // 응답 결과 임시 저장 취소
        async: true,  // true: 비동기 통신
        dataType: 'json', // 응답 형식: json, html, xml...
        data: params,      // 데이터
        success: function(rdata) { // 응답이 온경우, Spring에서 하나의 객체를 전달한 경우 통문자열
          // {"bookgrpno":1,"visible":"Y","seqno":1,"rdate":"2021-04-08 17:01:28","name":"문화"}
          var bookgrpno = rdata.bookgrpno;
          var name = rdata.name;
          var seqno = rdata.seqno;
          var rdate = rdata.rdate;

          var frm_update = $('#frm_update');
          $('#bookgrpno', frm_update).val(bookgrpno);
          $('#name', frm_update).val(name);
          $('#seqno', frm_update).val(seqno);
          $('#rdate', frm_update).val(rdate);
          
        },
        // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
        error: function(request, status, error) { // callback 함수
          console.log(error);
        }
      }
    );  //  $.ajax END

  } 

  // 삭제 폼(자식 레코드가 없는 경우의 삭제)
  function read_delete_ajax(bookgrpno) {
    $('#panel_create').css("display","none"); // hide, 태그를 숨김
    $('#panel_update').css("display","none"); // hide, 태그를 숨김  
    $('#panel_delete').css("display",""); // show, 숨겨진 태그 출력 
    // return;
    
    // console.log('-> bookgrpno:' + bookgrpno);
    var params = "";
    // params = $('#frm').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
    params = 'bookgrpno=' + bookgrpno; // 공백이 값으로 있으면 안됨.
    
    $.ajax(
      {
        url: '/bookgrp/read_ajax.do',
        type: 'get',  // get, post
        cache: false, // 응답 결과 임시 저장 취소
        async: true,  // true: 비동기 통신
        dataType: 'json', // 응답 형식: json, html, xml...
        data: params,      // 데이터
        success: function(rdata) { // 응답이 온경우, Spring에서 하나의 객체를 전달한 경우 통문자열
          // {"bookgrpno":1,"visible":"Y","seqno":1,"rdate":"2021-04-08 17:01:28","name":"문화"}
          var bookgrpno = rdata.bookgrpno;
          var name = rdata.name;
          var seqno = rdata.seqno;
          // var rdate = rdata.rdate;
          var count_by_bookgrpno = parseInt(rdata.count_by_bookgrpno);
          console.log('count_by_bookgrpno: ' + count_by_bookgrpno);

          var frm_delete = $('#frm_delete');
          $('#bookgrpno', frm_delete).val(bookgrpno);
          
          $('#frm_delete_name').html(name);
          $('#frm_delete_seqno').html(seqno);
          
          if (count_by_bookgrpno > 0) { // 자식 레코드가 있다면
      
            $('#frm_delete_count_by_bookgrpno_panel').html('관련자료 ' + count_by_bookgrpno + ' 건');
            $('#frm_delete_count_by_bookgrpno').show();

            // alert($('#a_list_by_bookgrpno').attr('href')); // ../book/list_by_bookgrpno.do?bookgrpno=
            $('#a_list_by_bookgrpno').attr('href', '../book/list_by_bookgrpno.do?bookgrpno=' + bookgrpno);
            
          } else {
            $('#frm_delete_count_by_bookgrpno').hide();
          }
          // console.log('-> btn_recom: ' + $('#btn_recom').val());  // X
          // console.log('-> btn_recom: ' + $('#btn_recom').html());
          // $('#btn_recom').html('♥('+rdata.recom+')');
          // $('#span_animation').hide();
        },
        // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
        error: function(request, status, error) { // callback 함수
          console.log(error);
        }
      }
    );  //  $.ajax END

    // $('#span_animation').css('text-align', 'center');
    // $('#span_animation').html("<img src='/contents/images/ani04.gif' style='width: 8%;'>");
    // $('#span_animation').show(); // 숨겨진 태그의 출력
  } 
  
</script>
 
</head> 
 
<body>
<jsp:include page="../menu/top.jsp" />
 
 
 <section class="py-5">

  <DIV class='container c_bottom_10'> 
    <DIV class='title_line'>
      <a href="./list.do" class='link-primary'>시간표 입력</a>
    </DIV>
          
    <!-- 등록 -->
    <DIV id='panel_create' 
            style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; 
                      text-align: center;'>
      <FORM name='frm_create' id='frm_create' method='POST' action='./create.do'>
        <input type='hidden' name='memberno' id='memberno' value='${sessionScope.memberno}'>
        <label>강의명</label>
        <input type='text' name='classname' id='classname' value='' required="required" style='width: 10%;'
                   autofocus="autofocus">
        <label>요일</label>
        <input type='text' name='cday' id='cday' value='' required="required" 
                  min='9' max='18' step='1' style='width: 10%;'>
        <label>교수</label>
        <input type='text' name='professor' id='professor' value='' required="required" 
                  min='9' max='18' step='1' style='width: 10%;'>
        <label>교재</label>
        <input type='text' name='textbook' id='textbook' value='' required="required" 
                  min='9' max='18' step='1' style='width: 10%;'>
        <label>시작시간</label>
        <input type='number' name='starttime' id='starttime' value='9' required="required" 
                  min='9' max='18' step='1' style='width: 5%;'>
        <label>종료시간</label>
        <input type='number' name='endtime' id='endtime' value='18' required="required" 
                  min='9' max='18' step='1' style='width: 5%;'>
          
        <button type="button" onclick="create(${sessionScope.memberno})" class="btn btn-dark">저장</button>
        <button type="button" onclick="cancel();"  class="btn btn-dark">취소</button>
      </FORM>
    </DIV>
    
    <!-- 강의 수정 -->
    <DIV id='panel_update' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9;
            width: 100%; text-align: center; display: none;'>
      <FORM name='frm_create' id='frm_create' method='POST' action='./create.do'>
     
      
        <label>강의명</label>
        <input type='text' name='name' id='name' value='' required="required" style='width: 25%;'
                   autofocus="autofocus">
    
        <label>순서</label>
        <input type='number' name='seqno' id='seqno' value='1' required="required" 
                  min='1' max='1000' step='1' style='width: 5%;'>
         
         <button type="submit" id='submit' class="btn btn-dark">등록</button>
         <button type="button" onclick="cancel();"  class="btn btn-dark">취소</button>
      </FORM>
    </DIV>
  
    <%-- 삭제 --%>
    <DIV id='panel_delete' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; 
            width: 100%; text-align: center; display: none;'>
      <div class="msg_warning">강의를 삭제하시겠습니까?</div>
      <FORM name='frm_delete' id='frm_delete' method='POST' action='./delete.do'>
        <input type='hidden' name='bookgrpno' id='bookgrpno' value=''>
          
        <label>강의 번호</label><span id='frm_delete_name'></span>  
        <label>강의명</label>: <span id='frm_delete_seqno'></span>
        
        <%-- 자식 레코드 갯수 출력 --%>
        <div id='frm_delete_count_by_bookgrpno' 
               style='color: #FF0000; font-weight: bold; display: none; margin: 10px auto;'>
          <span id='frm_delete_count_by_bookgrpno_panel'></span>     
          『<A id='a_list_by_bookgrpno' href="">관련 자료 삭제하기</A>』
        </div>
         
        <button type="submit" id='submit' class="btn btn-dark">삭제</button>
         <button type="button" onclick="cancel();"  class="btn btn-dark">취소</button>
      </FORM>
    </DIV>
      
     <TABLE class='table mt-5'>
        <colgroup>
          <col style='width: 10%;'/>
          <col style='width: 30%;'/>
          <col style='width: 10%;'/> 
          <col style='width: 10%;'/>
          <col style='width: 10%;'/>
          <col style='width: 20%;'/>
          <col style='width: 10%;'/>
        </colgroup>
       
        <thead>  
         <TR class="table_title">
          <TH class="th_bs">강의번호</TH>
          <TH class="th_bs">강의명</TH>
          <TH class="th_bs">시작시간</TH>
          <TH class="th_bs">요일</TH>
          <TH class="th_bs">교수</TH>
          <TH class="th_bs">교제</TH>
          <TH class="th_bs">기타</TH>
        </TR>
        </thead>
      
        <tbody>
        <c:forEach var="scheduleVO" items="${list }">
          <c:set var="classno" value="${scheduleVO.classno }" />
          <TR>
            <TD class="td_bs">${scheduleVO.classno }</TD>
            <TD class="td_bs">${scheduleVO.classname }</TD>
            <TD class="td_bs">${scheduleVO.starttime }</TD>
            <TD class="td_bs">${scheduleVO.cday }</TD>
            <TD class="td_bs">${scheduleVO.professor }</TD>
            <TD class="td_bs">${scheduleVO.textbook }</TD>
            <TD class="td_bs">
              <A href="javascript: read_update_ajax(${scheduleVO.classno })" title="수정"><span class="glyphicon glyphicon-pencil"></span></A>
              <A href="javascript: read_delete_ajax(${scheduleVO.classno })" title="삭제"><span class="glyphicon glyphicon-trash"></span></A>  
            </TD>   
          </TR>   
        </c:forEach> 
        </tbody>
     
    </TABLE>
  </DIV>
</section>


 
<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>
