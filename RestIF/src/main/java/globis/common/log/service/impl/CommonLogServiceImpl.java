package globis.common.log.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import globis.common.log.service.CommonLogService;

@Service("CommonLogService")
public class CommonLogServiceImpl extends EgovAbstractServiceImpl implements CommonLogService {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonLogServiceImpl.class);
	
    @Resource(name="CommonLogMapper")
	private CommonLogMapper CommonLogMapper;
    
   
    public boolean insertLogInterFace(Map<String,Object> map) {
    	boolean rtn = false;
    	try{
    		rtn = CommonLogMapper.insertLogInterFace(map);
    	}catch (Exception e){
			e.printStackTrace();
		}
    	return rtn;
    }
    
}
