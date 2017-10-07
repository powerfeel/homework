package globis.common.code.service;

import globis.common.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;



public interface CommonCodeService {

	public List<Map<String, Object>> getCodeList(Map<String, Object> commandMap) throws Exception;
	public String getCodeNm(String parentCode,String code) throws Exception;
	public String addCodeNotExists(String parentCode,String codeNm) throws Exception;
	public String getCodeExists(String parentCode,String codeNm) throws Exception;
	 /**
  	 * Map을 인자로 받아  코드를 json 형태로 조회
  	 * @return String 
  	 * @exception Exception
  	 */
     public String getCodeJson(Map<String, Object> commandMap) throws Exception;
     
     
     /**
	 * use_yn이 Y인 코드를 json 형태로 조회
	 * @return String 
	 * @exception Exception
	 */
     public String getCodeJson(String parentCode) throws Exception;
     
     
     /**
 	 * use_yn상관없이  코드를 json 형태로 조회
 	 * @return String 
 	 * @exception Exception
 	 */
     public String getAllCodeJson(String parentCode) throws Exception;
     
     
     /**
   	 * CODE_MAP load
   	 * @return void
   	 * @exception Exception
   	 */
      public void initCodeMap() throws Exception;
      
      public String getTemplateJson(Map<String, Object> commandMap) throws Exception;
    
}
