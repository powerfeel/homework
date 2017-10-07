package globis.common.hndlr;

import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



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
