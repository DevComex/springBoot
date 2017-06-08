package cn.gyyx.action.beans.wd10yearcoser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 版        权：光宇游戏
 * 作        者：WangAndong
 * 创建时间：2016年9月9日 下午1:08:01
 * 描        述：
 */
public class ResourceBeanExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ResourceBeanExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(Integer value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(Integer value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(Integer value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(Integer value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(Integer value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<Integer> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<Integer> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(Integer value1, Integer value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andUserCodeIsNull() {
            addCriterion("user_code is null");
            return (Criteria) this;
        }

        public Criteria andUserCodeIsNotNull() {
            addCriterion("user_code is not null");
            return (Criteria) this;
        }

        public Criteria andUserCodeEqualTo(Integer value) {
            addCriterion("user_code =", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotEqualTo(Integer value) {
            addCriterion("user_code <>", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeGreaterThan(Integer value) {
            addCriterion("user_code >", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_code >=", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLessThan(Integer value) {
            addCriterion("user_code <", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLessThanOrEqualTo(Integer value) {
            addCriterion("user_code <=", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeIn(List<Integer> values) {
            addCriterion("user_code in", values, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotIn(List<Integer> values) {
            addCriterion("user_code not in", values, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeBetween(Integer value1, Integer value2) {
            addCriterion("user_code between", value1, value2, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("user_code not between", value1, value2, "userCode");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andFavoriteNumIsNull() {
            addCriterion("favorite_num is null");
            return (Criteria) this;
        }

        public Criteria andFavoriteNumIsNotNull() {
            addCriterion("favorite_num is not null");
            return (Criteria) this;
        }

        public Criteria andFavoriteNumEqualTo(Integer value) {
            addCriterion("favorite_num =", value, "favoriteNum");
            return (Criteria) this;
        }

        public Criteria andFavoriteNumNotEqualTo(Integer value) {
            addCriterion("favorite_num <>", value, "favoriteNum");
            return (Criteria) this;
        }

        public Criteria andFavoriteNumGreaterThan(Integer value) {
            addCriterion("favorite_num >", value, "favoriteNum");
            return (Criteria) this;
        }

        public Criteria andFavoriteNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("favorite_num >=", value, "favoriteNum");
            return (Criteria) this;
        }

        public Criteria andFavoriteNumLessThan(Integer value) {
            addCriterion("favorite_num <", value, "favoriteNum");
            return (Criteria) this;
        }

        public Criteria andFavoriteNumLessThanOrEqualTo(Integer value) {
            addCriterion("favorite_num <=", value, "favoriteNum");
            return (Criteria) this;
        }

        public Criteria andFavoriteNumIn(List<Integer> values) {
            addCriterion("favorite_num in", values, "favoriteNum");
            return (Criteria) this;
        }

        public Criteria andFavoriteNumNotIn(List<Integer> values) {
            addCriterion("favorite_num not in", values, "favoriteNum");
            return (Criteria) this;
        }

        public Criteria andFavoriteNumBetween(Integer value1, Integer value2) {
            addCriterion("favorite_num between", value1, value2, "favoriteNum");
            return (Criteria) this;
        }

        public Criteria andFavoriteNumNotBetween(Integer value1, Integer value2) {
            addCriterion("favorite_num not between", value1, value2, "favoriteNum");
            return (Criteria) this;
        }

        public Criteria andAttrsIsNull() {
            addCriterion("attrs is null");
            return (Criteria) this;
        }

        public Criteria andAttrsIsNotNull() {
            addCriterion("attrs is not null");
            return (Criteria) this;
        }

        public Criteria andAttrsEqualTo(String value) {
            addCriterion("attrs =", value, "attrs");
            return (Criteria) this;
        }

        public Criteria andAttrsNotEqualTo(String value) {
            addCriterion("attrs <>", value, "attrs");
            return (Criteria) this;
        }

        public Criteria andAttrsGreaterThan(String value) {
            addCriterion("attrs >", value, "attrs");
            return (Criteria) this;
        }

        public Criteria andAttrsGreaterThanOrEqualTo(String value) {
            addCriterion("attrs >=", value, "attrs");
            return (Criteria) this;
        }

        public Criteria andAttrsLessThan(String value) {
            addCriterion("attrs <", value, "attrs");
            return (Criteria) this;
        }

        public Criteria andAttrsLessThanOrEqualTo(String value) {
            addCriterion("attrs <=", value, "attrs");
            return (Criteria) this;
        }

        public Criteria andAttrsLike(String value) {
            addCriterion("attrs like", value, "attrs");
            return (Criteria) this;
        }

        public Criteria andAttrsNotLike(String value) {
            addCriterion("attrs not like", value, "attrs");
            return (Criteria) this;
        }

        public Criteria andAttrsIn(List<String> values) {
            addCriterion("attrs in", values, "attrs");
            return (Criteria) this;
        }

        public Criteria andAttrsNotIn(List<String> values) {
            addCriterion("attrs not in", values, "attrs");
            return (Criteria) this;
        }

        public Criteria andAttrsBetween(String value1, String value2) {
            addCriterion("attrs between", value1, value2, "attrs");
            return (Criteria) this;
        }

        public Criteria andAttrsNotBetween(String value1, String value2) {
            addCriterion("attrs not between", value1, value2, "attrs");
            return (Criteria) this;
        }

        public Criteria andCheckTypeIsNull() {
            addCriterion("check_type is null");
            return (Criteria) this;
        }

        public Criteria andCheckTypeIsNotNull() {
            addCriterion("check_type is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTypeEqualTo(String value) {
            addCriterion("check_type =", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeNotEqualTo(String value) {
            addCriterion("check_type <>", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeGreaterThan(String value) {
            addCriterion("check_type >", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeGreaterThanOrEqualTo(String value) {
            addCriterion("check_type >=", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeLessThan(String value) {
            addCriterion("check_type <", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeLessThanOrEqualTo(String value) {
            addCriterion("check_type <=", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeLike(String value) {
            addCriterion("check_type like", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeNotLike(String value) {
            addCriterion("check_type not like", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeIn(List<String> values) {
            addCriterion("check_type in", values, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeNotIn(List<String> values) {
            addCriterion("check_type not in", values, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeBetween(String value1, String value2) {
            addCriterion("check_type between", value1, value2, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeNotBetween(String value1, String value2) {
            addCriterion("check_type not between", value1, value2, "checkType");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNull() {
            addCriterion("is_show is null");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNotNull() {
            addCriterion("is_show is not null");
            return (Criteria) this;
        }

        public Criteria andIsShowEqualTo(Boolean value) {
            addCriterion("is_show =", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotEqualTo(Boolean value) {
            addCriterion("is_show <>", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThan(Boolean value) {
            addCriterion("is_show >", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_show >=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThan(Boolean value) {
            addCriterion("is_show <", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThanOrEqualTo(Boolean value) {
            addCriterion("is_show <=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowIn(List<Boolean> values) {
            addCriterion("is_show in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotIn(List<Boolean> values) {
            addCriterion("is_show not in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowBetween(Boolean value1, Boolean value2) {
            addCriterion("is_show between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_show not between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andShowTimeIsNull() {
            addCriterion("show_time is null");
            return (Criteria) this;
        }

        public Criteria andShowTimeIsNotNull() {
            addCriterion("show_time is not null");
            return (Criteria) this;
        }

        public Criteria andShowTimeEqualTo(Date value) {
            addCriterion("show_time =", value, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeNotEqualTo(Date value) {
            addCriterion("show_time <>", value, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeGreaterThan(Date value) {
            addCriterion("show_time >", value, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("show_time >=", value, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeLessThan(Date value) {
            addCriterion("show_time <", value, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeLessThanOrEqualTo(Date value) {
            addCriterion("show_time <=", value, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeIn(List<Date> values) {
            addCriterion("show_time in", values, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeNotIn(List<Date> values) {
            addCriterion("show_time not in", values, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeBetween(Date value1, Date value2) {
            addCriterion("show_time between", value1, value2, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeNotBetween(Date value1, Date value2) {
            addCriterion("show_time not between", value1, value2, "showTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andClickNumIsNull() {
            addCriterion("click_num is null");
            return (Criteria) this;
        }

        public Criteria andClickNumIsNotNull() {
            addCriterion("click_num is not null");
            return (Criteria) this;
        }

        public Criteria andClickNumEqualTo(Integer value) {
            addCriterion("click_num =", value, "clickNum");
            return (Criteria) this;
        }

        public Criteria andClickNumNotEqualTo(Integer value) {
            addCriterion("click_num <>", value, "clickNum");
            return (Criteria) this;
        }

        public Criteria andClickNumGreaterThan(Integer value) {
            addCriterion("click_num >", value, "clickNum");
            return (Criteria) this;
        }

        public Criteria andClickNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("click_num >=", value, "clickNum");
            return (Criteria) this;
        }

        public Criteria andClickNumLessThan(Integer value) {
            addCriterion("click_num <", value, "clickNum");
            return (Criteria) this;
        }

        public Criteria andClickNumLessThanOrEqualTo(Integer value) {
            addCriterion("click_num <=", value, "clickNum");
            return (Criteria) this;
        }

        public Criteria andClickNumIn(List<Integer> values) {
            addCriterion("click_num in", values, "clickNum");
            return (Criteria) this;
        }

        public Criteria andClickNumNotIn(List<Integer> values) {
            addCriterion("click_num not in", values, "clickNum");
            return (Criteria) this;
        }

        public Criteria andClickNumBetween(Integer value1, Integer value2) {
            addCriterion("click_num between", value1, value2, "clickNum");
            return (Criteria) this;
        }

        public Criteria andClickNumNotBetween(Integer value1, Integer value2) {
            addCriterion("click_num not between", value1, value2, "clickNum");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}