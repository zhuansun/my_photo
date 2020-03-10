package com.zspc.game.name.utils;


import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 生成所有的url
 */
public class EashXleiUrlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(EashXleiUrlUtils.class);

    /**
     * 10位的随机字符串
     */
    private static String[] seeds = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static void generate(long i) {
        String url = "http://amm.la/q_91";
        for (String a1 : seeds) {
            for (String a2 : seeds) {
                for (String a3 : seeds) {
                    for (String a4 : seeds) {
                        for (String a5 : seeds) {
                            for (String a6 : seeds) {
                                for (String a7 : seeds) {
                                    for (String a8 : seeds) {
                                        for (String a9 : seeds) {
                                            for (String a10 : seeds) {
                                                i++;
                                                request(url+a1+a2+a3+a4+a5+a6+a7+a8+a9+a10);
                                                if(i % 100 ==0){
                                                    System.out.println(i);
                                                    i=0L;
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private static void request(String url){

        try {
            Request requestGET = HttpUtils.createGetRequest(url);
            Response responseGET = HttpUtils.buildHttpClient(null).newCall(requestGET).execute();
            String responseBodyStringGET = responseGET.body().string();

            if (!responseBodyStringGET.contains("该提取地址不存在")){
                LOGGER.info(url);
            }

        } catch (IOException e) {
            System.err.println(url);
            e.printStackTrace();
        }
    }

}
