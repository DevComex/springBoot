package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.wdninestory.ResultBean;

@Controller
@RequestMapping(value = "/wdninestory")
public class WDNineStoryController {

	@RequestMapping("/index")
	public String index(Model model) {
		return "wdninestory/index";
	}


	/**
	 * 
	 * @日期：2015年3月12日
	 * @Title: vote
	 * @Description: TODO 投票
	 * @param writing
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping("/vote")
	@ResponseBody
	public ResultBean<Integer> vote(HttpServletRequest request) {

		ResultBean<Integer> result = new ResultBean<Integer>(false, "谢谢参与，活动已结束", 0);
		
		return result;
	}

	/**
         * 
         * @日期：2015年3月12日
         * @Title: getVote
         * @Description: TODO 获得每个作品的投票数量
         * @return ResultBean<List<WritingBean>>
         */
        @RequestMapping("/getVote")
        @ResponseBody
        public ResultBean getVote() {
            
                return new ResultBean<>(false, "谢谢参与，活动已结束", 0);
        }

        /**
         * 
         * @日期：2015年3月12日
         * @Title: getTotal
         * @Description: TODO 获取类别投票总数
         * @return ResultBean<List<TotalBean>>
         */
        @RequestMapping("/getTotal")
        @ResponseBody
        public ResultBean getTotal() {
            
                return new ResultBean<>(false, "谢谢参与，活动已结束", 0);
        }
}
