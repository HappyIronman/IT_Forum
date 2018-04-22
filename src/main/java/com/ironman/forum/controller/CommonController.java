package com.ironman.forum.controller;

import com.ironman.forum.form.LikeArticleFormBean;
import com.ironman.forum.service.CommonService;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronResponseEntity;
import com.ironman.forum.util.IronUtil;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.ImageVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/data")
@Log4j
public class CommonController {

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/article/like", method = RequestMethod.POST)
    public IronResponseEntity likeArticle(@RequestBody @Valid LikeArticleFormBean form, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            commonService.likeArticle(form);
            return new IronResponseEntity(ResponseStatus.SUCCESS);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }

    @RequestMapping(value = "/article/cancel_like", method = RequestMethod.POST)
    public IronResponseEntity cancelLikeArticle(@RequestBody @Valid LikeArticleFormBean form, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            commonService.cancelLikeArticle(form);
            return new IronResponseEntity(ResponseStatus.SUCCESS);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }

    @RequestMapping(value = "/upload/pic")
    @ResponseBody
    public List<ImageVO> uploadPics(@RequestParam("pics") MultipartFile[] images) {

        List<ImageVO> imageVOList = null;
        try {
            imageVOList = commonService.saveImages(images);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
        }

        return imageVOList;
    }
}
