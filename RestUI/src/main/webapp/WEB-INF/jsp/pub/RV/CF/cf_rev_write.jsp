<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Globals.IF.URL" var="ifURL"/>
<!DOCTYPE html>
<html lang="kor">
<head>
    <!-- Head Include Start -->
	<jsp:include page="/DS/include/headInc.do"/>
	<!-- Head Include End -->
	
	<script type="text/javaScript">		
        $(function(){
        	
        	//등록 버튼 클릭
        	$("#regBtn").click(function(){
        		//Validation
        		
        		if(confirm('회의실을 예약 하시겠습니까?') == true){
        			$.ajax({
            			url		 : "<c:url value='/common/sessionCheck.do'/>",
            			data	 : "",
            			type	 : "POST",
            			dataType : "json",
            			success : function( sessionCheck ){
            				//세션 검증 후 실행  하기
            				if(sessionCheck.result == '1'){
            					$.ajax({
	    							url		 : "${ifURL}/relIF/IF/CONFRENCE",
	    							data	 : $("#conferenceReg").serialize(),
	    							type	 : "POST",
	    							dataType : "json",							
	    							success : function( data ){

	    								//성공시 리스트 화면을 이동
	    								if(data.rst){
	    									alert('회의실 예약이 성공 되었습니다.');
	    									$(location).attr('href','<c:url value="/RV/CF/cf_rev_list.do"/>');
	    								}else{
	    									alert(data.msg);		    									
	    								}
	    							}
	    						});	
            				}else{
            					alert('세션이 만료 되었습니다.');
            					$(location).attr("href","<c:url value="/DS/LG/s_logout.do"/>");	            					
            				}
            			}
            		});// End SessionCheck + FormSubmit
        		}	//end confirm       			
        		
            });

        	//달력 바인딩
			$("#revYmd").bindDate({align:"right", valign:"bottom", 
				
			});
		
        });

	</script>
</head>
<body>
	<!-- Site Top Include Start -->
	<jsp:include page="/DS/include/topInc.do"/>
	<!-- Site Top Include End -->
	
	<!-- Site Nav Include Start -->
	<jsp:include page="/DS/include/navInc.do"/>
	<!-- Site Nav Include End -->
	<!-- Page Contents Start -->
	<div class="page-content" style="padding-left: 400px;">
		<div class="container">
			<div class="section-header relative">
				<h2 class="pageHeader-title">회의실 등록</h2>				
			</div>
			<form id="conferenceReg" name="conferenceReg" method="post">
				<input type="hidden" id="revId" 	name="revId" 	value="${userPub.id}">
				<input type="hidden" id="revNm" 	name="revNm" 	value="${userPub.name}">
				<div>
					<div class="box-typical box-typical-padding tableLike">
						<table>
							<tr>
								<th style="width: 150px;">제목 *</th>
								<td>
									<input type="text" id="revTitle" name="revTitle" maxlength="50">
								</td>
							</tr>
							<tr>
								<th style="width: 150px;">회의실 *</th>
								<td>
									<select name="revItem" id="revItem">
										<option value="CF001">1번 대회의실</option>
										<option value="CF002">2번 중회의실</option>
										<option value="CF003">3번 소의실</option>
									</select>
								</td>
							</tr>
							<tr>
								<th style="width: 150px;">예약일 *</th>
								<td>
									<div class="calendar">
										<input id="revYmd" name="revYmd" type="text" readonly="readonly" value="" maxlength="10" class="form-control" data-axbind="date">										
									
									<select name="revStime" id="revStime">
										<option value="9">9시</option>
										<option value="10">10시</option>
										<option value="11">11시</option>
										<option value="12">12시</option>
										<option value="13">13시</option>
										<option value="14">14시</option>
										<option value="15">15시</option>
										<option value="16">16시</option>
										<option value="17">17시</option>
										<option value="18">18시</option>
									</select>
									~
									<select name="revEtime" id="revEtime">
										<option value="9">9시</option>
										<option value="10">10시</option>
										<option value="11">11시</option>
										<option value="12">12시</option>
										<option value="13">13시</option>
										<option value="14">14시</option>
										<option value="15">15시</option>
										<option value="16">16시</option>
										<option value="17">17시</option>
										<option value="18">18시</option>
									</select>
									</div>
								</td>
							</tr>
							<tr>
								<th style="width: 150px;">회의참석자 * </th>
								<td>
									 <input type="text" id="revMem" name="revMem" maxlength="100">
								</td>
							</tr>
							<tr>
								<th style="width: 150px;">회의내용</th>
								<td>
									<input type="text" id="revCont" name="revCont" maxlength="100">
								</td>
							</tr>
						</table>
					</div>
				</div>
				<br/>
				<br/>
				<div class="clearfix buttonWrapper">
					<table>
						<tr>
							<td>
								<input type="button" id="regBtn" value="회의실 예약">
							</td>
						</tr>
					</table>					
				</div>	
			</form>
		</div>
	</div>
	<!-- page-contents End-->

	<script type="text/javascript">
		$('.select2-no-search-arrow').select2({
			minimumResultsForSearch: "Infinity",
			theme: "arrow"
		});
	</script>
	
	<!-- Site Footer Include Start -->	
	<jsp:include page="/DS/include/bottomInc.do"/>
	<!-- Site Footer Include End -->

</body>
</html>