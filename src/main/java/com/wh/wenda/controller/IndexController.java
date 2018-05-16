package com.wh.wenda.controller;

import com.wh.wenda.aspect.LogAspect;
import com.wh.wenda.model.User;
import com.wh.wenda.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index(HttpSession httpSession) {
        logger.info("VISIT HOME");
        return "hello " + httpSession.getAttribute("msg");
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

    @RequestMapping(path = {"/vm"}, method = {RequestMethod.GET})
    public String template(Model model) {
        model.addAttribute("value1", "wwwwww");
        List<String> colors = Arrays.asList(new String[]{"red", "green", "black"});
        model.addAttribute("colors", colors);
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        map.put("2", "4");
        map.put("3", "9");
        model.addAttribute("map", map);
        model.addAttribute("user", new User("LEE"));
        return "home";
    }

    @RequestMapping(value = "/request", method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model, HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession httpSession,
                          @CookieValue("JSESSIONID") String sessionId) {
        StringBuilder sb = new StringBuilder();
        sb.append("COOKIEVALUE:" + sessionId);
        sb.append(request.getMethod() + "<br>");
        sb.append(request.getQueryString() + "<br>");
        sb.append(request.getPathInfo() + "<br>");
        sb.append(request.getRequestURI() + "<br>");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                sb.append("Cookie:" + cookie.getName() + " value:" + cookie.getValue());
            }
        }
        response.addHeader("noecoderId", "hello");
        response.addCookie(new Cookie("username", "noecoder"));
        return sb.toString();
    }

    @RequestMapping(value = "/redirect/{code}", method = {RequestMethod.GET})
    public RedirectView redirect(@PathVariable("code") int code,
                                 HttpSession httpSession) {
        httpSession.setAttribute("msg", "jump from redirect");
        RedirectView red = new RedirectView("/", true);
        if (code == 301) {
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @ResponseBody
    public String admin(@RequestParam(value = "key", defaultValue = "admin", required = false) String key) {
        if ("admin".equals(key))
            return "hello admin";
        throw new IllegalArgumentException("参数错误");
    }

    //网页异常处理
    @ExceptionHandler
    @ResponseBody
    public String error(Exception e) {
        return "error:" + e.getMessage() + "<br> 我错了";
    }

    @Autowired
    WendaService wendaService;

    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    @ResponseBody
    public String show() {
        return wendaService.getMessage(1);
    }
}
