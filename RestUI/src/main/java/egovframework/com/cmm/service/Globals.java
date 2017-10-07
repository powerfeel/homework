package egovframework.com.cmm.service;

/**
 *  Class Name : Globals.java
 *  Description : 시스템 구동 시 프로퍼티를 통해 사용될 전역변수를 정의한다.
 *  Modification Information
 *
 *     수정일         수정자                   수정내용
 *   -------    --------    ---------------------------
 *   2009.01.19    박지욱          최초 생성
 *
 *  @author 공통 서비스 개발팀 박지욱
 *  @since 2009. 01. 19
 *  @version 1.0
 *  @see
 *
 */

public class Globals {
	//OS 유형
    public static final String OS_TYPE = EgovProperties.getProperty("Globals.OsType");
    //DB 유형
    public static final String DB_TYPE = EgovProperties.getProperty("Globals.DbType");
    //메인 페이지
    public static final String MAIN_PAGE = EgovProperties.getProperty("Globals.MainPage");
    //ShellFile 경로
    public static final String SHELL_FILE_PATH = EgovProperties.getPathProperty("Globals.ShellFilePath");
    //퍼로퍼티 파일 위치
    public static final String CONF_PATH = EgovProperties.getPathProperty("Globals.ConfPath");
    //Server정보 프로퍼티 위치
    public static final String SERVER_CONF_PATH = EgovProperties.getPathProperty("Globals.ServerConfPath");
    //Client정보 프로퍼티 위치
    public static final String CLIENT_CONF_PATH = EgovProperties.getPathProperty("Globals.ClientConfPath");
    //파일포맷 정보 프로퍼티 위치
    public static final String FILE_FORMAT_PATH = EgovProperties.getPathProperty("Globals.FileFormatPath");

    //파일 업로드 원 파일명
	public static final String ORIGIN_FILE_NM = "originalFileName";
	//파일 확장자
	public static final String FILE_EXT = "fileExtension";
	//파일크기
	public static final String FILE_SIZE = "fileSize";
	//업로드된 파일명
	public static final String UPLOAD_FILE_NM = "uploadFileName";
	//파일경로
	public static final String FILE_PATH = "filePath";

	
/************************이 이하로만 G-POST에서 사용하는 프로퍼티***************************/
	
	/** DB 경로 **/
	//public static final String DB_URL = EgovProperties.getProperty("Globals.Url");
	//public static final String DB_USER = EgovProperties.getProperty("Globals.UserName");
	//public static final String DB_PW = EgovProperties.getProperty("Globals.Password");
	
	//페이징 기본셋팅
    public static final String PAGE_INDEX 	= EgovProperties.getProperty("Globals.PAGE_INDEX");
    public static final String PAGE_UNIT 	= EgovProperties.getProperty("Globals.PAGE_UNIT");
    public static final String PAGE_SIZE 	= EgovProperties.getProperty("Globals.PAGE_SIZE");
    
    public static final String SYSTEM_MODE = EgovProperties.getProperty("Globals.SystemMode");
    
    public static final String TRN_ATTACH_PATH = EgovProperties.getProperty("Globals.TrnAttachPath");
    public static final String TRN_ATTACH_EXEC_PATH = EgovProperties.getProperty("Globals.TrnAttachExecPath");
    
    public static final String EXEC_TRN_YN = EgovProperties.getProperty("Globals.ExecTrnYn");
    
    public static final String SKIN = EgovProperties.getProperty("Globals.Skin"); 
    
    public static final String BATCH_YN_EML = EgovProperties.getProperty("Globals.BatchYn.EML");
    public static final String BATCH_YN_PHN = EgovProperties.getProperty("Globals.BatchYn.PHN");
    public static final String BATCH_YN_MSG = EgovProperties.getProperty("Globals.BatchYn.MSG");
    public static final String BATCH_YN_BOD = EgovProperties.getProperty("Globals.BatchYn.BOD");
    public static final String BATCH_YN_USER = EgovProperties.getProperty("Globals.BatchYn.USER");
    public static final String BATCH_YN_POINT = EgovProperties.getProperty("Globals.BatchYn.POINT");
    
    public static final String QUEST_YN = EgovProperties.getProperty("Globals.Quest");
    
    //제거 예정
    //public static final String SMS_MANUAL_YN = EgovProperties.getProperty("Globals.sms.manualYn");
    
 	//게시판 자동연결 여부
	//public static final String TRN_BOD_CONN_YN = EgovProperties.getProperty("Globals.trn.bodConnYn");

