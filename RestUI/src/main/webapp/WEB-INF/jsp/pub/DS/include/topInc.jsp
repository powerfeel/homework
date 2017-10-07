<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<c:set var="goLogoUrl" value=""/>
	<c:set var="logoImageURL" value="/pub/images/pub/logo_04.png"/>
	
	<c:choose>
		<c:when test="${null ne userVO.memSeqNo}">
			<c:set var="goLogoUrl" value="/pub/RV/MP/s_main.do"/>
		</c:when>
		<c:otherwise>
			<c:set var="goLogoUrl" value="/pub/"/>
		</c:otherwise>
	</c:choose>

	<!-- top start -->
	<div class="header">
		<div class="header_content">
			<ul class="logo">
				<li><a href="${goLogoUrl}">NAVER LABS</a></li>
			</ul>
			<ul class="logout">
				<c:choose>
					<c:when test="${null ne userVO.memSeqNo}">
						<li><a href="/pub/DS/LG/s_logout.do">로그아웃</a></li>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
	<!-- top start -->
