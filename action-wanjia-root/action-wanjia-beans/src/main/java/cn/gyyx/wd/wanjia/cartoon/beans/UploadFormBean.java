package cn.gyyx.wd.wanjia.cartoon.beans;

public class UploadFormBean {

	private Integer netType;
	private String serverCode;
	private String authorName;
	private String title;
	private String bookName;
	private String bookNum;
	private String context;
	private Integer isClosed;
	private Integer categoryCode;
	private String captcha;

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookNum() {
		return bookNum;
	}

	public void setBookNum(String bookNum) {
		this.bookNum = bookNum;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(Integer isClosed) {
		this.isClosed = isClosed;
	}

	public Integer getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(Integer categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public Integer getNetType() {
		return netType;
	}

	public void setNetType(Integer netType) {
		this.netType = netType;
	}

	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	public UploadFormBean(Integer netType, String serverCode, String authorName, String title, String bookName,
			String bookNum, String context, Integer isClosed, Integer categoryCode, String captcha) {
		super();
		this.netType = netType;
		this.serverCode = serverCode;
		this.authorName = authorName;
		this.title = title;
		this.bookName = bookName;
		this.bookNum = bookNum;
		this.context = context;
		this.isClosed = isClosed;
		this.categoryCode = categoryCode;
		this.captcha = captcha;
	}

	public UploadFormBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "UploadFormBean [netType=" + netType + ", serverCode=" + serverCode + ", authorName=" + authorName
				+ ", title=" + title + ", bookName=" + bookName + ", bookNum=" + bookNum + ", context=" + context
				+ ", isClosed=" + isClosed + ", categoryCode=" + categoryCode + ", captcha=" + captcha + "]";
	}

}
