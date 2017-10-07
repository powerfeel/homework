package globis.common.login.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.Globals;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.com.utl.slm.EgovHttpSessionBindingListener;
import egovframework.com.utl.slm.EgovMultiLoginPreventor;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import globis.common.log.service.CommonLogService;
import globis.common.login.service.CommonLoginService;
import globis.common.login.service.MenuSessionVO;
import globis.common.login.service.UserSessionVO;
import globis.common.util.ReqUtil;
import globis.common.util.StringUtil;



@Service("commonLoginService")
public class CommonLoginServiceImpl extends EgovAbstractServiceImpl implements CommonLoginService {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonLoginServiceImpl.class);
	
    @Resource(name="commonLoginMapper")
	private CommonLoginMapper commonLoginMapper;
    
    @Resource(name = "CommonLogService")
	private CommonLogService CommonLogService;
    
    //List<MenuSessionVO> menuList = new ArrayList<MenuSessionVO>();
    //String firstPath = "";
    /*
    String msg = "";
    public String getLoginMsg(){
    	return msg;
    }
    */
    /**
	 * 로그인 데이터를 조회하고 세션을 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록 
	 * @exception Exception
	 */
    public Map login( Map<String, Object> commandMap,HttpSession session,HttpServletRequest request) throws Exception {
    	Map rtnMap = new HashMap();
    	String msg=""; 
    	//SEEDUtil.encryptMapId(commandMap, "userId");
    	String userId=(String)commandMap.get("userId");
    	String password=(String)commandMap.get("password");
    	String ssoYn=(String)StringUtil.nvl(commandMap.get("ssoYn"),"");
    	Map<String,Object> empl = commonLoginMapper.selectEmplMap(userId);
    	String passwordEncParam;
    	boolean rtn = false;
    	if (empl != null) {
    		String ip = ReqUtil.getClientIp(request);
    		 
    		String userIdx = String.valueOf(empl.get("userIdx"));
   			passwordEncParam = EgovFileScrty.encryptPassword(password);
    		String passwordEnc = String.valueOf((String)empl.get("passwordEnc"));
    		String limitIp = (String)empl.get("limitIp");
    		if(limitIp!=null && !limitIp.equals("")){
    			if(limitIp.indexOf(ip)==-1){
	    			msg = "허용 IP에 포함되어 있지 않은 IP로 접속하여<br/>로그인 할 수 없습니다.<br/><br/>"
							+ "접속자 IP : "+ip+"<br/>"
							+ "허용 IP : "+limitIp+"<br/>";
	    			rtnMap.put("result", false);
	    	    	rtnMap.put("msg", msg);
	    	    	return rtnMap;
    			}
    		}
    		
    		/** leftInc.jsp에 hidden 필드 추가 (한전 sso) **/
    		if(ssoYn.equals("Y")||(!passwordEnc.isEmpty() && passwordEncParam.equals(passwordEnc))){
	    		UserSessionVO sessionVO = new UserSessionVO();
	    		String loginMethod = "ID/PASSWORD";
	    		if(ssoYn.equals("Y")){
	    			loginMethod = "SSO";
	    		}
	    		Map<String,Object> reqMap = new HashMap<String,Object>();
        		reqMap.put("userIdx", userIdx);
        		reqMap.put("ip", ip);
        		reqMap.put("loginMethod", loginMethod);
        		CommonLogService.insertLogLogin(reqMap);
        		//long logIdx = (Long)reqMap.get("logIdx");
        		
	    		//SEEDUtil.decryptMap(empl);
	    		sessionVO.setUserIdx(userIdx);
    			sessionVO.setUserId(String.valueOf(empl.get("userId")));
    			sessionVO.setUserNm((String)empl.get("userNm"));
	    		sessionVO.setDeptIdx(String.valueOf(empl.get("deptIdx")));
	    		sessionVO.setDeptId((String)empl.get("deptId"));
	    		sessionVO.setDeptNm((String)empl.get("deptNm"));
	    		sessionVO.setPstCd((String)empl.get("pstCd"));
	    		sessionVO.setPstNm((String)empl.get("pstNm"));
    			sessionVO.setEmail((String)empl.get("email"));
	    		sessionVO.setMngDeptIdx(String.valueOf(empl.get("mngDeptIdx")));
	    		sessionVO.setMngDeptId((String)empl.get("mngDeptId"));
	    		sessionVO.setMngDeptNm((String)empl.get("mngDeptNm"));
	    		sessionVO.setIp(ip);
	    		sessionVO.setLastLoginDt((String)empl.get("lastLoginDt"));
	    		sessionVO.setLoginDt((String)empl.get("loginDt"));
        		//sessionVO.setLogIdx(String.valueOf(logIdx));
        		sessionVO.setLoginMethod(loginMethod);
	    		session.setAttribute("user", sessionVO);
	    		
	    		List<MenuSessionVO> menuList = getMenuList(userIdx);
	    		
	    		if (menuList == null) { 
	    			menuList = getCommonMenuList();
	    		}
	    		session.setAttribute("menu", menuList);
	    		updateLastLoginDt(sessionVO.getUserIdx());
	    		
				
				if(Globals.LOGIN_MULTI_YN.equals("N")){
					//중복로그인 메세지 표시시작
					UserSessionVO multiSessioVO = EgovMultiLoginPreventor.getMultiLoginSessionVO(userIdx);
					if(multiSessioVO!=null){ 
						if(!ip.equals(multiSessioVO.getIp())){//로그인 ip가 다를경우만 메세지
							msg = "다른IP에서 이미 로그인되어 있는 사용자가 존재하여 해당 사용자를 로그아웃 처리 하였습니다.<br/><br/>"
									+ "로그인 시각 : "+multiSessioVO.getLoginDt()+"<br/>"
									+ "로그인 IP : "+multiSessioVO.getIp()+"<br/>"
									+ "로그인 방법 : "+multiSessioVO.getLoginMethod();
						}
					}
					//중복로그인 메세지 표시끝
					
			    	//중복 로그인 방지 처리 끝
			    	EgovHttpSessionBindingListener listener = new EgovHttpSessionBindingListener();
			    	request.getSession().setAttribute(userIdx, listener);
			    	//중복 로그인 방지 처리 끝
				}
				rtn = true;
    		}else{
    			msg = "아이디와 비밀번호를 확인하여 주십시요.";
    		}
    	}else{
    		msg = "아이디와 비밀번호를 확인하여 주십시요.";
    	}
    	rtnMap.put("result", rtn);
    	rtnMap.put("msg", msg);
    	return rtnMap;
    }

    /**
	 * 메뉴리스트를조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록 
	 * @exception Exception
	 */
    private List<MenuSessionVO> getMenuList(String userIdx) throws Exception {
    	
    	List<Map<String,Object>> allList = commonLoginMapper.selectAuthMenu(userIdx);
    	List<MenuSessionVO> menuList = new ArrayList<MenuSessionVO>();
    	
    	//메뉴 경로가 존재하지 않을 경우 하위 첫번째 메뉴 부여
    	for(int i=0; i < allList.size(); i++){
    		Map<String,Object> map = allList.get(i);
    		String menuPath = "";
    		if(map.get("menuPath")!=null)menuPath = (String)map.get("menuPath");
    		String menuId = (String)map.get("menuId");
    		if(menuPath.equals("")){
    			menuPath = getSubUrl(allList,menuId);
    		}
    		allList.get(i).put("menuChildPath",menuPath);
    	}
    	
    	//메뉴 경로가 존재하지 않을 경우 메뉴 제거
    	for(int i = allList.size() - 1; i >= 0; i--){
    		Map<String,Object> map = allList.get(i);
    		String menuChildPath = (String)map.get("menuChildPath");
    		if(menuChildPath.isEmpty()){
    			allList.remove(i);
    		}
    	}
    	
    	//하위 메뉴 tree대로 정렬
    	for(int i=0;i<allList.size();i++){
    		Map<String,Object> map = allList.get(i);
    		String menuId = (String)map.get("menuId");
    		String parentMenuId = (String)map.get("parentMenuId");
    		//String menuPath = (String)map.get("menuPath");
    		if(parentMenuId.equals("root")){
    			int level = 1;
    			allList.get(i).put("level", level);
    			addMenuList(menuList,allList.get(i));
    			setChildMenu(menuList,allList,menuId,level+1);
    		}
    	}
    	
    	return menuList;
    }
    
    /**
	 * 메뉴리스트를조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록 
	 * @exception Exception
	 */
    public List<MenuSessionVO> getCommonMenuList() throws Exception {
    	
    	List<Map<String,Object>> allList = commonLoginMapper.selectCommonMenu();
    	List<MenuSessionVO> menuList = new ArrayList<MenuSessionVO>();
    	
    	//메뉴 경로가 존재하지 않을 경우 하위 첫번째 메뉴 부여
    	for(int i=0; i < allList.size(); i++){
    		Map<String,Object> map = allList.get(i);
    		String menuPath = "";
    		
    		if(map.get("menuPath") != null) {
    			menuPath = (String)map.get("menuPath");
    		}
    		
    		String menuId = (String)map.get("menuId");
    		
    		if(menuPath.equals("")){
    			menuPath = getSubUrl(allList, menuId);
    		}
    		
    		allList.get(i).put("menuChildPath",menuPath);
    	}
    	
    	//메뉴 경로가 존재하지 않을 경우 메뉴 제거
    	for(int i=allList.size()-1;i>=0;i--){
    		Map<String,Object> map = allList.get(i);
    		String menuChildPath = (String)map.get("menuChildPath");
    		if(menuChildPath.isEmpty()){
    			allList.remove(i);
    		}
    	}
    	
    	//하위 메뉴 tree대로 정렬    	
    	for(int i=0; i < allList.size(); i++){
    		Map<String,Object> map = allList.get(i);
    		
    		String menuId = (String)map.get("menuId");
    		String parentMenuId = (String)map.get("parentMenuId");
    		
    		//String menuPath = (String)map.get("menuPath");
    		
    		if(parentMenuId.equals("root")){
    			int level = 1;
    			allList.get(i).put("level", level);
    			addMenuList(menuList, allList.get(i));
    			setChildMenu(menuList, allList, menuId, level+1);
    		} 
    	}
		
    	for(int i = 0; i < allList.size(); i++){
    		boolean isIncluded = false;
    		Map<String,Object> map = allList.get(i);
    		String menuId = (String)map.get("menuId");
    		
    		for(int j = 0; j < menuList.size(); j++){
    			if (menuList.get(j).getMenuId() == menuId) {
    				isIncluded = true;
    			}
    		}
    		
    		if (!isIncluded) {
    			int level = 1;
    			allList.get(i).put("level", level);
    			addMenuList(menuList, allList.get(i));
    		}
    	}
    	
    	
    	return menuList;
    }
    
    private void setChildMenu(List<MenuSessionVO> menuList,List<Map<String,Object>> allList,String parentMenuId,int level) throws Exception {
    	for(int i=0;i<allList.size();i++){
    		if(allList.get(i).get("level")==null&&
    				((String)allList.get(i).get("parentMenuId")).equals(parentMenuId)
    				){
    			allList.get(i).put("level", level);
    			addMenuList(menuList,allList.get(i));
    			setChildMenu(menuList,allList,(String)allList.get(i).get("menuId"),level+1);
			}
    	}
    }
    
	//selectAllMenu 에서 호출
	private String getSubUrl(List<Map<String,Object>> allList,String parentMenuIdArg){
		String befMenuPath = "";
		for(int i=0;i<allList.size();i++){
    		Map<String,Object> map = allList.get(i);
    		String parentMenuId = (String)map.get("parentMenuId");
    		String menuId = (String)map.get("menuId");
    		String menuPath = "";
    		if(map.get("menuPath")!=null)menuPath=(String)map.get("menuPath");
    		if(parentMenuIdArg.equals(parentMenuId)){
    			if(!menuPath.equals("")){
    				befMenuPath = menuPath;
    			}else{
    				befMenuPath = getSubUrl(allList,menuId);
    			}
    			if(!befMenuPath.equals("")){
    				break;
    			}
    		}
    	}
		return befMenuPath;
	}

    private void addMenuList(List<MenuSessionVO> menuList,Map<String,Object> map) throws Exception {
    	//if(firstPath.isEmpty())firstPath = (String)map.get("menuChildPath");
    	MenuSessionVO menuVO = new MenuSessionVO();
    	
		menuVO.setMenuId((String)map.get("menuId"));
		menuVO.setMenuPath((String)map.get("menuPath"));
		menuVO.setMenuChildPath((String)map.get("menuChildPath"));
		menuVO.setParentMenuId((String)map.get("parentMenuId"));
		menuVO.setMenuNm((String)map.get("menuNm"));
		menuVO.setAuthScopeCd((String)map.get("authScopeCd"));
		menuVO.setWriteAbleYn((String)map.get("writeAbleYn"));
		menuVO.setLevel((Integer)map.get("level"));
		menuVO.setMenuChildCnt(String.valueOf(map.get("menuChildCnt")));
		menuVO.setIconCd((String)map.get("iconCd"));
		menuVO.setMenuPosition((String)map.get("menuPosition"));
		menuVO.setMenuComment((String)map.get("menuComment"));
		
		menuList.add(menuVO);
    }
    
    
	/**
	* 
	* 네비게이션 메뉴를 조회한다
	* @param menuSessionList - 세션의 전체 메뉴
	* @return top 메뉴 목록
	* @exception Exception
	*/
	public List<Map<String,Object>> getCurrentMenuNavi(String menuId) throws Exception {
		   
		  List<Map<String,Object>> list =  commonLoginMapper.selectMenuNavi(menuId);
	   
		  return list; 
	}
	
	
	/**
	* 
	* 마지막 로그인일시를 업데이트 한다.
	* @param userIdx - 세션의 전체 메뉴
	* @return top 메뉴 목록
	* @exception Exception
	*/
	public void updateLastLoginDt(String userIdx) throws Exception {		   
		commonLoginMapper.updateLastLoginDt(userIdx);
	}

}
