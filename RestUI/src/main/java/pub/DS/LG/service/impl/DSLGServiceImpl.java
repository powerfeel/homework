package pub.DS.LG.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pub.DS.LG.service.DSLGService;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;


@Service("DSLGService")
public class DSLGServiceImpl extends EgovAbstractServiceImpl implements DSLGService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DSLGServiceImpl.class);

    @Resource(name="DSLGMapper")
	private DSLGMapper DSLGMapper;

    /**
	 * 로그인 체크 ( select cnt )
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @return Map<String,Object>
	 * @exception Exception
	 */
     public Map<String,Object> s_loginSelAjaxActCnt( Map<String, Object> commandMap) throws Exception {
    	 String pwd=(String)commandMap.get("pwd");
	   	 pwd = EgovFileScrty.encryptPassword(pwd);
	   	 commandMap.put("pwd", pwd);
         return DSLGMapper.s_loginSelAjaxActCnt(commandMap); 
     }

     /**
 	 * 로그인 정보조회 ( select )
 	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
 	 * @return Map<String,Object>
 	 * @exception Exception
 	 */
      public Map<String,Object> s_loginSelAct(Map<String, Object> commandMap) throws Exception {
            return DSLGMapper.s_loginSelAct(commandMap); 
      }     
}