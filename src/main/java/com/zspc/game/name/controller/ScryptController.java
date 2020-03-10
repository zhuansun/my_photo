package com.zspc.game.name.controller;

import com.zspc.game.name.utils.EashXleiUrlUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ScryptController {

    /**
     *
     */
    @RequestMapping(value = "/start",method = RequestMethod.GET)
    public String test(@PathVariable Long i) {
        EashXleiUrlUtils.generate(i);
        return String.valueOf(i);
    }

    public static void main(String[] args) {

        long total = 36L*36*36*36*36*36*36*36*36*36;

        long  day = 960000L;

        long year = 365;

        System.out.println("总共需要请求："+ total);
        System.out.println("需要多少天："+total/day);
        System.out.println("需要多少年："+total/day/year);

    }


}
