package com.research.chat.domain.ai.spark.achieve.defaults.session;

import cn.hutool.http.ContentType;
import com.research.chat.domain.ai.spark.common.utils.JsonUtils;
import com.research.chat.domain.ai.spark.achieve.ApiData;
import com.research.chat.domain.ai.spark.achieve.Configuration;
import com.research.chat.domain.ai.spark.achieve.standard.interfaceSession.EmbeddingSession;
import com.research.chat.domain.ai.spark.common.SparkApiUrl;
import com.research.chat.domain.ai.spark.common.utils.AuthUtils;
import com.research.chat.domain.ai.spark.endPoint.embedding.req.EmbeddingRequest;
import com.research.chat.domain.ai.spark.endPoint.embedding.resp.EmbeddingResponse;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.research.chat.domain.ai.spark.common.utils.ValidationUtils.ensureNotNull;

public class DefaultEmbeddingSession implements EmbeddingSession {

    private Configuration configuration;

    public DefaultEmbeddingSession(Configuration configuration) {
        this.configuration = ensureNotNull(configuration, "configuration");
    }

    @Override
    public EmbeddingResponse embed(EmbeddingRequest embeddingRequest) {
        ApiData apiData = configuration.getSystemApiData();
        return this.embed(apiData.getApiKey(), apiData.getApiSecret(), embeddingRequest);
    }

    @Override
    @SneakyThrows
    public EmbeddingResponse embed(String apiKey, String apiSecret, EmbeddingRequest embeddingRequest) {
        // 鉴权，得到请求路径
        String authUrl = AuthUtils.getAuthUrl(AuthUtils.RequestMethod.POST.getMethod(), SparkApiUrl.ApiUrl.embeddingq.getUrl(), apiKey, apiSecret);
        // 创建请求，设置请求URL和json数据
        Request request = new Request.Builder().url(authUrl).post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()), JsonUtils.toJson(embeddingRequest))).build();
        // 发起请求，获取返回的json字符串
        String response = configuration.getOkHttpClient().newCall(request).execute().body().string();
        // 将json映射到对象上
        return JsonUtils.fromJson(response, EmbeddingResponse.class);
    }
}
