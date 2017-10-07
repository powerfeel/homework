package pub.DS.LG.service;

import java.util.Map;


public interface DSLGService {
    
	/**
	 * 로그인 체크 ( select cnt ) interface
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @return Map<String,Object> 로그인 정보 유무 검색
	 * @exception Exception
	 */
	public Map<String,Object> s_loginSelAjaxActCnt( Map<String, Object> commandMap) throws Exception;
    
	/**
	 * 로그인 정보조회 ( select ) interface
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @return Map<String,Object> 로그인 정보
	 * @exception Exception
	 */
	public Map<String,Object> s_loginSelAct( Map<String, Object> commandMap) throws Exception;

}
