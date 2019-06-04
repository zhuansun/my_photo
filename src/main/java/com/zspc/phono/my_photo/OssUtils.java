package com.zspc.phono.my_photo;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * oss操作
 *
 * @author zhuansunpengcheng
 * @create 2018-04-19 上午11:00
 **/
public class OssUtils {


    private static final Logger logger = LoggerFactory.getLogger(OssUtils.class);

    private String accessKeyId = "阿里云oss提供";

    private String endpoint = "阿里云oss提供";

    private String accessKeySecret = "阿里云oss提供";

    private String bucketName = "阿里云oss提供";

    private String pictureURLPrefix = "阿里云oss提供";

    private String originalURLPrefix = "阿里云oss提供";

    public void uploadFile(File file) {
        OSSClient client = null;
        InputStream input = null;
        try {
            client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

            ObjectMetadata meta = new ObjectMetadata();

            input = new FileInputStream(file);
            meta.setContentLength(file.length());

            client.putObject(bucketName, file.getName(), input, meta);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {

            try {
//                if (client != null)
//                    client.shutdown();
                if (input != null) {
                    input.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }

    }

    /**
     * 上传String 一般用于上传json
     *
     * @param fileName 上传之后显示的文件名
     * @param content  String的内容
     * @return 存储此内容的oss的地址
     */
    public String uploadFile(String fileName, String content) {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ObjectMetadata meta = new ObjectMetadata();

        meta.setContentLength(content.getBytes().length);
        try {
            client.putObject(bucketName, fileName, new ByteArrayInputStream(content.getBytes()), meta);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            logger.error("上传oss发生异常");

        }

        return pictureURLPrefix + fileName;
    }

    /**
     * 上传图片
     * @param inputStream
     * @param contentLength 文件的大小，file.getSize()
     * @param fileName 上传后的文件名
     * @return 存储此内容的oss的地址
     */
    public String uploadFile(InputStream inputStream, long contentLength, String fileName) {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        ObjectMetadata meta = new ObjectMetadata();

        meta.setContentLength(contentLength);

        client.putObject(bucketName, fileName, inputStream, meta);

        return pictureURLPrefix + fileName;

    }

    public void uploadFile(InputStream inputStream, long contentLength, String fileName, String contentType) {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        ObjectMetadata meta = new ObjectMetadata();

        meta.setContentLength(contentLength);
        if (contentType != null && !"".equals(contentType)) {
            meta.setContentType(contentType);
        }

        PutObjectResult result = client.putObject(bucketName, fileName, inputStream, meta);

    }

    public boolean getFile(String prefix) {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName, prefix, "", "", null);
        ObjectListing listing = client.listObjects(listObjectsRequest);
        List<OSSObjectSummary> ossOSList = listing.getObjectSummaries();
        return ossOSList != null && !ossOSList.isEmpty() ? true : false;
    }

    public List getFileList(String prefix) {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName, prefix, "", "", null);
        ObjectListing listing = client.listObjects(listObjectsRequest);
        List<OSSObjectSummary> ossOSList = listing.getObjectSummaries();
        return ossOSList;
    }

    public String changeUrl(String url) {
        if (url == null || "".equals(url)) {
            return null;
        }
        if (url.contains(originalURLPrefix)) {
            return url.replaceAll(originalURLPrefix, pictureURLPrefix);
        }
        return url;
    }


}
