package pub.RV.CF.web;

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
public class RVCFController {

	/** log */
    protected static final Log LOGGER = LogFactory.getLog(RVCFController.class);
	
	/**
	 * 회의실 등록 화면
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @param model ModelMap
	 * @param session HttpSession
	 * @return view 경로
	 * @exception Exception
	 */
	@RequestMapping(value = "/RV/CF/cf_rev_write.do")
	public String cfRevWrite(@CommandMap Map<String, Object> commandMap, ModelMap model, HttpSession session) throws Exception {
		
		//세션 처리
		UserSessionVO userVO = (UserSessionVO)session.getAttribute("userPub");
		
		//세션이 없으면 강제로 로그아웃 시킴
		if(userVO == null){
			commandMap.put("commandMap", null);
			session.invalidate();
			return "redirect:/";
		}else{
			model.addAttribute("commandMap", commandMap);
			return "RV/CF/cf_rev_write";
		}
	}
	
	/**
	 * 회의실 목록 조회
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @param model ModelMap
	 * @param session HttpSession
	 * @return view 경로
	 * @exception Exception
	 */
	@RequestMapping(value = "/RV/CF/cf_rev_list.do")
	public String cfRevList(@CommandMap Map<String, Object> commandMap, ModelMap model, HttpSession session) throws Exception {
		
		//세션 처리
		UserSessionVO userVO = (UserSessionVO)session.getAttribute("userPub");
		
		//세션이 없으면 강제로 로그아웃 시킴
		if(userVO == null){
			commandMap.put("commandMap", null);
			session.invalidate();
			return "redirect:/";
		}else{
			model.addAttribute("commandMap", commandMap);
			return "RV/CF/cf_rev_list";
		}
	}
	
	
	/**
	 * 회의실 상세 정보 조회
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @param model ModelMap
	 * @param session HttpSession
	 * @return view 경로
	 * @exception Exception
	 */
	@RequestMapping(value = "/RV/CF/cf_rev_detail.do")
	public String cfRevDetail(@CommandMap Map<String, Object> commandMap, ModelMap model, HttpSession session) throws Exception {
		
		//세션 처리
		UserSessionVO userVO = (UserSessionVO)session.getAttribute("userPub");
		
		//세션이 없으면 강제로 로그아웃 시킴
		if(userVO == null){
			commandMap.put("commandMap", null);
			session.invalidate();
			return "redirect:/";
		}else{
			model.addAttribute("commandMap", commandMap);
			return "RV/CF/cf_rev_detail";
		}
	}
}