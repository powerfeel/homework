package globis.common.login.service;

import java.io.Serializable;



public class UserSessionVO implements Serializable {
	private static final long serialVersionUID = -2426190187062272061L;
	/** 사용자-INDEX */
	private String userIdx;
	/** 사용자ID */
	private String userId;
	/** 사용자명 */
	private String userNm;
	/** 부서-INDEX */
	private String deptIdx;
	/** 부서ID */
	private String deptId;	
	/** 부서명 */
	private String deptNm;		
	/** 관리부서-INDEX */
	private String mngDeptIdx;
	/** 관리부서ID */
	private String mngDeptId;	
	/** 관리부서명 */
	private String mngDeptNm;
	/** 직위코드 */
	private String pstCd;
	/** 직위명 */
	private String pstNm;	
	/** 로그인IP */
	private String ip;
	/** EMAIL */
	private String email;
	/** 마지막로그인일시 */
	private String lastLoginDt;
	private String loginDt;
	//private String logIdx;
	private String loginMethod;
	
	// 마크애니 직웍 정보
	private String id;		//ID는 사번으로 한다
	private int memSeqNo;
	private String name;
	private String title;
	private String position;
	private String promotionYmd;
	private String jobGroup;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public int getMemSeqNo() {
		return memSeqNo;
	}
	public void setMemSeqNo(int memSeqNo) {
		this.memSeqNo = memSeqNo;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getPromotionYmd() {
		return promotionYmd;
	}	
	public void setPromotionYmd(String promotionYmd) {
		this.promotionYmd = promotionYmd;
	}
	
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	
	public String getUserIdx() {
		return userIdx;
	}
	public void setUserIdx(String userIdx) {
		this.userIdx = userIdx;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getDeptIdx() {
		return deptIdx;
	}
	public void setDeptIdx(String deptIdx) {
		this.deptIdx = deptIdx;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}	
	public String getPstCd() {
		return pstCd;
	}
	public void setPstCd(String pstCd) {
		this.pstCd = pstCd;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPstNm() {
		return pstNm;
	}
	public void setPstNm(String pstNm) {
		this.pstNm = pstNm;
	}
	
	public String getMngDeptIdx() {
		return mngDeptIdx;
	}
	public void setMngDeptIdx(String mngDeptIdx) {
		this.mngDeptIdx = mngDeptIdx;
	}
	public String getMngDeptId() {
		return mngDeptId;
	}
	public void setMngDeptId(String mngDeptId) {
		this.mngDeptId = mngDeptId;
	}
	public String getMngDeptNm() {
		return mngDeptNm;
	}
	public void setMngDeptNm(String mngDeptNm) {
		this.mngDeptNm = mngDeptNm;
	}
	public String getLastLoginDt() {
		return lastLoginDt;
	}
	public void setLastLoginDt(String lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}
	public String getLoginDt() {
		return loginDt;
	}
	public void setLoginDt(String loginDt) {
		this.loginDt = loginDt;
	}
	//public String getLogIdx() {
		//return logIdx;
	//}
	//public void setLogIdx(String logIdx) {
//		this.logIdx = logIdx;
	//}
	public String getLoginMethod() {
		return loginMethod;
	}
	public void setLoginMethod(String loginMethod) {
		this.loginMethod = loginMethod;
	}

	public String toString() {
		return "memSeqNo:"+memSeqNo
				+ ",name:"+name
				+ ",id:"+id
				+ ",userIdx:"+userIdx
				+ ",userId:"+userId
				+ ",userNm:"+userNm
				+ ",deptIdx:"+deptIdx
				+ ",deptId:"+deptId
				+ ",deptNm:"+deptNm
				+ ",pstCd:"+pstCd
				+ ",pstNm:"+pstNm
				+ ",ip:"+ip
				+ ",email:"+email
				+ ",lastLoginDt:"+lastLoginDt;
	}
}
