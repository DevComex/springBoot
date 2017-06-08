/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-ui
 * @作者：caishuai
 * @联系方式：caishuai@gyyx.cn
 * @创建时间：2017年3月13日 上午12:39:15
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.google.common.base.Throwables;

import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 将servlet的/**的请求映射到对应的html资源位置
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
@SuppressWarnings("serial")
public class StaticViewServlet extends HttpServlet {

    public static final Logger logger = GYYXLoggerFactory
            .getLogger(StaticViewServlet.class);

    /**
     * 将/wd的静态页面请求转发到html下对应目录的.html
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = StringUtils.stripEnd(req.getPathInfo(), "/");
        // 判定请求是否为根目录
        if (StringUtils.isEmpty(path)) {
            req.getRequestDispatcher("/").forward(req, resp);
        } else {
            // 当请求为标识符下的一级目录，默认直接访问该目录下的index页
            if (path.lastIndexOf("/") == 0) {
                path += "/index";
            }
            String webRoot = getServletContext().getRealPath("/");
            File htmlFile = new File(webRoot + "/html" + path + ".html");
            // 默认先判定.html存在不存在
            if (!htmlFile.exists()) {
                htmlFile = new File(webRoot + "/html" + path + ".html.wechat");
                // 不存在后判定/html.wechat是否存在
                if (!htmlFile.exists()) {
                    // 返回404
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "File not found");
                    return;
                } else if (!checkIsWeiXin(req, resp)) {
                    // 判定不是微信浏览器
                    return;
                }
            }
            // 输出view
            outPutView(htmlFile, resp);

        }
    }

    /**
     * post转get
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    /**
     * 
     * <p>
     * 将文件输出到视图
     * </p>
     *
     * @action caishuai 2017年3月13日 下午7:09:26 描述
     *
     * @param file
     *            文件
     * @param response
     *            void
     */
    private void outPutView(File file, HttpServletResponse response) {
        FileInputStream fileInputStream;
        OutputStream outputStream;
        int n = 512;
        int len = 0;
        byte[] buffer = new byte[n];
        response.setHeader("content-type", "text/html;charset=UTF-8");
        try {
            fileInputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            try {
                while (((len = fileInputStream.read(buffer, 0, n)) != -1)
                        && (n > 0)) {
                    outputStream.write(buffer, 0, len);
                }
            } catch (Exception e) {
                logger.warn("write response error ,error stack:{}",
                    Throwables.getStackTraceAsString(e));
            } finally {
                fileInputStream.close();
                outputStream.flush();
                outputStream.close();
            }

        } catch (FileNotFoundException e1) {
            logger.warn("get file error,error stack:{}",
                Throwables.getStackTraceAsString(e1));
            e1.printStackTrace();
        } catch (IOException e) {
            logger.warn("IO operation error,error stack:{}",
                Throwables.getStackTraceAsString(e));
        }

    }

    /**
     * 
     * <p>
     * 判断是否为微信浏览器，不是则默认输出错误提示
     * </p>
     *
     * @action caishuai 2017年3月13日 下午7:21:59 描述
     *
     * @param request
     * @param response
     * @return boolean
     */
    private boolean checkIsWeiXin(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            String ua = request.getHeader("user-agent").toLowerCase();

            if (ua.indexOf("micromessenger") == -1
                    || ua.indexOf("windows") > -1) {// 不是微信浏览器
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter()
                        .write(
                            "<!doctype html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no'></head><body><div style='text-align: center;padding-top:36px;'><p style='margin:0 auto;margin-bottom: 30px;width:92px;height:92px;background:url(http://static.cn114.cn/action/nationalday/images/warn.png) no-repeat'></p><div style='margin-bottom: 25px;padding: 0 20px;'><h4 style='padding:0;margin:0;margin-bottom: 5px;font-weight: 400;font-size: 1.2rem;'>请在微信客户端打开链接<h4></div></div></body></html>");
                response.getWriter().flush();
                response.getWriter().close();
                return false;
            }
        } catch (Exception e) {
            logger.error("检查是否是微信浏览器失败,错误堆栈:{}",
                Throwables.getStackTraceAsString(e));
        }
        return true;
    }
}