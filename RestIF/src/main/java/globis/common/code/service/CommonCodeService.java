package globis.common.code.service;

import globis.common.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * @Class Name : CommonCodeService.java
 * @Description : 공통코드 Service Class
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
      
      //public String getTemplateJson(Map<String, Object> commandMap) throws Exception;
      //public String getTemplateConts(Map<String, Object> commandMap) throws Exception;
      
      Map<String,Object> selectCommonCode(Map<String, Object> commandMap) throws Exception;
    
}
