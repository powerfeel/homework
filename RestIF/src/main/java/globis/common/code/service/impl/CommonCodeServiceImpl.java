package globis.common.code.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import globis.common.code.service.CommonCodeService;
import globis.common.util.StringUtil;


/**
 * @Class Name : CommonCodeServiceImpl.java
 * @Description : 공통코드 ServiceImpl Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2015.02.10   	도정훈 	       최초생성
 *
 * @author 그로비스인포텍 프레임웍 개발팀
 * @since 2015. 02.10
 * @version 1.0
 */



@Service("commonCodeService")
public class CommonCodeServiceImpl extends EgovAbstractServiceImpl implements CommonCodeService {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonCodeServiceImpl.class);
	
	@Resource(name="commonCodeMapper")
	private CommonCodeMapper commonCodeMapper;

    //@Resource(name="manageCodeIdGnrService")
    //private EgovIdGnrService manageCodeIdGnrService;
    
	private static Map<String,List<Map<String,Object>>> CODE_MAP;
	
	 /**
 	 * static 선언된 codeMap을 리턴한다.
 	 * @return List 
 	 * @exception Exception
 	 */
	public List<Map<String, Object>> getCodeList(Map<String, Object> commandMap) throws Exception{
    	 String parentCode = (String)commandMap.get("parentCode");
    	 String mode = "";
    	 if(commandMap.get("mode")!=null){
    		 mode = (String)commandMap.get("mode");
    	 }
 		if(CODE_MAP==null||CODE_MAP.isEmpty()){
 			initCodeMap();
 		}
 		List<Map<String, Object>> list = CODE_MAP.get(parentCode);
 		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
 		if(list==null)return rtnList;
 		try{
	 		for(int i=0;i<list.size();i++){
	 			Map<String, Object> map = list.get(i);
	 			if(mode.equals("all") || ((String)map.get("useYn")).equals("Y")){
	 				Map<String, Object> rtnMap = new HashMap<String, Object>();
	 				rtnMap.put("code", map.get("code"));
	 				rtnMap.put("codeNm", map.get("codeNm"));
	 				rtnList.add(rtnMap);
	 			}
	 		}
 		}catch (Exception e){
 			e.printStackTrace();
 			LOGGER.error(CODE_MAP.toString());
 		}
 		return rtnList;
 	 }
	
	 /**
 	 * static 선언된 codeMap을 리턴한다.
 	 * @return List 
 	 * @exception Exception
 	 */
	public String getCodeNm(String parentCode,String code) throws Exception{
 		if(CODE_MAP==null||CODE_MAP.isEmpty()){
 			initCodeMap();
 		}
 		String rtn = "";
 		List<Map<String, Object>> list = CODE_MAP.get(parentCode);
 		try{
	 		for(int i=0;i<list.size();i++){
	 			Map<String, Object> map = list.get(i);
	 			String compCode = (String)map.get("code");
	 			if(compCode.equals(code)&&((String)map.get("useYn")).equals("Y")){
	 				rtn = (String)map.get("codeNm");
	 			}
	 		}
 		}catch (Exception e){
 			e.printStackTrace();
 			LOGGER.error(CODE_MAP.toString());
 		}
 		return rtn;
 	 }
	
	/**
	 * static 선언된 codeMap을 리턴한다.
	 * @return List 
	 * @exception Exception
	 */
	public String addCodeNotExists(String parentCode,String codeNm) throws Exception{
		if(codeNm==null||codeNm.equals(""))return "";
		if(CODE_MAP==null||CODE_MAP.isEmpty()){
			initCodeMap();
		}
		boolean rtn = true;
		String code = "";
		List<Map<String, Object>> list = CODE_MAP.get(parentCode);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		try{
			for(int i=0;i<list.size();i++){
				Map<String, Object> map = list.get(i);
				String compCodeNm = (String)map.get("codeNm");
				if(compCodeNm.equals(codeNm)){
					code = (String)map.get("code");
					rtn = false; //같은 코드이름이 존재하면 false 새로운 값이면 true->일 경우에 insert
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			LOGGER.error(CODE_MAP.toString());
		}
		
		return code;
	}
	
	/**
	 * static 선언된 codeMap을 리턴한다.
	 * @return List 
	 * @exception Exception
	 */
	public String getCodeExists(String parentCode,String codeNm) throws Exception{
		if(codeNm==null||codeNm.equals(""))return "";
		if(CODE_MAP==null||CODE_MAP.isEmpty()){
			initCodeMap();
		}
		String code = "";
		List<Map<String, Object>> list = CODE_MAP.get(parentCode);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		try{
			for(int i=0;i<list.size();i++){
				Map<String, Object> map = list.get(i);
				String compCodeNm = (String)map.get("codeNm");
				if(compCodeNm.equals(codeNm)){
					code = (String)map.get("code");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			LOGGER.error(CODE_MAP.toString());
		}
		
		return code;
	}
     
     /**
  	 * Map을 인자로 받아  코드를 json 형태로 조회
  	 * @return String 
  	 * @exception Exception
  	 */
     public String getCodeJson(Map<String, Object> commandMap) throws Exception{
  		List<Map<String, Object>> list = getCodeList(commandMap);
  		Gson gson = new Gson();
  		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
  		for(int i=0; i<list.size(); i++) {
  			Map<String,Object> codeMap 	= list.get(i);
  			Map<String,Object> opMap 	= new HashMap<String,Object>();
  			opMap.put("text"	, (String)codeMap.get("codeNm"));
  			opMap.put("value"	, (String)codeMap.get("code"));
  			StringUtil.getConvertHTMLTag(opMap);
  			rtnList.add(opMap);
  		}
  		return gson.toJson(rtnList);
  	}

     /**
   	 * use_yn이 Y인 코드를 json 형태로 조회
   	 * @return String 
   	 * @exception Exception
   	 */
     public String getCodeJson(String parentCode) throws Exception{
    	 Map<String, Object> commandMap = new HashMap<String, Object>();
    	 commandMap.put("parentCode",parentCode);
		return getCodeJson(commandMap); 
	}
     /**
	 * use_yn상관없이  코드를 json 형태로 조회
	 * @return String 
	 * @exception Exception
	 */
     public String getAllCodeJson(String parentCode) throws Exception{
    	 Map<String, Object> commandMap = new HashMap<String, Object>();
    	 commandMap.put("parentCode",parentCode);
    	 commandMap.put("mode","all");
		return getCodeJson(commandMap);
	}
      
     
     /**
  	 * CODE_MAP load
  	 * @return void
  	 * @exception Exception
  	 */
     public void initCodeMap() throws Exception{
 		CODE_MAP = new HashMap<String,List<Map<String,Object>>>();
 		List<Map<String,Object>> list = commonCodeMapper.selectCommonCodeList();
 		String befParentCodeId = "";
 		String befParentCode = "";
 		List<Map<String,Object>> codeList = new ArrayList<Map<String,Object>>();
 		for(int i=0;i<list.size();i++){
 			//이전 부모코드와 같지 않을 경우 List새로 생성
 			Map<String,Object> codeMap = list.get(i);
 			if(!((String)codeMap.get("parentCodeId")).equals("root")){
	 			if(i>0 && !((String)codeMap.get("parentCodeId")).equals(befParentCodeId)){
	 				CODE_MAP.put(befParentCode,codeList);		
	 				codeList = new ArrayList<Map<String,Object>>();
	 			}
	 			befParentCodeId = (String)codeMap.get("parentCodeId");
	 			befParentCode = (String)codeMap.get("parentCode");
	 			codeList.add(codeMap);
 			}
 		}
		CODE_MAP.put(befParentCode,codeList);
 	 }
 	 
 	
     
//     public String getTemplateJson(Map<String, Object> commandMap) throws Exception{
//   		List<Map<String, Object>> list = commonCodeMapper.selectCommonTemplateList(commandMap);
//   		Gson gson = new Gson();
//   		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
//   		for(int i=0; i<list.size(); i++) {
//   			Map<String,Object> codeMap 	= list.get(i);
//   			Map<String,Object> opMap 	= new HashMap<String,Object>();
//   			opMap.put("text"	, codeMap.get("title"));
//   			opMap.put("value"	, codeMap.get("tempIdx"));
//   			StringUtil.getConvertHTMLTag(opMap);
//   			rtnList.add(opMap);
//   		}
//   		return gson.toJson(rtnList);
//   	}
//    public String getTemplateConts(Map<String, Object> commandMap) throws Exception{
//    	
//    	Map<String, Object> map = commonCodeMapper.selectCommonTemplateDetail(commandMap);
// 		map.put("trnType", (String)commandMap.get("trnType"));
// 		
// 		String trnType = (String)map.get("trnType");
// 		String conts = "";
// 		
// 		if(trnType.equals("PHN") || trnType.equals("MSG")){
// 			conts = ((String)map.get("conts"))
// 	 				.replaceAll("&lt;", "<")
// 	 				.replaceAll("&gt;", ">")
// 	    		 	.replaceAll("\\<.*?\\>", "")
// 	    		 	.replaceAll("\\n", " ")
// 	    		 	.replaceAll("\\r", " ")
// 	    		 	.trim();
// 		}else{
// 			conts = (String)map.get("conts");
// 		}
// 		
// 		return conts;
// 	}

     
     public Map<String,Object> selectCommonCode(Map<String, Object> commandMap) throws Exception {
     	return commonCodeMapper.selectCommonCode(commandMap); 
     }
}
