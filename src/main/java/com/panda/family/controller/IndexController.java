package com.panda.family.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController extends BaseController{

    @RequestMapping(path = "/")
    public void index(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        response.sendRedirect("/family/index.html");
    }
}
