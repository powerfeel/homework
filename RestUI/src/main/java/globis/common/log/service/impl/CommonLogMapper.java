package globis.common.log.service.impl;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


@Mapper("CommonLogMapper") 
public interface CommonLogMapper {

    boolean insertLogLogin(Map<String,Object> map) throws Exception;

}
