package com.ironman.forum.controller;

import com.ironman.forum.service.TimeLineService;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.TimeLineVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Log4j
@RequestMapping(value = "/data")
public class TimeLineController {

    @Autowired
    private TimeLineService timeLineService;

    @RequestMapping(value = "/mycircle", method = RequestMethod.GET)
    public IronResponseEntity myFriendCircle(@Valid PageRequest pageRequest, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            List<TimeLineVO> timeLineVOList = timeLineService.pageMyFriendCircle(pageRequest);
            return new IronResponseEntity(ResponseStatus.SUCCESS, timeLineVOList);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }
}
