<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Globals.SystemName" var="SystemName"/>

	<meta charset="utf-8">
	<meta http-equiv="Content-Script-Type" content="text/javascript">
	<meta http-equiv="Content-Style-Type" content="text/css">	
	<title>${SystemName}</title>
	<link href="/pub/images/pub/favicon.ico" rel="shortcut icon" type="image/x-icon" />
	 
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
	
	<script src="/pub/js/pub/jquery.methodOverride.js"></script>
	<script src="/pub/js/common.js"></script>
	
	<script type="text/javascript">
		$(function(){
			if(("${userVO.memSeqNo}" == null || "${userVO.memSeqNo}" == "") && "${commandMap.manual}" != "Y"){
				CommonUtil.alert("세션이 초기화되어 로그아웃 처리됩니다.",function(){
					location.href = "<c:url value="/"/>";
					return false;
				});
			}
			
		});
				
	</script>