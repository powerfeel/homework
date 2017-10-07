package release.IF.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;


public interface InterfaceService {

	/**
   	 * 회의실 예약정보 가져오기
   	 * @param commandMap
   	 * @return List<Map<String,Object>>
   	 * @exception Exception
   	 */
	public List<Map<String,Object>> selectRevList( Map<String, Object> commandMap) throws Exception;
	
	/**
   	 * 예약정보 건수 가져오기
   	 * @param commandMap
   	 * @return List<Map<String,Object>>
   	 * @exception Exception
   	 */
	int selectRevTotalCount(Map<String, Object> commandMap) throws Exception;
	
	/**
	 * 회의설 예약정보 등록
	 * @param commandMap
	 * @return boolean
	 * @throws Exception
	 */
	boolean insertRevConference(Map<String, Object> commandMap) throws Exception;
	
	/**
	 * 회의실 예약 중복 건수 검색
	 * @param commandMap
	 * @return int
	 * @throws Exception
	 */
	int existRevConference(Map<String, Object> commandMap) throws Exception;
	
	/**
   	 * 회의실 예약 상세 정보 검색
   	 * @param commandMap
   	 * @return Map<String,Object>
   	 * @exception Exception
   	 */
    public Map<String,Object> selectRevConference( Map<String, Object> commandMap) throws Exception ;
    
    
    /**
   	 * 회의설 예약정보 변경
   	 * @param commandMap
   	 * @return boolean
   	 * @exception Exception
   	 */
    public boolean updateRevConference(Map<String, Object> commandMap) throws Exception ;
    
    /**
   	 * 회의설 예약정보 삭제
   	 * @param commandMap
   	 * @return boolean
   	 * @exception Exception
   	 */
    public boolean deleteRevConference(Map<String, Object> commandMap) throws Exception ;

}