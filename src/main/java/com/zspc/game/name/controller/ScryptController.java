package com.zspc.game.name.controller;

import com.zspc.game.name.domain.GameNameVo;
import com.zspc.game.name.domain.RestResponse;
import com.zspc.game.name.utils.ScryptUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class ScryptController {


    /**
     *
     */
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public RestResponse test(@RequestBody GameNameVo name) {
        RestResponse restResponse = new RestResponse();
        Map<String,String> result = new HashMap<>();
        Map<String, Object> keyMap;
        try {
            keyMap = ScryptUtils.initKey();
            String publicKey = ScryptUtils.getPublicKey(keyMap);
            String privateKey = ScryptUtils.getPrivateKey(keyMap);


        } catch (Exception e) {
            e.printStackTrace();
        }



        return restResponse;
    }




}
