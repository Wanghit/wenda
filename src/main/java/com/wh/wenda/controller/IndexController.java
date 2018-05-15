package com.wh.wenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index() {
        return "hello";
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "type", defaultValue = "1", required = true) int type,
                          @RequestParam(value = "key", defaultValue = "2", required = false) String key) {
        return "Profile Page of " + groupId + " " + userId + " " + type
                + " " + key;
    }
    @RequestMapping(path = {"/vm"},method = {RequestMethod.GET})
    public String template(Model model) {
        model.addAttribute("value1","wwwwww");
        return "home";
    }
}
