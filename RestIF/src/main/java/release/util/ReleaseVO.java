package release.util;

import java.io.Serializable;

/*************************
 * ReleaseVO
 * 공통
 * @author 최형준
 * @since 2016.09.09.
 ************************/
public class ReleaseVO implements Serializable{
	private String wrtSn;
	private Integer ctntIdx;
	private String dpyFolderRoot;
	
	public String getWrtSn() {
		return wrtSn;
	}
	public void setWrtSn(String wrtSn) {
		this.wrtSn = wrtSn;
	}
	public Integer getCtntIdx() {
		return ctntIdx;
	}
	public void setCtntIdx(Integer ctntIdx) {
		this.ctntIdx = ctntIdx;
	}
	public String getDpyFolderRoot() {
		return dpyFolderRoot;
	}
	public void setDpyFolderRoot(String dpyFolderRoot) {
		this.dpyFolderRoot = dpyFolderRoot;
	}
}
