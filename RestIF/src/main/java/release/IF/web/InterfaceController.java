package release.IF.web;

import egovframework.rte.ptl.mvc.bind.annotation.CommandMap;
import globis.common.util.ReqUtil;
import globis.common.util.ResUtil;
import globis.common.util.validate.RVCFValidate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import release.IF.service.InterfaceService;

import com.google.gson.Gson;


@Controller
public class InterfaceController {
	
	/** InterfaceService */
	@Resource(name = "InterfaceService")
    private InterfaceService interfaceService;
	
	@Resource(name = "rvcfValidate")
	private RVCFValidate rvcfValidate;

	
	/**
	 * 회의실 목록 검색
	 * 
	 * @param commandMap
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/IF/CONFRENCE/LIST", method = RequestMethod.POST)
	public void serarchtRevConference(@CommandMap  Map<String, Object> commandMap, 
			HttpServletRequest request, 
			HttpServletResponse response, 
			ModelMap model 
	) throws Exception{
		
		//검색 조건에 해당하는 목록을 가져 온다
		List<Map<String, Object>> list 	= interfaceService.selectRevList(commandMap);
		int totalCnt 					= interfaceService.selectRevTotalCount(commandMap);
		
		//결과 생성
		Map<String,Object> map = new HashMap<String,Object>();		 
 		map.put("rst", 			true);
		map.put("totalCnt", 	totalCnt);
		map.put("list", 		list);

		//json 변환
		Gson gson = new Gson();
		String rtn = gson.toJson(map);
		
		//Cross Domain 설정
		response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");        
        response.setHeader("Access-Control-Allow-Origin", "*");
		
		ResUtil.printResultJson(response,  rtn);
	}

	/**
	 * 회의실 예약 정보 등록
	 * @param commandMap
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/IF/CONFRENCE", method = RequestMethod.POST)
	public void insertRevConference(
			@CommandMap Map<String, Object> commandMap, 			
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model
	){
		
		//결과 변수
		boolean isSuccess = false;
		String msg = "";
				
		try{
			//유효성 체크
			msg += rvcfValidate.checkTitle(commandMap);
			msg += rvcfValidate.checkRevItem(commandMap);
			msg += rvcfValidate.checkRevDate(commandMap);
			msg += rvcfValidate.checkRevTime(commandMap);
			msg += rvcfValidate.checkRevMem(commandMap);
			msg += rvcfValidate.checExistRevConference(commandMap);
			
			//회의실 예약 정보 등록
			if("".equals(msg)){
				
				//요청 기록 생성
				String reqIp = ReqUtil.getClientIp(request);
				commandMap.put("reqIp", reqIp);
				commandMap.put("ifType", "INSERT");

				isSuccess = interfaceService.insertRevConference(commandMap);
				
				if(isSuccess){
					msg = "회의실 등록이 성공 하였습니다.";
				}else{
					msg = "회의실 등록이 실패 하였습니다.";
				}
			}

		}catch(Exception e){
			msg = "일시적인 시스템 오류 입니다.";
		}
		
		//결과 생성
		Map<String,Object> map = new HashMap<String,Object>();		 
 		map.put("rst", isSuccess);
		map.put("msg", msg);		
		
		//json 변환
		Gson gson = new Gson();
		String rtn = gson.toJson(map);
		
		//Cross Domain 설정
		response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with"); 
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		ResUtil.printString(response, rtn);

	}

	/**
	 * 회의실 예약 정보 검색(by revSeqNo)
	 * @param revSeqNo
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/IF/CONFRENCE/{revSeqNo}", method = RequestMethod.GET)
	public void getRevConference(
			@PathVariable String revSeqNo,
			HttpServletResponse response,
			ModelMap model
	){
		
		//검색 파라미터
		Map<String,Object> searchtMap = new HashMap<String,Object>();
		searchtMap.put("revSeqNo", revSeqNo);

		//결과 변수
		Map<String, Object> result = null;
		String msg = "";
				
		try{
			result 	= interfaceService.selectRevConference(searchtMap);

		}catch(Exception e){
			msg = "일시적인 시스템 오류 입니다.";
		}
		
		if(result == null){
			msg = "검색된 예약된 정보가 없습니다.";
		}
		
		//결과 생성
		Map<String,Object> map = new HashMap<String,Object>();		 
		map.put("msg", 	msg);
		map.put("rev", 	result);
		
		//json 변환
		Gson gson = new Gson();
		String rtn = gson.toJson(map);
		
		//Cross Domain 설정
		response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with"); 
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		ResUtil.printString(response, rtn);

	}
	
	
	/**
	 * 회의실 정보 변경
	 * @param revSeqNo
	 * @param commandMap
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/IF/CONFRENCE/{revSeqNo}", method = RequestMethod.PUT)
	public void updateRevConference(
			@PathVariable String revSeqNo,
			@CommandMap Map<String, Object> commandMap, 			
			HttpServletRequest request, 
			HttpServletResponse response,
			ModelMap model
	){
		
		//결과 변수
		boolean isSuccess = false;
		String msg = "";
				
		try{
			//유효성 체크
			msg += rvcfValidate.checkTitle(commandMap);
			msg += rvcfValidate.checkRevItem(commandMap);
			msg += rvcfValidate.checkRevDate(commandMap);
			msg += rvcfValidate.checkRevTime(commandMap);
			msg += rvcfValidate.checkRevMem(commandMap);
			msg += rvcfValidate.checExistRevConference(commandMap);
			
			//회의실 예약 정보 등록
			if("".equals(msg)){
				
				//요청 기록 생성
				String reqIp = ReqUtil.getClientIp(request);
				commandMap.put("reqIp", reqIp);
				commandMap.put("ifType", "UPDATE");

				isSuccess = interfaceService.updateRevConference(commandMap);
				
				if(isSuccess){
					msg = "회의실 정보가 수정 되었습니다.";
				}else{
					msg = "회의실 정보 수정이 실패 하였습니다.";
				}
			}

		}catch(Exception e){
			msg = "일시적인 시스템 오류 입니다.";
		}
		
		//결과 생성
		Map<String,Object> map = new HashMap<String,Object>();		 
 		map.put("rst", isSuccess);
		map.put("msg", msg);		
		
		//json 변환
		Gson gson = new Gson();
		String rtn = gson.toJson(map);
		
		//Cross Domain 설정
		response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with"); 
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		ResUtil.printString(response, rtn);

	}
	
	/**
	 * 회의실 정보 삭제
	 * @param revSeqNo
	 * @param commandMap
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/IF/CONFRENCE/{revSeqNo}", method = RequestMethod.DELETE)
	public void deleteConference(
			@PathVariable String revSeqNo,
			@CommandMap Map<String, Object> commandMap, 			
			HttpServletRequest request, 
			HttpServletResponse response,
			ModelMap model
	){
		
		//데이터가 존재 하는지 체크
		Map<String,Object> searchtMap = new HashMap<String,Object>();
		searchtMap.put("revSeqNo", revSeqNo);

		//결과 변수
		Map<String, Object> existRevCof	= null;
		boolean isSuccess 				= false;
		String msg 						= "";
				
		try{
			//삭제할 예약 정보가 있는지 확인
			existRevCof	= interfaceService.selectRevConference(searchtMap);
			
			if(existRevCof == null){
				msg = "예약된 정보가 없습니다.";
			}else{
				
				//요청 기록 생성
				String reqIp = ReqUtil.getClientIp(request);
				commandMap.put("reqIp", reqIp);
				commandMap.put("ifType", "DELETE");
				commandMap.put("revSeqNo", revSeqNo);
				
				isSuccess 	= interfaceService.deleteRevConference(commandMap);
				
				if(isSuccess){
					msg = "예약정보가 삭제 되었습니다.";
				}
			}
		}catch(Exception e){
			msg = "일시적인 시스템 오류 입니다.";
		}
	
		//결과 생성
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rst", 	isSuccess);
		map.put("msg", 	msg);
		
		//json 변환
		Gson gson = new Gson();
		String rtn = gson.toJson(map);
		
		//Cross Domain 설정
		response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with"); 
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		ResUtil.printString(response, rtn);
	}
}