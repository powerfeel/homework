package release.IF.service.impl;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("InterfaceMapper")
public interface InterfaceMapper {

	/**
	 * 회의실 예약정보 목록 가져오기
	 * 파일명 : IF_SQL.xml
	 * release.IF.service.impl.InterfaceMapper.selectRevList 호출
	 * 
	 * @param commandMap Map
	 * @return List<Map<String,Object>>
	 * @exception Exception
	 */
	List<Map<String,Object>> selectRevList(Map<String, Object> commandMap) throws Exception;
	
	/**
	 * 회의실 예약정보 목록 건수 가져오기
	 * 파일명 : IF_SQL.xml
	 * release.IF.service.impl.InterfaceMapper.selectRevTotalCount 호출
	 * 
	 * @param commandMap Map
	 * @return int
	 * @exception Exception
	 */
	int selectRevTotalCount(Map<String, Object> commandMap) throws Exception;
	
	/**
	 * 회의실 예약정보 등록 하기
	 * 파일명 : IF_SQL.xml
	 * release.IF.service.impl.InterfaceMapper.insertRevConference 호출
	 * 
	 * @param commandMap Map
	 * @return boolean
	 * @exception Exception
	 */
	boolean insertRevConference(Map<String, Object> commandMap) throws Exception;
	
	/**
	 * 회의실 예약 중복 예약 건수 가져오기
	 * 파일명 : IF_SQL.xml
	 * release.IF.service.impl.InterfaceMapper.existRevConference 호출
	 * 
	 * @param commandMap Map
	 * @return int
	 * @exception Exception
	 */
	int existRevConference(Map<String, Object> commandMap) throws Exception;
	
	/**
	 * 회의실 예약정보 가져오기
	 * 파일명 : IF_SQL.xml
	 * release.IF.service.impl.InterfaceMapper.selectRevConference 호출
	 * 
	 * @param commandMap Map
	 * @return Map<String,Object>
	 * @exception Exception
	 */
	Map<String,Object> selectRevConference(Map<String, Object> commandMap) throws Exception;
	
	/**
	 * 회의실 예약정보 업데이트
	 * 파일명 : IF_SQL.xml
	 * release.IF.service.impl.InterfaceMapper.updateRevConference 호출
	 * 
	 * @param commandMap Map
	 * @return boolean
	 * @exception Exception
	 */
	boolean updateRevConference(Map<String, Object> commandMap) throws Exception;
	
	/**
	 * 회의실 예약정보 삭제
	 * 파일명 : IF_SQL.xml
	 * release.IF.service.impl.InterfaceMapper.deleteRevConference 호출
	 * 
	 * @param commandMap Map
	 * @return boolean
	 * @exception Exception
	 */
	boolean deleteRevConference(Map<String, Object> commandMap) throws Exception;
}