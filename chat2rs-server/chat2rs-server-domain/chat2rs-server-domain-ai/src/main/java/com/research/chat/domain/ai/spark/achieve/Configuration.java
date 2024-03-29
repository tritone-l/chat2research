package com.research.chat.domain.ai.spark.achieve;

import com.research.chat.domain.ai.spark.achieve.standard.api.SparkApiServer;
import com.research.chat.domain.ai.spark.common.strategy.KeyStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.net.Proxy;
import java.util.List;

/**
 * @Description: 配置信息
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {

    /**
     * api 服务提供者
     */
    private SparkApiServer sparkApiServer;

    /**
     * api 请求客户端
     */
    private OkHttpClient okHttpClient;

    /**
     * api Key 集合
     */
    @NotNull
    private List<ApiData> keyList;

    /**
     * 请求地址（很多情况下，这个apiHost都是一个摆设）
     */
    @NotNull
    private String apiHost;

    /**
     * 获取key的策略
     */
    private KeyStrategy keyStrategy;

    /**
     * 代理信息
     */
    private Proxy proxy;

    public ApiData getSystemApiData() {
        return (ApiData) keyStrategy.apply(keyList);
    }

}
