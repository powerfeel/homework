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
 *  @see 참고사항
 *
 */

public class Globals {
	//OS 유형
    public static final String OS_TYPE = EgovProperties.getProperty("Globals.OsType");
    //OS BIT 유형
    public static final String OS_BIT = EgovProperties.getProperty("Globals.OsBit");
    //저작권 기본설정
    public static final String LICENSE = EgovProperties.getProperty("Globals.License");
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
		  
    //STL, Thumnail, Cover, Meta정보 업로드 폴더
    public static final String FILE_UPLOAD_PATH = EgovProperties.getProperty("Globals.FileUploadPath");
    
    //페이징 기본셋팅
    public static final String PAGE_INDEX 	= EgovProperties.getProperty("Globals.PAGE_INDEX");
    public static final String PAGE_UNIT 	= EgovProperties.getProperty("Globals.PAGE_UNIT");
    public static final String PAGE_SIZE 	= EgovProperties.getProperty("Globals.PAGE_SIZE");

    public static final String SYSTEM_MODE = EgovProperties.getProperty("Globals.SystemMode");

    
    public static final String SKIN = EgovProperties.getProperty("Globals.Skin");

	//메일 serer IP
	public static final String MAIL_SERVER_IP = EgovProperties.getProperty("Globals.mail.serverIP");
	public static final String MAIL_SERVER_PORT = EgovProperties.getProperty("Globals.mail.serverPort");
	
	//한번에 발송 되는 메일 숫자(서버 부하로 제한)
	public static final int MAIL_SEND_CNT = Integer.parseInt(EgovProperties.getProperty("Globals.mail.sendCnt"));

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

	
	public static final String MENU_2DEPT_OPEN_YN = EgovProperties.getProperty("Globals.menu.2deptOpenYn");
	public static final String MENU_3DEPT_OPEN_YN = EgovProperties.getProperty("Globals.menu.3deptOpenYn");
	
	public static final String LOGIN_MULTI_YN = EgovProperties.getProperty("Globals.login.multiYn");
	
	public static final String DEPT_TREE_EXPAND_MODE = EgovProperties.getProperty("Globals.dept.treeExpandMode");
	
	//메일 관련 설정
	public static final String MAIL_FROM 			= EgovProperties.getProperty("Globals.mail.from");
	public static final String MAIL_FROMPWD 		= EgovProperties.getProperty("Globals.mail.fromPwd");
	public static final String MAIL_SSL_USE 		= EgovProperties.getProperty("Globals.mail.ssl.use");
	public static final String MAIL_AUTH_USE 		= EgovProperties.getProperty("Globals.mail.auth.use");	
	public static final String MAIL_FROM_PATH 		= EgovProperties.getProperty("Globals.mail.from.path");	//메일 html 외부 경로
	public static final String MAIL_LINK_URL 		= EgovProperties.getProperty("Globals.mail.link.url");	//메일 로고 클릭시 URL
	public static final String MAIL_IMAGE_URL 		= EgovProperties.getProperty("Globals.mail.image.url");	//메일 로그 이미지 URL

}
