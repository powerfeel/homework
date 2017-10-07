package globis.common.log.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CommonLogMapper") 
public interface CommonLogMapper {
    boolean insertLogInterFace(Map<String,Object> map) throws Exception;    
}
