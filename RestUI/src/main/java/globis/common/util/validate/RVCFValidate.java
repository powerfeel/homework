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
	
	/**
	 * 회의실 예약시 타이틀 유효성 검사를 한다.
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
	 * 회의 참석자 유효성 검사를 한다.
	 * 
	 * @param commandMap 사용자로 부터 넘겨 받은 데이터 Map
	 * @return resultMsg 유효성 결과 메시지
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
}