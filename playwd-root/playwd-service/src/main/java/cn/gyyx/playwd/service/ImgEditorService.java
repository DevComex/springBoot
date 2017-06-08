 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年5月3日下午3:57:07
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.playwd.agent.UploadAgent;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.utils.ImgEditor;

/**
 * 上传图片编辑类
 * @author lidudi
 *
 */
@Service
public class ImgEditorService {
	
	private ImgEditor imgEditor;  
	
	private UploadAgent uploadAgent;
	
	/**
	 * 裁剪图片
	 * @param request
	 * @param response
	 * @param xaxis
	 * @param yaxis
	 * @param width
	 * @param height
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public ResultBean<String> cutImg(URL imageFileUrl,Integer xaxis,Integer yaxis,
			Integer width,Integer height,Integer cutWidth,Integer cutHeight) throws ClientProtocolException, IOException {

        String result = "../cutImg"+UUID.randomUUID().toString()+".PNG";
        
        //缩放裁剪取
        File fileScale= imgEditor.scale(imageFileUrl, result, height, width);
        
        //剪切图片
        File fileCut= imgEditor.cut(fileScale, result, xaxis, yaxis, cutWidth, cutHeight);
        
        //缩放到玩家天地固定比例
        File fileScaleByRegular= imgEditor.scale(fileCut, result, 119, 214);
        
        //剪切图片
        BufferedImage bi = ImageIO.read(fileScaleByRegular);  
        int srcHeight= bi.getHeight(); // 源图高度  
        int srcWidth= bi.getWidth(); // 源图宽度  
        
        File fileCutByRegular= imgEditor.cut(fileScaleByRegular, result, 0, 0, srcWidth, srcHeight);
        
        //保存图片
        Map<String, Object> map=uploadAgent.upload(fileCutByRegular);
		return new ResultBean<>((Integer)map.get("Status")==1?true:false, (String)map.get("Message"),(String)map.get("Url"));
	}
	
	public ImgEditor getImgEditor() {
		return imgEditor;
	}

	@Autowired
	public void setImgEditor(ImgEditor imgEditor) {
		this.imgEditor = imgEditor;
	}

	public UploadAgent getUploadAgent() {
		return uploadAgent;
	}

	@Autowired
	public void setUploadAgent(UploadAgent uploadAgent) {
		this.uploadAgent = uploadAgent;
	}
}
