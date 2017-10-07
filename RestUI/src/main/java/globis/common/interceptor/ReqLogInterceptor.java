package globis.common.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.com.cmm.service.Globals;
import globis.common.log.service.CommonLogService;
import globis.common.login.service.UserSessionVO;
import globis.common.util.ReqUtil;


public class ReqLogInterceptor extends HandlerInterceptorAdapter {
	
	@Resource(name = "CommonLogService")
	private CommonLogService CommonLogService;
	
	 long oldTime; // 타이머가 ON 되었을 때의 시각을 기억하고 있는 변수

	
	/**
	 * 세션에 계정정보(LoginVO)가 있는지 여부로 인증 여부를 체크한다.
	 * 계정정보(LoginVO)가 없다면, 로그인 페이지로 이동한다.
	 */
	@Override
	public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception{	
		oldTime = System.currentTimeMillis();
		return true;
	}

	
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(Globals.FORWARD_SERVER_YN.equals("Y"))return;
		try{
			String processTime = Long.toString(System.currentTimeMillis() - oldTime);
			//String requestURI      = request.getRequestURI(); //요청 URI
			
			String url = request.getServletPath();
	        if(request.getQueryString()!=null){
	        	url+="?"+request.getQueryString();
	        }
	        
			HttpSession session = request.getSession();
			
			int userIdx  = -1;
    		if(session.getAttribute("userPub")!=null){
    			UserSessionVO userSession =  (UserSessionVO)session.getAttribute("userPub");
    			userIdx = userSession.getMemSeqNo();
    		}
    		

//    		Enumeration params = request.getParameterNames();
//    		while (params.hasMoreElements()){
//    		    String name = (String)params.nextElement();
//    		    System.out.println(name + " : " +request.getParameter(name));
//    		}
    		
    		String errorMsg = "";
    		String errorDetail = "";
    		String errorYn = "N";
			if (ex != null) {
				errorDetail = exceptionStacktraceToString(ex);
				errorMsg = ex.toString();
				errorYn = "Y";
			}
    		
			String reqClient = ReqUtil.getClientOsInfo(request)
					+" "+ReqUtil.getClientBit(request)
					+" "+ReqUtil.getClientWebKind(request)
					+" : "+ReqUtil.getClientUserAgent(request);
			
			Map<String,Object> reqMap = new HashMap<String,Object>();
    		reqMap.put("userIdx", 		userIdx);
    		reqMap.put("ip", 			ReqUtil.getClientIp(request));
    		reqMap.put("reqUrl", 		url);
    		reqMap.put("errorMsg", 		errorMsg);
    		reqMap.put("errorDetail", 	errorDetail);
    		reqMap.put("errorYn", 		errorYn);
    		reqMap.put("processTime", 	processTime);
    		reqMap.put("reqClient", 	reqClient);
    		
    		//메뉴 접속 로그 생성
    		//CommonLogService.insertLogReq(reqMap);

		}catch (Exception e){
			//e.printStackTrace();
		}
	}
	
	private String exceptionStacktraceToString(Exception e)
	{	
		String errorMsg = "";
		ByteArrayOutputStream baos=null;
		PrintStream ps=null;
		try {
		    baos = new ByteArrayOutputStream();
		    ps = new PrintStream(baos);
		    e.printStackTrace(ps);
		    errorMsg = baos.toString();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally{
			if(ps!=null){
				try {ps.close();} catch (Exception e1) {}
			}
			if(baos!=null){
				try {baos.close();;} catch (Exception e1) {}
			}
		}
	    return errorMsg;
	}
	
	
}
