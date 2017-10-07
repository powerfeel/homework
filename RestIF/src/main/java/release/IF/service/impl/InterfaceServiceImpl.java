package release.IF.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import release.IF.service.InterfaceService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import globis.common.log.service.CommonLogService;

@Service("InterfaceService")
public class InterfaceServiceImpl extends EgovAbstractServiceImpl implements InterfaceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceServiceImpl.class);

    @Resource(name="InterfaceMapper")
	private InterfaceMapper InterfaceMapper;
    	
	@Resource(name = "CommonLogService")
    private CommonLogService commonLogService;
	
    /**
   	 * 회의실 예약정보 가져오기
   	 * @param commandMap
   	 * @return List<Map<String,Object>>
   	 * @exception Exception
   	 */
    public List<Map<String,Object>> selectRevList( Map<String, Object> commandMap) throws Exception {
       	   return InterfaceMapper.selectRevList(commandMap); 
    }
    
    /**
   	 * 예약정보 건수 가져오기
   	 * @param commandMap
   	 * @return int
   	 * @exception Exception
   	 */
    public int selectRevTotalCount(Map<String, Object> commandMap) throws Exception {
    	return InterfaceMapper.selectRevTotalCount(commandMap);
    }
    
    /**
   	 * 회의설 예약정보 등록
   	 * @param commandMap
   	 * @return boolean
   	 * @exception Exception
   	 */
    public boolean insertRevConference(Map<String, Object> commandMap) throws Exception {
    	boolean isSuccess = false;
    	isSuccess = InterfaceMapper.insertRevConference(commandMap);
    	if(isSuccess){
    		commonLogService.insertLogInterFace(commandMap);
    	}
    	return isSuccess;
    }
    
    /**
   	 * 회의실 예약 중복 건수 검색
   	 * @param commandMap
   	 * @return List<Map<String,Object>>
   	 * @exception Exception
   	 */
    public int existRevConference(Map<String, Object> commandMap) throws Exception {
    	return InterfaceMapper.existRevConference(commandMap);
    }
    
    
    /**
   	 * 회의실 예약 상세 정보 검색
   	 * @param commandMap
   	 * @return Map<String,Object>
   	 * @exception Exception
   	 */
    public Map<String,Object> selectRevConference( Map<String, Object> commandMap) throws Exception {
       	   return InterfaceMapper.selectRevConference(commandMap); 
    }
    
    
    /**
   	 * 회의설 예약정보 변경
   	 * @param commandMap
   	 * @return boolean
   	 * @exception Exception
   	 */
    public boolean updateRevConference(Map<String, Object> commandMap) throws Exception {
    	boolean isSuccess = false;
    	isSuccess = InterfaceMapper.updateRevConference(commandMap);
    	if(isSuccess){
    		commonLogService.insertLogInterFace(commandMap);
    	}
    	return isSuccess;
    }
    
    /**
   	 * 회의설 예약정보 삭제
   	 * @param commandMap
   	 * @return boolean
   	 * @exception Exception
   	 */
    public boolean deleteRevConference(Map<String, Object> commandMap) throws Exception {
    	boolean isSuccess = false;
    	isSuccess = InterfaceMapper.deleteRevConference(commandMap);
    	if(isSuccess){
    		commonLogService.insertLogInterFace(commandMap);
    	}
    	return isSuccess;
    }
}