package com.zspc.photo.my_photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试controlelr
 **/
@Controller
public class SubmitController {

    @Autowired
    private SubmitService submitService;

    /**
     * 跳转到项目首页
     */
    @RequestMapping("/index")
    public String test() {
        return "/index";
    }

    /**
     * 跳转到项目首页
     */
    @RequestMapping("/success")
    public String success() {
        return "/success";
    }

    /**
     * 开始上传
     */
    @RequestMapping("/submit")
    @ResponseBody
    public SubmitResponse submit(@RequestBody SubmitRequest submitVo) {
        String userName = submitVo.getUsername();
        String password = submitVo.getPassword();
        String verification = submitVo.getVerification();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password) || StringUtils
            .isEmpty(verification)) {
            throw new SubmitException(SubmitException.SUBMIT_EXCEPTION_ERROR_PARAMS);
        }

        if (!submitService.checkEmail(userName)) {
            throw new SubmitException(SubmitException.SUBMIT_EXCEPTION_ERROR_EMAIL);
        }
        if (!submitService.checkVeri(verification)) {
            throw new SubmitException(SubmitException.SUBMIT_EXCEPTION_ERROR_VERIFICATION);

        }
        boolean submit = submitService.handleSubmit(userName, password, verification);
        if (submit) {
            return SubmitResponse.buildSuccess();
        }
        return SubmitResponse.buildUnkownError();
    }

    /**
     * 开始上传
     */
    @RequestMapping("/successMail")
    @ResponseBody
    public SubmitResponse successMail(@RequestBody SubmitRequest submitVo) {
        String userName = submitVo.getUsername();
        if (!submitService.checkEmail(userName)) {
            throw new SubmitException(SubmitException.SUBMIT_EXCEPTION_ERROR_EMAIL);
        }
        submitService.handleSuccess(userName);
        return SubmitResponse.buildSuccess();
    }

}
