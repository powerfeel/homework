package globis.common.login.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public interface CommonLoginService {

    /**
	 * 글 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
	//public String getLoginMsg();
	Map login( Map<String, Object> commandMap,HttpSession session,HttpServletRequest request) throws Exception;
	
   /**
	* 세션체크가 필요없은 공통 메뉴리스트를 조회한다.
	* @param menuSessionList - 세션의 전체 메뉴
	* @return top 메뉴 목록
	* @exception Exception
	*/
	List<MenuSessionVO> getCommonMenuList() throws Exception;
	
   /**
	* 네비게이션 메뉴를 조회한다.
	* @param menuSessionList - 세션의 전체 메뉴
	* @return top 메뉴 목록
	* @exception Exception
	*/
	List<Map<String,Object>> getCurrentMenuNavi(String menuId) throws Exception;
}
