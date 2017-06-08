/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：laixiancai
  * @联系方式：laixiancai@gyyx.cn
  * @创建时间：2017年4月9日 下午5:55:46
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.beans.wdrankinglist2017;

/**
 * <p>
 * 前台宣言排行列表页查询封装类
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public class DeclarationQueryBean {
    private Integer pageIndex;
    private Integer pageSize;

    private String type;

    private String key;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
