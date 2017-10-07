package globis.common.login.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


@Mapper("commonLoginMapper") 
public interface CommonLoginMapper {

    /**
	 * 글 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
    Map<String, Object> selectEmplMap(String email) throws Exception;
    
    List<Map<String, Object>> selectAuthMenu(String userIdx) throws Exception;
    
    List<Map<String, Object>> selectCommonMenu() throws Exception;
    
    List<Map<String,Object>> selectMenuNavi(String menuId) throws Exception;
    
    boolean updateLastLoginDt(String userIdx) throws Exception;
}
