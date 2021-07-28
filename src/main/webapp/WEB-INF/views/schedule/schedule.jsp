<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>

<c:set var="classno" value="${classVO.classno }" />

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

<style>
  table{
    border: 2px solid #d2d2d2;
    border-collapse: collapse;
    font-size: 0.9em;
  }
  th, td{
    border: 1px solid #d2d2d2;
    border-collapse: collapse;
  }
  th{
    height: 5px;
    text-align: center;
  }
  td {
    width: 100px;
    height: 60px;
  }
</style>

<script type="text/javascript">
var classno = [];
var classname = [];
var starttime = [];
var endtime = [];
var cday = [];
var professor = [];
var textbook = [];

//window.addEventListener('load', load(memberno));

function load(memberno) {
  var params = "";
  params = 'memberno=' + memberno; // 공백이 값으로 있으면 안됨.
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
          classno[key] = rdata[key].classno;
          classname[key] = rdata[key].classname;
          starttime[key] = rdata[key].starttime;
          endtime[key] = rdata[key].endtime;
          cday[key] = rdata[key].cday;
        }
        console.log(classno.length);
        for(var i =0;  i<classno.length; i++){
          classno[i]; 
          classname[i];
          starttime[i];
          endtime[i];
          cday[i];
          console.log(cday[i]);
          for (cday[i]; cday[i]<=5; cday[i]++){
            for (starttime[i]; starttime[i]<=endtime[i]; starttime[i]++){
              $('#'+starttime[i]+'_'+cday[i]).attr('rowspan', (endtime[i]-starttime[i])); 
              $('#'+starttime[i]+'_'+cday[i]).html('<h4><A href="/schedule/read_by_classno.do?classno='+ classno[i] +'">'+classname[i]+'</A></h4>');
              $('#'+starttime[i]+'_'+cday[i]).css('background', randomColor());
              
              for (starttime[i]; starttime[i]<endtime[i]-1; starttime[i]++){
                console.log((Number(starttime[i])+1)+[i]);
                $('#'+(Number(starttime[i])+1)+'_'+cday[i]).remove();
              }
              break;
            }
            break;
          }
        }
        
      },
      // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
      error: function(request, status, error) { // callback 함수
        console.log(error);
      }
    }
  );  //  $.ajax END
}

function randomColor() {
  var colorCode ='#'+Math.round(Math.random() * 0xffffff).toString(16);
  return colorCode;
}

</script>
</head> 
 
<body>
<jsp:include page="../menu/top.jsp" />
  <div class="container">
    <div class="col-md-6 col-md-offset-3" style="text-align: center;">                        
      <h3>시간표</h3>
    </div>
    <div class="col-sm-6 col-md-offset-3" style="text-align: center;">
      <table id="scheduler" width=100% height=600 style="color: #121212; text-align: center; margin-bottom: 30px;">
        <thead width=10% height=30>
          <tr id="tr_day">
            <th id="th_time"></th>
            <th>월</th>
            <th>화</th>
            <th>수</th>
            <th>목</th>
            <th>금</th>
          </tr>
        <thead>
        <tbody id="tbody">
          <%-- -----1교시----- --%>
          <tr id="tr_9">
            <th>9시</th>
            <td id="9_0"></td>
            <td id="9_1"></td>
            <td id="9_2"></td>
            <td id="9_3"></td>
            <td id="9_4"></td>
          </tr>
          
          <%-- -----2교시----- --%>
          <tr id="tr_10">
            <th>10시</th>
            <td id="10_0"></td>
            <td id="10_1"></td>
            <td id="10_2"></td>
            <td id="10_3"></td>
            <td id="10_4"></td>
          </tr>
          
          <%-- -----3교시----- --%>
          <tr id="tr_11">
            <th>11시</th>
            <td id="11_0"></td>
            <td id="11_1"></td>
            <td id="11_2"></td>
            <td id="11_3"></td>
            <td id="11_4"></td>
          </tr>
          
          <%-- -----4교시----- --%>
          <tr id="tr_12">
            <th>12시</th>
            <td id="12_0"></td>
            <td id="12_1"></td>
            <td id="12_2"></td>
            <td id="12_3"></td>
            <td id="12_4"></td>
          </tr>
          
          <%-- -----5교시----- --%>
          <tr id="tr_13">
            <th>1시</th>
            <td id="13_0"></td>
            <td id="13_1"></td>
            <td id="13_2"></td>
            <td id="13_3"></td>
            <td id="13_4"></td>
          </tr>
          
          <%-- -----6교시----- --%>
          <tr id="tr_14">
            <th>2시</th>
            <td id="14_0"></td>
            <td id="14_1"></td>
            <td id="14_2"></td>
            <td id="14_3"></td>
            <td id="14_4"></td>
          </tr>
          
          <%-- -----7교시----- --%>
          <tr id="tr_15">
            <th>3시</th>
            <td id="15_0"></td>
            <td id="15_1"></td>
            <td id="15_2"></td>
            <td id="15_3"></td>
            <td id="15_4"></td>
          </tr>
          
          <%-- -----8교시----- --%>
          <tr id="tr_16">
            <th>4시</th>
            <td id="16_0"></td>
            <td id="16_1"></td>
            <td id="16_2"></td>
            <td id="16_3"></td>
            <td id="16_4"></td>
          </tr>
          
          <%-- -----9교시----- --%>
          <tr id="tr_17">
            <th>5시</th>
            <td id="17_0"></td>
            <td id="17_1"></td>
            <td id="17_2"></td>
            <td id="17_3"></td>
            <td id="17_4"></td>
          </tr>
          
          <%-- -----10교시----- --%>
          <tr id="tr_18">
            <th>6시</th>
            <td id="18_0"></td>
            <td id="18_1"></td>
            <td id="18_2"></td>
            <td id="18_3"></td>
            <td id="18_4"></td>
          </tr>
        </tbody>
      </table>
      
      <button type="button" class="btn btn-primary" onclick="load(${sessionScope.memberno})">시간표 불러오기</button>
      <button type="button" class="btn btn-primary" onclick="location.href='./list.do?memberno=${sessionScope.memberno }'">시간표 추가하기</button> <br><br><br>
    </div>
  </div> <!-- container// -->
<jsp:include page="../menu/bottom.jsp"  flush='false' />
</body>
 
</html>

