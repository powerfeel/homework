package globis.common.hndlr;

import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Class Name : ExceptionHandler.java
 * @Description : ExceptionHandler Class
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

public class ExcepHndlr implements ExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcepHndlr.class);

    /**
    * @param ex
    * @param packageName
    * @see 개발프레임웍크 실행환경 개발팀
    */
    public void occur(Exception ex, String packageName) {
    	LOGGER.debug(" ExcepHndlr : "+packageName);
    	ex.printStackTrace();
    }

}
