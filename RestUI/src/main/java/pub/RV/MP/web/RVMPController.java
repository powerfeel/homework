package pub.RV.MP.web;

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
public class RVMPController {

	/** log */
    protected static final Log LOGGER = LogFactory.getLog(RVMPController.class);
	
	/**
	 * 로그인 사용자 메인 화면
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @param model ModelMap
	 * @param session HttpSession
	 * @return jsp경로 대시보드 페이지 경로, model로 각 데이터, 리스트 add 
	 * @exception Exception
	 */
	@RequestMapping(value = "/RV/MP/s_main.do")
	public String s_main(@CommandMap Map<String, Object> commandMap, ModelMap model, HttpSession session) throws Exception {
		
		//세션 처리
		UserSessionVO userVO = (UserSessionVO)session.getAttribute("userPub");
		
		//세션이 없으면 강제로 로그아웃 시킴
		if(userVO == null){
			commandMap.put("commandMap", null);
			session.invalidate();
			return "redirect:/";
		}else{
			model.addAttribute("commandMap", commandMap);
			return "RV/MP/s_main";
		}
	}
}