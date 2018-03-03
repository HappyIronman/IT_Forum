package com.ironman.forum.controller;

import com.ironman.forum.entity.User;
import com.ironman.forum.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomepageController {

    private static Log logger = LogFactory.getLog(HomepageController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public String test(Long id, ModelMap map) {
        logger.info("hahaha: " + id);
        User user = userService.getUser(id);
        map.addAttribute("user", user);
        return "index";
    }

    @RequestMapping(value = "hello")
    public String test2(){
        return "index";
    }

}
