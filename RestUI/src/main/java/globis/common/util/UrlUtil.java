package globis.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Class Name : UrlUtil.java
 * @Description : Url 패턴 분석 Class
 * @Modification Information
 * @
 * @  수정일      		수정자                수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2015.02.10   	도정훈 	       최초생성
 *
 * @author 그로비스인포텍 프레임웍 개발팀
 * @since 2015. 02.10
 * @version 1.0
 */

public class UrlUtil{
	
	public UrlUtil() {
	
	}
	static public String getDir(String url){
		if(url!=null){
			if(url.indexOf("?")>-1){
				url = url.substring(0,url.lastIndexOf("?"));
			}
			return url.substring(0,url.lastIndexOf("/")+1);
		}else{
			return "";
		}
	}
	
	static public String getQueryParam(String url){
		String rtn = "";
		if(url!=null && url.lastIndexOf("?")>-1){
			rtn = url.substring(url.lastIndexOf("?")+1);
		}
		return rtn;
	}
	
	static public boolean isEditPath(String url){
		if(url==null) return false;
		boolean rtn = false;
		String fileName = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf(".do")+3);
		if(fileName.startsWith("insert")
				||fileName.startsWith("update")
				||fileName.startsWith("delete")
				||fileName.startsWith("edit")
				){
			rtn = true;
		}
		return rtn;
	}
	
	static public boolean isEqualPathAndParam(String menuUrl,String reqUrl){
		if(menuUrl==null||menuUrl.equals("")) return false;
		if(reqUrl==null||reqUrl.equals("")) return false;
		boolean rtn = false;
		if(getDir(menuUrl).indexOf(getDir(reqUrl))==0){
			if(getQueryParam(menuUrl).isEmpty()){
				rtn = true;
			}else if(getQueryParam(reqUrl).indexOf( getQueryParam(menuUrl))>-1){
				rtn = true;
			}
		}
		return rtn;
	}
	
	static public String getDir(HttpServletRequest request){
		return getDir(request.getServletPath());
	}
	static public boolean isUrlPath(String url){
		if(url==null) return false;
		boolean rtn = false;
		if(url.substring(url.length()-3).equals(".do")
				|| url.indexOf(".do")>-1){
			rtn = true;
		}
		return rtn;
	}
	static public boolean isUrlPath(HttpServletRequest request){
		return isUrlPath(request.getServletPath());
	}
	
}