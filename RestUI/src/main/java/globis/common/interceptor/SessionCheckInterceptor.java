package globis.common.interceptor;

import globis.common.code.service.CommonCodeService;
import globis.common.login.service.MenuSessionVO;
import globis.common.util.ReqUtil;
import globis.common.util.UrlUtil;
import globis.common.login.service.CommonLoginService;
import java.net.URLEncoder;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;




public class SessionCheckInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionCheckInterceptor.class);
	private Set<String> permittedURL;
	
	@Resource(name = "commonLoginService")
	private CommonLoginService commonLoginService;
		
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	
	public void setPermittedURL(Set<String> permittedURL) {
		this.permittedURL = permittedURL;
	}
	
    @Override
    public boolean preHandle(HttpServletRequest request, 
            HttpServletResponse response,
            Object handler) throws Exception {
    	HttpSession session = request.getSession();
    	
        String url = request.getServletPath();
        
        if(request.getQueryString()!=null){
        	url+="?"+request.getQueryString();
        }
        
        String accessPermitIp = commonCodeService.getCodeNm("SYSCONFIG","ACCESS_PERMIT_IP");
        String reqIp = ReqUtil.getClientIp(request);
        LOGGER.info("==========> "+accessPermitIp);
        LOGGER.info("==========> "+reqIp);
        if(accessPermitIp!=null&& !accessPermitIp.equals("") && accessPermitIp.indexOf(reqIp) == -1){
        	response.sendRedirect(request.getContextPath()+"/common/error.jsp");
			return false;
    	}
        
        boolean isPermittedURL = false; 
        
        String noSSO = request.getParameter("noSSO");
        
        if (noSSO == null) noSSO = "N";
		
		//LOG.debug(" == AuthenticInterceptor ========= LoginVO         ["+loginVO+"]");
		//LOG.debug(" == AuthenticInterceptor ========= requestURI      ["+requestURI+"]");

		permittedURL = new HashSet<String>();
		permittedURL.add("/board/[a-zA-Z0-9\\.\\&\\=\\?\\,\\_\\%]+");
		//permittedURL.add("/[a-zA-Z0-9]+\\.do");		
		//permittedURL.add("/[a-zA-Z0-9\\.\\&\\=\\?\\,\\_\\%]+");
		
        LOGGER.debug("세션 체크:"+url);
        
        if(UrlUtil.isUrlPath(url)){
        	
    		for(Iterator<String> it = this.permittedURL.iterator(); it.hasNext();){
    			String urlPattern = (String) it.next();
    			//LOGGER.debug(">>>>>>>>>>>  urlPattern:"+urlPattern);
    			//LOGGER.debug(">>>>>>>>>>>  urlPattern:"+url);

    			if( Pattern.matches(urlPattern, url) ){// 정규표현식을 이용해서 요청 URI가 허용된 URL에 맞는지 점검함.
    				LOGGER.debug(" == AuthenticInterceptor ========= urlPattern["+urlPattern+"] requestURI ["+url+"] isPermittedURL["+isPermittedURL+"]");
    				isPermittedURL = true;
    				break;
    			}
    		}   
    		
        	if(isPermittedURL){
    			LOGGER.debug(" == AuthenticInterceptor ========= 제외 url "+url);
    			session.setAttribute("lastPath",url);    			
        	} else {
        		if(session.getAttribute("userPub")==null && noSSO.equals("N")){
					String userId = "";
					if(!userId.isEmpty()){				
						Map<String, Object> commandMap = new HashMap();
						commandMap.put("userId", userId);
						commandMap.put("ssoYn", "Y");
						commonLoginService.login(commandMap, session, request);
					}
				}
        		
        		if(!url.startsWith("/index.do")){
					if(session.getAttribute("userPub")==null){
						LOGGER.debug("세션 미존재:"+url);
						response.sendRedirect(request.getContextPath()+"/index.do?msg="+URLEncoder.encode("로그인하여 주십시요.", "UTF-8")+"&returnUrl="+URLEncoder.encode(url, "UTF-8")+"&noSSO=Y");
						return false;
					}else{
						if(url.startsWith("/common/")||url.equals("/main.do")){
							LOGGER.debug("common은 세션체크만 하고 패스");
						}else{
							List<MenuSessionVO> menuSessionList =  (List<MenuSessionVO>)session.getAttribute("menu");
							MenuSessionVO currMenuVO = new MenuSessionVO();
							
							boolean auth = false;
							
							for(int i=0;menuSessionList.size()>i;i++){
								
								if(UrlUtil.isEqualPathAndParam(menuSessionList.get(i).getMenuPath(), url)){
									auth = true;
									currMenuVO = menuSessionList.get(i);
									break;
								}
							}
							
							if(auth){
								//메뉴에 쓰기권한이 없고, url이 쓰기 일 경우 권한 미존재
								if(!currMenuVO.getWriteAbleYn().equals("Y") && UrlUtil.isEditPath(url)){
									LOGGER.debug("쓰기 권한 없음:"+url+",권한:"+currMenuVO.getWriteAbleYn());
									response.sendRedirect(request.getContextPath()+"/index.do?msg="+URLEncoder.encode("쓰기 권한이 존재하지 않습니다.", "UTF-8")+"&noSSO=Y");
									return false;	
								}else{
									LOGGER.debug("권한 존재:"+url);
									session.setAttribute("lastPath",url);
								}
							}else{
								LOGGER.debug("권한 미존재:"+url);
								response.sendRedirect(request.getContextPath()+"/index.do?msg="+URLEncoder.encode("권한이 존재하지 않습니다.", "UTF-8")+"&noSSO=Y");
								return false;
							}
						}
					}
        		}
	        }
		}
        return super.preHandle(request, response, handler);
    }
 
    @Override
    public void postHandle(HttpServletRequest request, 
            HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    } 
 
    @Override
    public void afterCompletion(HttpServletRequest request, 
            HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
 
}

