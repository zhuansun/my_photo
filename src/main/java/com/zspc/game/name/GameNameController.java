package com.zspc.game.name;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/game")
public class GameNameController {


    /**
     *
     */
    @RequestMapping(value = "/ok", method = RequestMethod.POST)
    public RestResponse test(@RequestBody GameNameVo name) {
        RestResponse restResponse = new RestResponse();
        String gameName = name.getName();
        char[] chars = gameName.toCharArray();
        if (chars.length > 6) {
            restResponse.setCode(100);
            restResponse.setMsg("王者荣耀最大支持6个汉字");
        } else {
            //正常的
            List<String> result = new ArrayList<>();
            for (int i = 0; i <= 6 - chars.length; i++) {
                List<String> strings = generateBlankName(gameName, i);
                result.addAll(strings);
            }
            restResponse.setData(result);

        }

        return restResponse;
    }



    public List<String> generateBlankName(String name, Integer count) {
        Set<String> params = new HashSet<>();
        params.add(name);
        return new ArrayList<>(generate(params, count, "\u200B"));
    }


    private Set<String> generate(Set<String> params, int loop, String code) {
        if (loop <= 0) {
            return params;
        }


        Set<String> aa = new HashSet<>();
        for (String param : params) {
            aa.addAll(addBlank(param, code));
        }
        if (--loop > 0) {
            aa.addAll(generate(aa, loop, code));
        }
        return aa;
    }


    /**
     * 对一个string，添加空格
     */
    private Set<String> addBlank(String param, String code) {
        Set<String> result = new HashSet<>();
        char[] chars = param.toCharArray();
        StringBuilder buffer;
        for (int i = 0; i < chars.length + 1; i++) {
            buffer = new StringBuilder(param);
            result.add(buffer.insert(i, code).toString());
        }
        return result;
    }


}
