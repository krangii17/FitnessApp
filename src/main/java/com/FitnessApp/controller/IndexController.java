package com.FitnessApp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Api(description = "This controller is used for redirect to Angular server")
@Controller
public class IndexController {

    @ApiOperation(httpMethod = "GET",
            value = "Resource to redirect to Angular server by port 4200")
    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("redirect:" + "http://localhost:4200/");
    }
}
