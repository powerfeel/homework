package globis.common.util.validate;

import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.com.utl.fcc.service.EgovNumberUtil;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import globis.common.code.service.CommonCodeService;
import globis.common.constants.Constants;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("rvcfValidate")
public class RVCFValidate {

	private static final Logger LOGGER = LoggerFactory.getLogger(RVCFValidate.class);

	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	
//	/**
//	 * 공지사항의 분류  코드 유효성 검사를 한다.
//	 * 분류 구분 코드는 필수 이며 데이터 범위  1, 2, 3만 허용 한다.
//	 * 
//	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
//	 * @return resultMsg 공지사항의 분류 구분 유효성 결과 메시지
//	 */
//	public String checkDivGbCd(Map<String, Object> commandMap){
//
//		//결과값
//		String resultMsg = "";
//		
//		String divGbCd = "";
//		if(commandMap.get("divGbCd") != null){
//			divGbCd = (String)commandMap.get("divGbCd");
//			
//			if(!"01".equals(divGbCd) && !"02".equals(divGbCd) && !"03".equals(divGbCd)){
//				resultMsg = "공지사항 분류 데이터가 잘못 되었습니다.\n";
//			}
//		}else{
//			resultMsg = "공지사항 분류가 공백 입니다.\n";
//		}
//
//		return resultMsg;
//	}

	/**
	 * 회의실 예약시 타이틀 유효성 검사를 한다.
	 * 제목은 필수 입력 항목이며 최대 50자 까지 허용 한다.
	 * 제목은 " "을 허용 할수 없기 때문에 데이터를 trim 처리 후 비교를 한다.
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @return resultMsg 제목 유효성 결과 메시지
	 */
	public String checkTitle(Map<String, Object> commandMap){

		//결과값
		String resultMsg = "";
		
		if(commandMap.get("title") == null){
			resultMsg = "제목이 공백 입니다.\n";
		}else{
			
			String title = EgovStringUtil.isNullToString(commandMap.get("title"));
			
			if(!EgovStringUtil.isEmpty(title)){
				if(title.length() > Constants.CFRV_TITLE_LEN){
					resultMsg = "제목은 최대 " + Constants.CFRV_TITLE_LEN + "자 입니다.\n";
				}
			}else{
				resultMsg = "제목이 공백 입니다.\n";
			}
		}
		return resultMsg;
	}
	
	/**
	 * 예약일 유효성 검사를 한다.
	 * 예약일 범위는 10자(yyyy-MM-dd)만 허용 한다.
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @return resultMsg 예약일 유효성 결과 메시지
	 */
	public String checkRevDate(Map<String, Object> commandMap){

		//결과값
		String resultMsg = "";
		
		//예약일 관련 변수 설정
		String revYmd 				= "";
		
		//예약일 NULL 여부
		if(commandMap.get("revYmd") != null){
			
			revYmd = (String)commandMap.get("revYmd");
			
			if(!"".equals(revYmd)){		
				if(revYmd.trim().length() != Constants.CFRV_YMD_LEN){
					resultMsg = "예약일  날짜가 유효 하지 않습니다.\n";
				}else{
					
					//날짜에 "-" 데이터 제거 
					String chkDay = EgovStringUtil.removeMinusChar(revYmd);
					
					if(!EgovDateUtil.checkDate(chkDay)){
						resultMsg = "예약일  날짜가 유효 하지 않습니다.\n";
					}
				}
			}else{
				resultMsg = "예약일  날짜가 공백입니다.\n";
			}
		}else{
			resultMsg = "예약일  날짜가 공백입니다.\n";
		}
		return resultMsg;
	}
	
