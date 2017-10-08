<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html lang="ko">
<head>
	<jsp:include page="/DS/include/headInc.do"/>
	<script type="text/javaScript">		
        $(function(){
			//footer 위치 고정
        	$('.footer').css({position: 'fixed'});

        });
	</script>
</head>
<body>
<div class="wrap">
	<jsp:include page="/DS/include/topInc.do"/>
	
	<div class="container">
		<jsp:include page="/DS/include/navInc.do"/>
		
		<!-- content start -->
		<div class="container_content">
			<div class="content_header">
				<h2 class="content_tit"><p>예약실 관리 시스템</p></h2>
			</div>
			<div class="content_middle">
				<h4 class="content">
					<p>
					회의실 예약 모듈과 I/F 모듈로 분리되어 있음<br>
					로그인 계정은 1234 / 1234로 접속 가능 합니다.<br>
					IF 모듈을 다른 Tomcat에 기동시 globals.properties Globals.IF.URL 값을 변경해야 함<br>
					<br>
					</p>					
				</h4>
			</div>
		</div>
		<!-- content end -->
	</div>
	
	<jsp:include page="/DS/include/bottomInc.do"/>
</div>
</body>
</html>
