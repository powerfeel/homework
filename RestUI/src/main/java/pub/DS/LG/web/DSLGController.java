package pub.DS.LG.web;

import egovframework.rte.ptl.mvc.bind.annotation.CommandMap;
import globis.common.log.service.CommonLogService;
import globis.common.login.service.UserSessionVO;
import globis.common.util.ReqUtil;
import globis.common.util.ResUtil;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import pub.DS.LG.service.DSLGService;


@Controller
public class DSLGController {

	/** log */
    protected static final Log LOGGER = LogFactory.getLog(DSLGController.class);
    
    /** DSLGService */
	@Resource(name = "DSLGService")
    private DSLGService DSLGService;
	
	@Resource(name = "CommonLogService")
	private CommonLogService CommonLogService;
	
	/**
	 * 로그인 화면
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @param model ModelMap
	 * @param session HttpSession
	 * @return jsp경로 로그인 페이지 경로 
	 * @exception Exception
	 */
	@RequestMapping(value = "/DS/LG/s_login.do")
	public String s_login(@CommandMap Map<String, Object> commandMap, ModelMap model, HttpSession session) throws Exception {	
		
		model.addAttribute("commandMap", commandMap);
		return "DS/LG/s_login";
	}
	
	/**
	 * 로그인 체크 ( select cnt )
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @param model ModelMap
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param session HttpSession
	 * @return json으로 결과값 반환
	 * @exception Exception
	 */
	@RequestMapping(value = "/DS/LG/s_loginSelAjaxActCnt.do")
	public void s_loginSelAjaxActCnt(
			@CommandMap Map<String, Object> commandMap, 
			ModelMap model, 
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session
	) throws Exception {
		
		Map<String,Object> pub_map = DSLGService.s_loginSelAjaxActCnt(commandMap);
		
		String result = "";
		long cnt = (Long) pub_map.get("cnt");
		String memStat = (String) pub_map.get("memStat");
		
		if ( cnt == 0 ) {
			// 아이디, 암호가 틀림
			result = "00";
		} else {
			
			if( memStat.equals("02") ) {
				// 로그인 처리 (세션에 정보 저장)
				LOGGER.info("로그인성공후 세션저장====>");
				
				Map<String,Object> mem_map = DSLGService.s_loginSelAct(commandMap);
				
				//세션 생성 객체
				UserSessionVO userVO = new UserSessionVO();
				
				userVO.setMemSeqNo((Integer)mem_map.get("memSeqNo"));
				userVO.setId((String)mem_map.get("sabun"));
				userVO.setName((String)mem_map.get("name"));				
				
				//세선 저장
				session.setAttribute("userPub", userVO);
				
				//로그인 성공시 로그 DB 저정
				if(userVO != null){
					String ip = ReqUtil.getClientIp(request);
			  	  
					Map<String,Object> reqMap = new HashMap<String,Object>();
					reqMap.put("userIdx", (Integer)mem_map.get("memSeqNo"));
					reqMap.put("ip", ip);
					reqMap.put("loginMethod", "USER_LOGIN");
					CommonLogService.insertLogLogin(reqMap);
				}
				
				result = "02";
			} else if ( memStat.equals("01") ) {
				// 승인대기
				result = "01";
			} else if ( memStat.equals("03") ) {
				// 탈퇴신청
				result = "03";
			} else if ( memStat.equals("04") ) {
				// 탈퇴
				result = "04";
			}
		}
		
		ResUtil.printResultJson(response, result);
	}
	
	/**
	 * 로그아웃 시 로그테이블에 로그정보를 남긴다.
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @param model ModelMap
	 * @param request HttpServletRequest
	 * @param session HttpSession
	 * @return jsp경로 유통사 회원 로그인 페이지 경로
	 * @exception Exception
	 */
	@RequestMapping(value = "/DS/LG/s_logout.do")
	public String s_logout(@CommandMap Map<String, Object> commandMap, ModelMap model, HttpSession session, HttpServletRequest request) throws Exception {
		
		//세션 처리
		UserSessionVO userVO = (UserSessionVO)session.getAttribute("userPub");
		if(userVO != null){
			String ip = ReqUtil.getClientIp(request);
		  	  
			Map<String,Object> reqMap = new HashMap<String,Object>();
			reqMap.put("userIdx", (Integer) commandMap.get("_memSeqNo"));
			reqMap.put("ip", ip);
			reqMap.put("loginMethod", "USER_LOGOUT");
			CommonLogService.insertLogLogin(reqMap);
		}
		
		commandMap.put("commandMap", null);
		session.invalidate();
		return "redirect:/";
	}
}