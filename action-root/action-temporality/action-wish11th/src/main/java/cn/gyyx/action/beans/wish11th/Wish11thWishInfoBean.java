/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/10 11:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wish11th;

/**
 * <p>
 * 许愿信息实体
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class Wish11thWishInfoBean extends Wish11thWishBean {

    /**
     * 用户名
     */
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }
}
