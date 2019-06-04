package com.zspc.phono.my_photo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 测试controlelr
 *
 * @author zhuansunpengcheng
 * @create 2019-06-04 5:30 PM
 **/
@Controller
public class FileController {


    private OssUtils ossUtils = new OssUtils();

    /**
     * 跳转到项目首页
     * @return
     */
    @RequestMapping("/index")
    public String test(){
        return "/index";
    }


    /**
     * 开始上传
     * @param files
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String test1(@RequestBody MultipartFile files){

        String url;

        try {
            url =  ossUtils.uploadFile(files.getInputStream(),files.getSize(),files.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            url = files.getOriginalFilename()+"-->上传失败";
        }

        return url;
    }



}
