<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
     response.setStatus(200);
%>
<!DOCTYPE html>
<html lang="kor">

<head>
	<meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Secure Shape</title>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <link rel="stylesheet" href="/pub/css/pub/bootstrap.css">
    <link rel="stylesheet" href="/pub/css/pub/style.css">
    <link rel="stylesheet" href="/pub/css/pub/sp_style.css">
</head>



<body>

	<div class="container">

		<div class="page-center">
			<div class="page-center-in">
				<div class="container-fluid">
					<div class="page-error-box">
						<div class="error-title">잘못된 호출 입니다.</div>
						<a href="javascript:history.back(-1);" class="btn btn-rounded">이전 페이지</a>

						<div class="logo">
							<img src="/pub/images/pub/logo_04.png" alt=""/>
						</div>
					</div>
				</div>
			</div>
		</div><!--.page-center-->

	</div>

</body>

</html>