	//메일 serer IP
	public static final String MAIL_SERVER_IP = EgovProperties.getProperty("Globals.mail.serverIP");
	public static final String MAIL_SERVER_PORT = EgovProperties.getProperty("Globals.mail.serverPort");
	
	
	//한번에 발송 되는 메일 숫자(서버 부하로 제한)
	public static final int MAIL_SEND_CNT = Integer.parseInt(EgovProperties.getProperty("Globals.mail.sendCnt"));
	//foot 하단 관련 사이트 표출여부
	public static final String SITE_LINK_YN = EgovProperties.getProperty("Globals.SiteLinkYn");
	
	public static final String API_NAVER_SHORT_URL_ID = EgovProperties.getProperty("Globals.api.naverShortUrlId");
	public static final String API_NAVER_SHORT_URL_SECRET = EgovProperties.getProperty("Globals.api.naverShortUrlSecret");
	
	
	
	public static final String API_GOOGLE_SHORT_URL_KEY = EgovProperties.getProperty("Globals.api.googleShortUrlKey");
	public static final String API_SHORT_URL_MODE = EgovProperties.getProperty("Globals.api.shortUrlMode");
	
	
	//엑셀업로드 여부
	//public static final String HUMAN_EXCEL_UPLOAD_YN = EgovProperties.getProperty("Globals.human.excelUploadYn");
	public static final String HUMAN_PULL_YN = EgovProperties.getProperty("Globals.human.pullYn");
	public static final String HUMAN_LINK_DEL_YN = EgovProperties.getProperty("Globals.human.linkDelYn");
	
	//USERID 복호화 여부
	public static final String ENC_USER_ID_YN = EgovProperties.getProperty("Globals.enc.userIdYn");
	//이메일, 휴대폰번호 복호화여부
	public static final String ENC_CONTACT_YN = EgovProperties.getProperty("Globals.enc.contactYn");
	public static final String ENC_NAME_YN = EgovProperties.getProperty("Globals.enc.nameYn");
	public static final String LEFT_LOGIN_YN = EgovProperties.getProperty("Globals.leftLoginYn");
	//public static final String ADMIN_IP_LIMIT_YN = EgovProperties.getProperty("Globals.adminIpLimitYn");
	public static final String MAIL_DOMAIN = EgovProperties.getProperty("Globals.mail.domain");

   //보안 훈련시 사용자가 조회하는 이미지와 수집URL IP및 PORT
	public static final String COM_SERVER_URL = EgovProperties.getProperty("Globals.com.serverUrl");
	
	public static final String COM_CONN_URL = EgovProperties.getProperty("Globals.com.connUrl");

	//중계서버 정보
	public static final String FORWARD_SERVER_URL = EgovProperties.getProperty("Globals.forward.serverUrl");
	public static final String FORWARD_TRN_SEND_YN = EgovProperties.getProperty("Globals.forward.trnSendYn");
	public static final String FORWARD_TRN_RESULT_SEND_YN = EgovProperties.getProperty("Globals.forward.trnResultSendYn");
	public static final String FORWARD_INFO_SEND_YN = EgovProperties.getProperty("Globals.forward.infoSendYn");

	public static final String FORWARD_CONN_URL = EgovProperties.getProperty("Globals.forward.connUrl");
	public static final String FORWARD_SERVER_YN = EgovProperties.getProperty("Globals.forward.serverYn");
	public static final String FORWARD_GATTER_YN = EgovProperties.getProperty("Globals.forward.gatterYn");
	public static final String FILE_STORE_PATH = EgovProperties.getProperty("Globals.fileStorePath");
	//public static final String PRIVATE_POINT_YN = EgovProperties.getProperty("Globals.privatePointYn");
	//public static final String INDEX_MODE = EgovProperties.getProperty("Globals.index.mode");
	
	public static final String MENU_2DEPT_OPEN_YN = EgovProperties.getProperty("Globals.menu.2deptOpenYn");
	public static final String MENU_3DEPT_OPEN_YN = EgovProperties.getProperty("Globals.menu.3deptOpenYn");
	
	public static final String LOGIN_MULTI_YN = EgovProperties.getProperty("Globals.login.multiYn");
	
	public static final String DEPT_TREE_EXPAND_MODE = EgovProperties.getProperty("Globals.dept.treeExpandMode");
	
	public static final String BATCH_YN_VM = EgovProperties.getProperty("Globals.BatchYn.VM");
		
	//재배포 요청 URL
	public static final String RE_DPY_URL 			= EgovProperties.getProperty("Globals.ReDpyUrl");
	
	
}
