package com.research.chat.domain.ai.spark.achieve.defaults.session;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.research.chat.domain.ai.spark.achieve.ApiData;
import com.research.chat.domain.ai.spark.achieve.Configuration;
import com.research.chat.domain.ai.spark.achieve.standard.api.SparkApiServer;
import com.research.chat.domain.ai.spark.achieve.standard.interfaceSession.DocumentSession;
import com.research.chat.domain.ai.spark.common.SparkApiUrl;
import com.research.chat.domain.ai.spark.common.utils.AuthUtils;
import com.research.chat.domain.ai.spark.endPoint.document.req.FileUploadRequest;
import com.research.chat.domain.ai.spark.endPoint.document.resp.DocumentSummaryResponse;
import com.research.chat.domain.ai.spark.endPoint.document.resp.FileUploadResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.Map;

import static com.research.chat.domain.ai.spark.common.utils.ValidationUtils.ensureNotNull;


public class DefaultDocumentSession implements DocumentSession {

    private Configuration configuration;

    private SparkApiServer sparkApiServer;

    public DefaultDocumentSession(Configuration configuration) {
        this.configuration = ensureNotNull(configuration, "configuration");
        this.sparkApiServer = ensureNotNull(configuration.getSparkApiServer(), "sparkApiServer");
    }

    @Override
    public FileUploadResponse fileUpload(FileUploadRequest fileUploadRequest) {
        ApiData apiData = configuration.getSystemApiData();
        return this.fileUpload(apiData.getAppId(), apiData.getApiSecret(), fileUploadRequest);
    }

    @Override
    public FileUploadResponse fileUpload(String appId, String apiSecret, FileUploadRequest fileUploadRequest) {
        // 得到当前时间戳，按秒计算
        long ts = DateUtil.currentSeconds();
        // 设置文件
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileUploadRequest.getFile());
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileUploadRequest.getFile().getName(), fileBody);
        // 设置其余参数
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        if (StrUtil.isNotBlank(fileUploadRequest.getUrl())) {
            requestBodyMap.put(FileUploadRequest.Fields.url, RequestBody.create(MediaType.parse("multipart/form-data"), fileUploadRequest.getUrl()));
        }
        if (StrUtil.isNotBlank(fileUploadRequest.getFileName())) {
            requestBodyMap.put(FileUploadRequest.Fields.fileName, RequestBody.create(MediaType.parse("multipart/form-data"), fileUploadRequest.getFileName()));
        }
        if (StrUtil.isNotBlank(fileUploadRequest.getFileType())) {
            requestBodyMap.put(FileUploadRequest.Fields.fileType, RequestBody.create(MediaType.parse("multipart/form-data"), fileUploadRequest.getFileType()));
        }
        if (StrUtil.isNotBlank(fileUploadRequest.getCallbackUrl())) {
            requestBodyMap.put(FileUploadRequest.Fields.callbackUrl, RequestBody.create(MediaType.parse("multipart/form-data"), fileUploadRequest.getCallbackUrl()));
        }
        // 发起请求返回结果
        return sparkApiServer.fileUpload(appId, String.valueOf(ts), AuthUtils.getSignature(appId, apiSecret, ts), multipartBody, requestBodyMap).blockingGet();
    }

    @Override
    public DocumentSummaryResponse documentSummaryStart(String fileId) {
        ApiData apiData = configuration.getSystemApiData();
        return this.documentSummaryStart(apiData.getAppId(), apiData.getApiSecret(), fileId);
    }

    @Override
    public DocumentSummaryResponse documentSummaryStart(String appId, String apiSecret, String fileId) {
        return this.documentSummary(SparkApiUrl.ApiUrl.documentSummaryStart.getUrl(), appId, apiSecret, fileId);
    }

    @Override
    public DocumentSummaryResponse documentSummaryQuery(String fileId) {
        ApiData apiData = configuration.getSystemApiData();
        return this.documentSummaryQuery(apiData.getAppId(), apiData.getApiSecret(), fileId);
    }

    @Override
    public DocumentSummaryResponse documentSummaryQuery(String appId, String apiSecret, String fileId) {
        return this.documentSummary(SparkApiUrl.ApiUrl.documentSummaryQuery.getUrl(), appId, apiSecret, fileId);
    }

    /**
     * 文档总结底层都依赖这个方法
     *
     * @param url       请求的URL
     * @param appId     用户的AppId
     * @param apiSecret 用户的ApiSecret
     * @param fileId    文件ID
     * @return 请求结果
     */
    private DocumentSummaryResponse documentSummary(String url, String appId, String apiSecret, String fileId) {
        long ts = DateUtil.currentSeconds();
        return sparkApiServer.documentSummary(url, appId, String.valueOf(ts), AuthUtils.getSignature(appId, apiSecret, ts), RequestBody.create(MediaType.parse("multipart/form-data"), fileId)).blockingGet();
    }

}
