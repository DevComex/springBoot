package cn.gyyx.action.beans.QianKunLock;

import java.util.Date;

public class QianKunLockModels {
	  // 摘要:
    //     账号
    private String Account;
    //
    // 摘要:
    //     绑定时间
    private Date BindTime;
    //
    // 摘要:
    //     乾坤锁类型
    private String EKeyCompany;
    //
    // 摘要:
    //     乾坤锁序列号
    private String EKeySN;

    //密保类型
    private int EkeyType;

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}

	public Date getBindTime() {
		return BindTime;
	}

	public void setBindTime(Date bindTime) {
		BindTime = bindTime;
	}

	public String getEKeyCompany() {
		return EKeyCompany;
	}

	public void setEKeyCompany(String eKeyCompany) {
		EKeyCompany = eKeyCompany;
	}

	public String getEKeySN() {
		return EKeySN;
	}

	public void setEKeySN(String eKeySN) {
		EKeySN = eKeySN;
	}

	public int getEkeyType() {
		return EkeyType;
	}

	public void setEkeyType(int ekeyType) {
		EkeyType = ekeyType;
	}
    
}
