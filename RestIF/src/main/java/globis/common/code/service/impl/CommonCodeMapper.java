package globis.common.code.service.impl;

import java.util.Map;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("commonCodeMapper")
public interface CommonCodeMapper {

    /**
	 * 코드 목록 조회
	 * @param commandMap
	 * @return 코드 목록
	 * @exception Exception
	 */
    List<Map<String, Object>> selectCommonCodeList() throws Exception;
    //List<Map<String, Object>> selectCommonTemplateList(Map<String, Object> commandMap) throws Exception;
    //Map<String, Object> selectCommonTemplateDetail(Map<String, Object> commandMap) throws Exception;
    
    Map<String,Object> selectCommonCode(Map<String, Object> commandMap) throws Exception;
}