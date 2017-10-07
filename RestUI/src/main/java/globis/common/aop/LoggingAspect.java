package globis.common.aop;


import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class LoggingAspect{

    //protected Log log = LogFactory.getLog(this.getClass());
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    /**  
     * @throws Throwable 
     * @see 개발프레임웍크 실행환경 개발팀
     */ 
    //@Pointcut("execution(* globis..*Service.*(..))")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    	
    	StopWatch stopWatch = new StopWatch();
    	try{
    		stopWatch.start();
//    		if(true){
//    			throw new RuntimeException("먼저 로그인을 하셔야 합니다.");
//    		}
    		Object retValue = joinPoint.proceed();
    		return retValue;
    	}catch(Throwable e){ 
    		throw e;
    	}finally{
    		stopWatch.stop();
    		LOGGER.debug("기록 종료");
    		LOGGER.debug(joinPoint.getSignature().getName()+"메소드 실행시간:"+stopWatch.getTotalTimeMillis());
    	}
    }
    
}
