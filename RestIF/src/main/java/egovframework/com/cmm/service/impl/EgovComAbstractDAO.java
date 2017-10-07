/**
 *
 */
package egovframework.com.cmm.service.impl;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * EgovComAbstractDAO.java 클래스
 *
 * @author 서준식
 * @since 2011. 9. 23.
 * @version 1.0
 * @see 참고사항
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2011. 9. 23.   서준식        최초 생성
 * </pre>
 */
public abstract class EgovComAbstractDAO extends EgovAbstractDAO{

	@Resource(name="egov.sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSuperSqlMapClient(sqlMapClient);
    }
	
			
    /**
     * 리스트 조회 처리 SQL mapping 을 실행한다.
     *
     * @param queryId - 리스트 조회 처리 SQL mapping 쿼리 ID
     * @return 결과 List 객체 - SQL mapping 파일에서 지정한 resultClass/resultMap 에 의한 결과 객체(보통 VO 또는 Map)의 List
     */
	public List<?> list(String queryId) {
        return getSqlMapClientTemplate().queryForList(queryId);
    }

    /**
     * 리스트 조회 처리 SQL mapping 을 실행한다.
     *
     * @param queryId - 리스트 조회 처리 SQL mapping 쿼리 ID
     * @param parameterObject - 리스트 조회 처리 SQL mapping 입력 데이터(조회 조건)를 세팅한 파라메터 객체(보통 VO 또는 Map)
     * @return 결과 List 객체 - SQL mapping 파일에서 지정한 resultClass/resultMap 에 의한 결과 객체(보통 VO 또는 Map)의 List
     */
	public List<?> list(String queryId, Object parameterObject) {
        return getSqlMapClientTemplate().queryForList(queryId, parameterObject);
    }

	  /**
     * 단건조회 처리 SQL mapping 을 실행한다.
     *
     * @param queryId - 단건 조회 처리 SQL mapping 쿼리 ID
     * @return 결과 객체 - SQL mapping 파일에서 지정한 resultClass/resultMap 에 의한 단일 결과 객체(보통 VO 또는 Map)
     */
    public Object select(String queryId) {
    	Object obj = getSqlMapClientTemplate().queryForObject(queryId);
    	if(obj instanceof Map){
    		obj = (Map<String,Object>)obj;
    	}
    	return obj;
    }

    /**
     * 단건조회 처리 SQL mapping 을 실행한다.
     *
     * @param queryId - 단건 조회 처리 SQL mapping 쿼리 ID
     * @param parameterObject - 단건 조회 처리 SQL mapping 입력 데이터(key)를 세팅한 파라메터 객체(보통 VO 또는 Map)
     * @return 결과 객체 - SQL mapping 파일에서 지정한 resultClass/resultMap 에 의한 단일 결과 객체(보통 VO 또는 Map)
     */
    public Object select(String queryId, Object parameterObject) {
        Object obj = getSqlMapClientTemplate().queryForObject(queryId,parameterObject);
    	if(obj instanceof Map){
    		obj = (Map<String,Object>)obj;
    	}
    	return obj;
    }
    
}
