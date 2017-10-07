<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Globals.SystemName" var="SystemName"/>

<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="Content-Script-Type" content="text/javascript">
	<meta http-equiv="Content-Style-Type" content="text/css">	
	<title>${SystemName}</title>
	<link href="/pub/images/pub/favicon.ico" rel="shortcut icon" type="image/x-icon" />
	<%-- --%>
	<link rel="stylesheet" href="/pub/css/pub/AXJ.css">
	<link rel="stylesheet" href="/pub/css/pub/AXInput.css">
	
	<link rel="stylesheet" href="/pub/css/pub/default.css">	

	<script src="/pub/js/pub/jquery-1.11.2.min.js"></script>
	<script src="/pub/js/pub/AXJ.js"></script>
	<script src="/pub/js/pub/AXInput.js"></script>
	<script src="/pub/js/pub/tether.min.js"></script>
	
	<script src="/pub/js/pub/select2.full.min.js"></script>
	<script src="/pub/js/pub/moment.min.js"></script>
			
	<script src="/pub/js/pub/bootstrap.min.js"></script>	
	<script src="/pub/js/pub/daterangepicker.js"></script>	
	<script src="/pub/js/common.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
			//footer 위치 고정
        	$('.footer').css({position: 'fixed'});
			
			//alert 호출 후 ESC버튼 클릭 동작
			$(window).keydown(function(event){
			    if(event.keyCode == 27) {
			    	mask.close();
			    }
		  	});
			
			$("#loginBtn").on("click", function(){
				var objForm = $("#loginForm");
	
				if($("#id").val() == "" || $("#pwd").val() == ""){
					CommonUtil.alert("사번과 비밀번호를 입력하여 주십시오.");
		   			return false;
				}
	
				$.ajax({
					url		 : "/pub/DS/LG/s_loginSelAjaxActCnt.do",
					data	 : objForm.serialize(),
					type	 : "POST",
					dataType : "json",
					success : function( data ){
						if(data.result == "00"){
							CommonUtil.alert("존재하지 않는 사번 이거나 틀린 비밀번호 입니다."); 
							return false;
						} else if (data.result == "01") {
							CommonUtil.alert("미승인 직원입니다(인사팀에게 문의 바랍니다.).");
						} else if (data.result == "02") {
							// 정상로그인 main페이지로 이동
							$("#loginForm").attr("action", "/pub/RV/MP/s_main.do").submit();
						} else if (data.result == "03"){
							CommonUtil.alert("사용이 중지된 직원 입니다(인사팀에게 문의 바랍니다.).");
						} else if (data.result == "04"){
							CommonUtil.alert("퇴사 직원은 사용이 불가 합니다.");
						} 
					},
					error : function(xhr, status, error) {
						CommonUtil.alert("에러발생" + error);
			        }
				});
			});
		});
		
		function enterChk(){
			if(event.keyCode==13){
				$("#loginBtn").trigger("click");
			}
		}
	</script>
</head>

<body onkeypress="javascript:enterChk();">
<div class="wrap">
	<div class="header">
		<div class="header_content">
			<ul class="logo">
				<li><a href="/pub/">NAVER LABS</a></li>
			</ul>			
		</div>
	</div>
	<div class="container">
		<div class="login_box">
			<h1>로그인</h1>

			<h2>회의실 예약 시스템</h2>
			<h3>
			사번과 비밀 번호를 입력 해주세요 <br>
			해당 시스템은 NAVER LABS 임직원만 사용이 가능 합니다.<br>
			</h3>
			<form id="loginForm" name="loginForm" method="post">
			<input id="id" name="id" type="text" class="input" placeholder="사번" value='1234'>
			<input id="pwd" name="pwd" type="password" class="input" placeholder=" 비밀번호" value="1234"><br>
			</form>
			<button class="btn_login" id="loginBtn">로그인</button>
		</div>
	</div>
	<!-- content end -->

	<jsp:include page="/DS/include/bottomInc.do"/>
</div>
</body>
</html>
