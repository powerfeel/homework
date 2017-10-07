package pub.DS.LG.service.impl;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("DSLGMapper")
public interface DSLGMapper {

	/**
	 * 로그인 체크 ( select cnt )
	 * 파일명 : DSLG_SQL.xml
	 * pub.DS.LG.web.service.impl.DSLGMMapper.s_loginSelAjaxActCnt 호출
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @return Map<String,Object>
	 * @exception Exception
	 */
    Map<String,Object> s_loginSelAjaxActCnt(Map<String, Object> commandMap) throws Exception;
    
    /**
	 * 로그인 정보조회 ( select )
	 * 파일명 : DSLG_SQL.xml
	 * pub.DS.LG.web.service.impl.DSLGMMapper.s_loginSelAct 호출
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @return Map<String,Object>
	 * @exception Exception
	 */
    Map<String,Object> s_loginSelAct(Map<String, Object> commandMap) throws Exception;
   
   
}