package globis.common.login.service;

import java.io.Serializable;



public class MenuSessionVO implements Serializable {
	
	private static final long serialVersionUID = 1540107951554372149L;
	/** 메뉴ID */
	private String menuId;
	/** 메뉴명 */
	private String menuNm;
	/** 메뉴경로 */
	private String menuPath;
	/** 메뉴하위경로 */
	private String menuChildPath;
	/** 상위메뉴ID */
	private String parentMenuId;
	/** 범위코드 */
	private String authScopeCd;
	/** 쓰기가능여부 */
	private String writeAbleYn;
	/** 메뉴레벨 **/
	private int level;
	/** 자식메뉴수 **/
	private String menuChildCnt;
	/** 아이콘 **/
	private String iconCd;
	/** 메뉴위치 **/
	private String menuPosition;
	/** 메뉴설명 **/
	private String menuComment;
	
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuNm() {
		return menuNm;
	}
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}
	public String getMenuPath() {
		return menuPath;
	}
	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}
	public String getMenuChildPath() {
		return menuChildPath;
	}
	public void setMenuChildPath(String menuChildPath) {
		this.menuChildPath = menuChildPath;
	}
	public String getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	public String getAuthScopeCd() {
		return authScopeCd;
	}
	public void setAuthScopeCd(String authScopeCd) {
		this.authScopeCd = authScopeCd;
	}
	public String getWriteAbleYn() {
		return writeAbleYn;
	}
	public void setWriteAbleYn(String writeAbleYn) {
		this.writeAbleYn = writeAbleYn;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getMenuChildCnt() {
		return menuChildCnt;
	}
	public void setMenuChildCnt(String menuChildCnt) {
		this.menuChildCnt = menuChildCnt;
	}
	public String getIconCd() {
		return iconCd;
	}
	public void setIconCd(String iconCd) {
		this.iconCd = iconCd;
	}
	public String getMenuPosition() {
		return menuPosition;
	}
	public void setMenuPosition(String menuPosition) {
		this.menuPosition = menuPosition;
	}
	
	public String getMenuComment() {
		return menuComment;
	}
	
	public void setMenuComment(String menuComment) {
		this.menuComment = menuComment;
	}
	
	public String toString() {
		return "menuId:"+menuId
				+ ",menuNm:"+menuNm
				+ ",menuPath:"+menuPath
				+ ",menuChildPath:"+menuChildPath
				+ ",parentMenuId:"+parentMenuId
				+ ",authScopeCd:"+authScopeCd
				+ ",writeAbleYn:"+writeAbleYn
				+ ",level:"+level
				+ ",menuChildCnt:"+menuChildCnt
				+ ",iconCd:"+iconCd
				+ ",menuPosition:"+menuPosition
				+ ",menuComment:"+menuComment;
	}
	
}
