package pub.DS.DB.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.ptl.mvc.bind.annotation.CommandMap;
import globis.common.login.service.UserSessionVO;


@Controller
public class DSDBController {

	/** log */
    protected static final Log LOGGER = LogFactory.getLog(DSDBController.class);
    
    /**
	 * headInc 페이지 include, 세션 존재여부 체크  
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @param model ModelMap
	 * @param session HttpSession
	 * @return jsp경로 css, js 선언 페이지 경로 
	 * @exception Exception
	 */
	@RequestMapping(value = "/DS/include/headInc.do")
	public String headInc(@CommandMap Map<String, Object> commandMap, ModelMap model, HttpSession session) throws Exception {	
		
		UserSessionVO userVO = (UserSessionVO)session.getAttribute("userPub");
		LOGGER.info("/DS/include/headInc.do pubMemMgtNo========> "+userVO);
		model.addAttribute("userVO", userVO);
		
		return "DS/include/headInc";
	}
	
	/**
	 * topInc 페이지 include, 세션 존재여부 체크  
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @param model ModelMap
	 * @param session HttpSession
	 * @return jsp경로 최상단 Top 링크 페이지 경로 
	 * @exception Exception
	 */
	@RequestMapping(value = "/DS/include/topInc.do")
	public String topInc(@CommandMap Map<String, Object> commandMap, ModelMap model, HttpSession session) throws Exception {	
		
		UserSessionVO userVO = (UserSessionVO)session.getAttribute("userPub");
		LOGGER.info("/DS/include/topInc.do pubMemMgtNo========> "+userVO);
		model.addAttribute("userVO", userVO);
		
		return "DS/include/topInc";
	}
	
	/**
	 * navInc 페이지 include, 세션 존재여부 체크  
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @param model ModelMap
	 * @param session HttpSession
	 * @return jsp경로 대메뉴 링크 페이지 경로 
	 * @exception Exception
	 */
	@RequestMapping(value = "/DS/include/navInc.do")
	public String navInc(@CommandMap Map<String, Object> commandMap, ModelMap model, HttpSession session) throws Exception {	
		
		UserSessionVO userVO = (UserSessionVO)session.getAttribute("userPub");
		LOGGER.info("/DS/include/navInc.do pubMemMgtNo========> "+userVO);
		model.addAttribute("userVO", userVO);
		
		return "DS/include/navInc";
	}
	
	/**
	 * bottomInc 페이지 include
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @param model ModelMap
	 * @param session HttpSession
	 * @return jsp경로 footer 페이지 경로 
	 * @exception Exception
	 */
	@RequestMapping(value = "/DS/include/bottomInc.do")
	public String bottomInc(@CommandMap Map<String, Object> commandMap, ModelMap model, HttpSession session) throws Exception {	
		LOGGER.info("/DS/include/bottomInc.do");
		return "DS/include/bottomInc";
	}
}