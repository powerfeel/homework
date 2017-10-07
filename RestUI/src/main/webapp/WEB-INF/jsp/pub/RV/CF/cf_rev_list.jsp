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
        	$("#searchBtn").click(function(){
        		//Validation
        		
        		
        			$.ajax({
            			url		 : "<c:url value='/common/sessionCheck.do'/>",
            			data	 : "",
            			type	 : "POST",
            			dataType : "json",
            			success : function( sessionCheck ){
            				//세션 검증 후 실행  하기
            				if(sessionCheck.result == '1'){
            					$.ajax({
	    							url		 : "${ifURL}/relIF/IF/CONFRENCE/LIST",
	    							data	 : $("#cfSerarch").serialize(),
	    							type	 : "POST",
	    							dataType : "json",							
	    							success : function( data ){
	    								var parsedJson = JSON.parse(data.result); 
										var rst = parsedJson.rst;
										var list = parsedJson.list;
										var totalCnt = parsedJson.totalCnt;
										
										var content = "";
										
										//검색시 기존 데이터 초기화
										$("#cfRevList > tr").remove();
									
										
										if(totalCnt > 0){										
											for(var i=0; i<totalCnt; i++){
												content = "";
												var revSeqno = list[i].revSeqNo;
												content += "<tr style=\"text-align: center;\">";
												content += "<td>"+ revSeqno +"</td>";
												content += "<td><a href='javascript:goDetail("+ revSeqno +");'>"+ list[i].revTitle +"</a></td>";
												content += "<td>"+ list[i].revItem +"</td>";
												content += "<td>"+ list[i].revNm +"</td>";
												content += "<td>"+ list[i].revYmd + " " + list[i].revStime + "시 ~ " + list[i].revEtime +"시</td>";
												content += "</tr>";
												
												$("#cfRevList").append(content);
							                }
										}else{
											content = "";
											content += "<tr style=\"text-align: center;\">";
											content += "<td colspan='5'>검색된 정보가 없습니다.</td>";
											content += "</tr>";
											$("#cfRevList").append(content);
										}
										
										
	    							}
	    						});	
            				}else{
            					alert('세션이 만료 되었습니다.');
            					$(location).attr("href","<c:url value="/DS/LG/s_logout.do"/>");	            					
            				}
            			}
            		});// End SessionCheck + FormSubmit
    			
        		
            });

        	//달력 바인딩
			$("#revYmd").bindDate({align:"right", valign:"bottom", 
				
			});
		
        });
        
        function goDetail(revSeqNo){        	
        	$(location).attr("href","<c:url value="/RV/CF/cf_rev_detail.do"/>?revSeqNo="+revSeqNo);
        }

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
			<br>
			<div class="section-header relative">
				<h2 class="pageHeader-title">회의실 조회</h2>				
			</div>
			<br>
			<form id="cfSerarch" name="cfSerarch" method="post">
				<input type="hidden" id="revSeqNo" 	name="revSeqNo" value="">
				<div>
					<div class="box-typical box-typical-padding tableLike">
						<table border="1" >
							<thead>
								<tr>
									<th style="width: 50px;">순번</th>
									<th style="width: 250px;">제목</th>								
									<th style="width: 100px;">예약항목</th>
									<th style="width: 100px;">등록자</th>
									<th style="width: 200px;">예약시간</th>
								</tr>
							</thead>
							<tbody id="cfRevList">
								<tr style="text-align: center;"><td colspan="5">검색된 정보가 없습니다.</td></tr>
							</tbody>			
						</table>
					</div>
				</div>
				<br/>
				<br/>
				<div class="clearfix buttonWrapper">
					<table>
						<tr>
							<th>
								예약일
							</th>
							<td>
								<div class="calendar">
									<input id="revYmd" name="revYmd" type="text" readonly="readonly" value="" maxlength="10" class="form-control" data-axbind="date">
								</div>
							</td>
						</tr>
						<tr>
							<th>
								예약자
							</th>
							<td>
								<input type="text" id="revNm" name="revNm">
							</td>
						</tr>
						<tr>
							<th>
								회의실
							</th>
							<td>
								<select name="revItem" id="revItem">
									<option value="">선택하세요</option>
									<option value="CF001">1번 대회의실</option>
									<option value="CF002">2번 중회의실</option>
									<option value="CF003">3번 소의실</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<input type="button" value="회의실 조회" id="searchBtn">
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