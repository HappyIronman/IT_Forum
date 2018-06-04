package com.ironman.forum.controller;

import com.ironman.forum.service.CommonService;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronResponseEntity;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.ImageVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 公有接口控制器
 */
@RestController
@RequestMapping("/data")
@Log4j
public class CommonController {

    @Autowired
    private CommonService commonService;

    /**
     * 上传图片
     *
     * @param images
     * @return
     */
    @RequestMapping(value = "/upload/pic")
    @ResponseBody
    public IronResponseEntity uploadPics(@RequestParam("pics") MultipartFile[] images) throws GlobalException {

        List<ImageVO> imageVOList = commonService.saveImages(images);
        return new IronResponseEntity(ResponseStatus.SUCCESS, imageVOList);
    }
}
