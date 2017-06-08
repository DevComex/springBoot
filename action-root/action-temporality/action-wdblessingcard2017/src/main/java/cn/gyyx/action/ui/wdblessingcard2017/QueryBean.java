/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdblessingcard2017
  * @作者：niushuai
  * @联系方式：niushuai@gyyx.cn
  * @创建时间：2017年3月13日 下午7:37:04
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.ui.wdblessingcard2017;

/**
 * <p>
 * 用于查询数据的实体
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public class QueryBean {

    private Integer page;
    private Integer size;
    private String title;
    private Integer registerYearFrom;
    private Integer registerYearEnd;
    private String account;
    private Integer actionCode;
    private String roleName;
    private String orderBy;
    private Integer hdCode;
    private Integer registerYear;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getActionCode() {
        return actionCode;
    }

    public void setActionCode(Integer actionCode) {
        this.actionCode = actionCode;
    }

    public Integer getRegisterYearFrom() {
        return registerYearFrom;
    }

    public void setRegisterYearFrom(Integer registerYearFrom) {
        this.registerYearFrom = registerYearFrom;
    }

    public Integer getRegisterYearEnd() {
        return registerYearEnd;
    }

    public void setRegisterYearEnd(Integer registerYearEnd) {
        this.registerYearEnd = registerYearEnd;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getHdCode() {
        return hdCode;
    }

    public void setHdCode(Integer hdCode) {
        this.hdCode = hdCode;
    }

    public Integer getRegisterYear() {
        return registerYear;
    }

    public void setRegisterYear(Integer registerYear) {
        this.registerYear = registerYear;
    }

    @Override
    public String toString() {
        return "QueryBean [page=" + page + ", size=" + size + ", title=" + title
                + ", registerYearFrom=" + registerYearFrom
                + ", registerYearEnd=" + registerYearEnd + ", account="
                + account + ", actionCode=" + actionCode + ", roleName="
                + roleName + ", orderBy=" + orderBy + ", hdCode=" + hdCode
                + ", registerYear=" + registerYear + "]";
    }

}
