package cn.gyyx.action.beans.wd9yeardatavideo;

public class QuestionNameBean {
	private int code;    
	private String questionName;    //问题名
	private String questionAnswer;    //问题答案
	private int questionType;    //问题类型    1:"大数据视频"; 2:"参考游戏"; 3:"较难问题";
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	public String getQuestionAnswer() {
		return questionAnswer;
	}
	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}
	public int getQuestionType() {
		return questionType;
	}
	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}
	
}
