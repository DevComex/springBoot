/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/1 15:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wish11th;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 问道许愿活动常量类
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class Constants {
    public static final int ACTIVITY_ID = 454;
    public static final String ACTIVITY_NAME = "问道周年许愿活动";
    public static final String ACTIVITY_ENGLISHNAME = "wish11th";
    public static final String ACTIVITY_TYPE = "lottery";
    // 上传视频状态 0:未审核 1:通过审核 -1:未通过
    public static final int WISH11TH_VERIFYED = 1;// 审核通过
    public static final int WISH11TH_REFUSED = -1;// 审核拒绝
    public static final int WISH11TH_VERIFYPENDING = 0;// 未审核
    public static final int WISH11TH_ALLSTATUS = 100;// 全部状态

    public static final String ROLEBIND_PRIFIX = "wish11th_lottery_addrolebind";

    // 用户抽奖时分布式锁前缀
    public static final String LOTTERY_PRIFIX = ACTIVITY_ID + "_lottery";
    // 错误日志的前缀
    public static final String ERROR_LOG = ACTIVITY_NAME + "_" + ACTIVITY_ID
            + "_ERROR_LOG:";
    // 活动归属的游戏编号
    public static final int GAMEID = 2;

    // 每层对应的ActionCode
    public static enum Wish11thMapperActionCode {
        FIRST_FLOOR(1, 454), SECOND_FLOOR(2, 455), THIRD_FLOOR(3,
                456), FOURTH_FLOOR(4,
                        457), FIVE_FLOOR(5, 458), SIX_FLOOR(6, 459);

        private int level;
        private int actionCode;

        private Wish11thMapperActionCode(int level, int actionCode) {
            this.level = level;
            this.actionCode = actionCode;
        }

        public static int getActionCode(int level) {
            int actionCode = 454;
            if (level == 1) {
                actionCode = Wish11thMapperActionCode.FIRST_FLOOR.actionCode;
            } else if (level == 2) {
                actionCode = Wish11thMapperActionCode.SECOND_FLOOR.actionCode;
            } else if (level == 3) {
                actionCode = Wish11thMapperActionCode.THIRD_FLOOR.actionCode;
            } else if (level == 4) {
                actionCode = Wish11thMapperActionCode.FOURTH_FLOOR.actionCode;
            } else if (level == 5) {
                actionCode = Wish11thMapperActionCode.FIVE_FLOOR.actionCode;
            } else if (level == 6) {
                actionCode = Wish11thMapperActionCode.SIX_FLOOR.actionCode;
            }
            return actionCode;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getActionCode() {
            return actionCode;
        }

        public void setActionCode(int actionCode) {
            this.actionCode = actionCode;
        }
    }
}