	/**
	 * 예약시간 유효성 검사를 한다.
	 * 예약 시간은 9시 ~ 18시 데이터만 유효
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @return resultMsg 예약일 유효성 결과 메시지
	 */
	public String checkRevTime(Map<String, Object> commandMap){

		//결과값
		String resultMsg = "";
		
		//예약 관련 변수 설정
		String revStime 			= "";
		String revEtime				= "";

		//시간  조건 검색
		if(commandMap.get("revStime") != null && commandMap.get("revEtime") != null){
			
			revStime = (String)commandMap.get("revStime");
			revEtime = (String)commandMap.get("revEtime");
			
			if(!"".equals(revStime) && !"".equals(revEtime)){
				
				int iStime = 0;
				int iEtime = 0;
				
				//숫자일때에만 종료 시간은 시작 시간을 넘을 수없다.
				if(EgovNumberUtil.getNumberValidCheck(revStime)){
					iStime = EgovStringUtil.zeroConvert(revStime);
				}
				
				//숫자일때에만 종료 시간은 시작 시간을 넘을 수없다.
				if(EgovNumberUtil.getNumberValidCheck(revEtime)){
					iEtime = EgovStringUtil.zeroConvert(revEtime);
				}
				
				//9시 ~ 18시 사이, 시작시간은 종료 시간보다 같거나 작아야 함
				if(
					iStime < 9 || iStime> 18 || iStime > iEtime ||
					iEtime < 9 || iEtime> 18
				){
					resultMsg = "예약시간이 유효 하지 않습니다.\n";
				}
				
			}else{
				resultMsg = "예약시간이 공백입니다.\n";
			}
		}else{
			resultMsg = "예약시간이 공백입니다.\n";
		}
		
		return resultMsg;
	}
	
	
	/**
	 * 공지사항의  내용 유효성 검사를 한다.
	 * 내용은 필수 이며 최대 혀용자리는 없다. 단 DB에서 받을 수 있는 최대 자리를 넘어 설경우 오류 처리 한다.
	 * 내용을 공백(" ")을 허용하지 않기 때문에 trim 처리 후 검사를 한다.
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @return resultMsg 공지사항의 내용 유효성 결과 메시지
	 */
	public String checkRevMem(Map<String, Object> commandMap){

		//결과값
		String resultMsg = "";
		
		if(commandMap.get("revMem") == null){
			resultMsg = "회의 참석자가 공백 입니다.\n";
		}else{
			
			String title = EgovStringUtil.isNullToString(commandMap.get("revMem"));
			
			if(!EgovStringUtil.isEmpty(title)){
				if(title.length() > Constants.CFRV_REVMEM_LEN){
					resultMsg = "회의 참석자는 최대 " + Constants.CFRV_REVMEM_LEN + "자 입니다.\n";
				}
			}else{
				resultMsg = "회의 참석자가 공백 입니다.\n";
			}
		}
		return resultMsg;
	}
	
//	/**
//	 * 공지사항의 상태 유효성 검사를 한다.
//	 * 수정시 필수 입력 사항 이며 데이터 범위  Y, N만 허용 한다.
//	 * 
//	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
//	 * @return resultMsg 공지사항의 상태 유효성 결과 메시지
//	 */
//	public String checkUseYn(Map<String, Object> commandMap){
//
//		//결과값
//		String resultMsg = "";
//		
//		String useYn = "";
//		if(commandMap.get("useYn") == null){
//			resultMsg = "상태 데이터가 공백 입니다..\n";
//		}else{			
//			useYn = (String)commandMap.get("useYn");
//			
//			if(!"Y".equals(useYn) && !"N".equals(useYn)){
//				resultMsg = "상태 데이터가 잘못 되었습니다.\n";
//			}
//		}
//		return resultMsg;
//	}
	
	/**
	 * 공지 사항의  DB 존재 여부 유효성 검사를 한다.
	 * 수정 하고자 하는 공지사항의 Key가 DB에 존재 하는지 검사 한다.
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @return resultMsg 공지사항의 DB존재여부 유효성 결과 메시지
	 */
	public String checkBoardExist(Map<String, Object> commandMap){

		//결과값
		String resultMsg = "";
		
		//공지사항을 조회 한다.(PK를 이용)
		Map<String, Object> resultMap = null;
		try {
			
			//데이터가 존재 하는지 체크
			Map<String,Object> existMap = new HashMap<String,Object>();
			existMap.put("boardId", commandMap.get("boardId"));
			existMap.put("contId", commandMap.get("contId"));

			//resultMap = pmntService.getNotice(existMap);
			
			//유효성 검증
			if(resultMap == null){			
				resultMsg = "존재하지 않는 공지사항 정보 입니다.\n";
			}

		} catch (Exception e) {
 			resultMsg = "공지사항 정보 조회 오류 입니다.\n";
		}		

		return resultMsg;
	}
}