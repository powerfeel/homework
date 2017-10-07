package globis.common.util;

import java.net.InetAddress;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.Globals;

public class ReqUtil {
	private static final Logger LOG = Logger.getLogger(ReqUtil.class.getClass());
	
	public static String getClientIp(HttpServletRequest request ) throws Exception{
		//-Djava.net.preferIPv4Stack=true
		String ip = request.getHeader("X-Forwarded-For");
				
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		    ip = request.getHeader("Proxy-Client-IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		    ip = request.getHeader("WL-Proxy-Client-IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		    ip = request.getHeader("HTTP_CLIENT_IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		    ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		    ip = request.getRemoteAddr();  
		}
		return ip;
	}
	
	public static String getServerIp() throws Exception{
		InetAddress address = InetAddress.getLocalHost(); 
	    return address.getHostAddress();
	}
    
	/**
	 * 클라이언트(Client)의 OS 정보를 조회하는 기능
	 * @param HttpServletRequest request Request객체
	 * @return String osInfo OS 정보
	 * @exception Exception
	*/
	public static String getClientOsInfo(HttpServletRequest request) throws Exception {
		String user_agent = request.getHeader("user-agent");
		String os_info = "";
		//String os_info = user_agent.toUpperCase().split(";")[2].split("\\)")[0];
			
		if( user_agent.indexOf("NT 5.1") != -1 ){
			os_info ="Windows XP";
		}else if( user_agent.indexOf("NT 5.2") != -1 ){
			os_info ="Windows 2003";
		}else if( user_agent.indexOf("NT 6.0") != -1 ){
			os_info ="Windows Vista/Server 2008";
		}else if( user_agent.indexOf("NT 6.1") != -1 ){
			os_info ="Windows 7";
		}else if( user_agent.indexOf("NT 6.2") != -1 ){
			os_info ="Windows 8";
		}else if( user_agent.indexOf("NT 6.3") != -1 ){
			os_info ="Windows 8.1";
		}else if( user_agent.indexOf("NT 6.4") != -1|| user_agent.indexOf("NT 10.0") != -1 ){
			os_info ="Windows 10";
		}else if( user_agent.indexOf("Linux") != -1 ){
			os_info ="Linux";
		}else{
			os_info ="기타";
		}
		return os_info;
	}
	
	public static String getClientUserAgent(HttpServletRequest request) throws Exception {
		return request.getHeader("user-agent");
	}
	/**
	 * 클라이언트(Client)의 웹브라우저 종류를 조회하는 기능
	 * @param HttpServletRequest request Request객체
	 * @return String webKind 웹브라우저 종류
	 * @exception Exception
	*/
	public static String getClientWebKind(HttpServletRequest request) throws Exception {
		String user_agent = request.getHeader("user-agent");
		// 웹브라우저 종류 조회
		String webKind = "";
		if (user_agent.toUpperCase().indexOf("GECKO") != -1) {
			if (user_agent.toUpperCase().indexOf("NESCAPE") != -1) {
				webKind = "Netscape (Gecko/Netscape)";
			} else if (user_agent.toUpperCase().indexOf("FIREFOX") != -1) {
				webKind = "Mozilla Firefox (Gecko/Firefox)";
			}else if (user_agent.indexOf("Trident/4.0") != -1) {
				webKind = "Internet Explorer 8";
			} else if (user_agent.indexOf("Trident/5.0") != -1) {
				webKind = "Internet Explorer 9";
			} else if (user_agent.indexOf("Trident/6.0") != -1) {
				webKind = "Internet Explorer 10";
			} else if (user_agent.indexOf("Trident/7.0") != -1) {
				webKind = "Internet Explorer 11";
			} else {
				webKind = "Mozilla (Gecko/Mozilla)";
			}
		} else if (user_agent.toUpperCase().indexOf("MSIE") != -1) {
			if (user_agent.toUpperCase().indexOf("OPERA") != -1) {
				webKind = "Opera (MSIE/Opera/Compatible)";
			} else {
				webKind = "Internet Explorer (MSIE/Compatible)";
			}
		} else if (user_agent.toUpperCase().indexOf("SAFARI") != -1) {
			if (user_agent.toUpperCase().indexOf("CHROME") != -1) {
				webKind = "Google Chrome";
			} else {
				webKind = "Safari";
			}
		} else if (user_agent.toUpperCase().indexOf("THUNDERBIRD") != -1) {
			webKind = "Thunderbird";
		} else {
			webKind = "Other Web Browsers";
		}
		return webKind;
	}
	
	
	/**
	 * 클라이언트(Client)의 bit
	 * @param HttpServletRequest request Request객체
	 * @return String webKind 웹브라우저 종류
	 * @exception Exception
	*/
	public static String getClientBit(HttpServletRequest request) throws Exception {
		String user_agent = request.getHeader("user-agent");
		String bit = "32";
		if (user_agent.indexOf("WOW64")>-1 || user_agent.indexOf("Win64")>-1){
			bit = "64";
		}
		return bit;
	}
	
	
	/**
	 * 클라이언트(Client)의 웹브라우저 버전을 조회하는 기능
	 * @param HttpServletRequest request Request객체
	 * @return String webVer 웹브라우저 버전
	 * @exception Exception
	*/
	public static String getClientWebVer(HttpServletRequest request) throws Exception {
		String user_agent = request.getHeader("user-agent");
		// 웹브라우저 버전 조회
		String webVer = "";
		String [] arr = {"MSIE", "OPERA", "NETSCAPE", "FIREFOX", "SAFARI"};
		for (int i = 0; i < arr.length; i++) {
			int s_loc = user_agent.toUpperCase().indexOf(arr[i]);
			if (s_loc != -1) {
				int f_loc = s_loc + arr[i].length();
				webVer = user_agent.toUpperCase().substring(f_loc, f_loc+5);
				webVer = webVer.replaceAll("/", "").replaceAll(";", "").replaceAll("^", "").replaceAll(",", "").replaceAll("//.", "");
			}
		}
		return webVer;
	}
	public static void setNull(Map<String,Object> commandMap,String key) throws Exception {
		if(commandMap.get(key)==null){
			commandMap.put(key, null);
        }else{
        	if(commandMap.get(key) instanceof String){
        		if(((String)commandMap.get(key)).equals("")||((String)commandMap.get(key)).equals("null")){
        			commandMap.put(key, null);
        		}
        	}
        }
	}
	
	
	
}
