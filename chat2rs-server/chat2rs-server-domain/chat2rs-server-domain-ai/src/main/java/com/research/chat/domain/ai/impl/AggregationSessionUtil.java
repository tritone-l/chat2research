package com.research.chat.domain.ai.impl;

import com.research.chat.domain.ai.spark.achieve.ApiData;
import com.research.chat.domain.ai.spark.achieve.Configuration;
import com.research.chat.domain.ai.spark.achieve.defaults.DefaultSparkSessionFactory;
import com.research.chat.domain.ai.spark.achieve.defaults.listener.ChatListener;
import com.research.chat.domain.ai.spark.achieve.standard.SparkSessionFactory;
import com.research.chat.domain.ai.spark.achieve.standard.interfaceSession.AggregationSession;
import com.research.chat.domain.ai.spark.common.strategy.impl.FirstKeyStrategy;
import com.research.chat.domain.ai.spark.endPoint.chat.req.ChatRequest;
import com.research.chat.domain.ai.spark.endPoint.chat.resp.ChatResponse;
import lombok.SneakyThrows;

import java.util.Arrays;

/**
 * name：SparkInif
 * Author：tritone
 * Date：2024/1/30  22:49
 */

public class AggregationSessionUtil {
    private static AggregationSession aggregationSession;

    static {
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://spark-api.xf-yun.com/v3.1/chat/");
        // 3. 设置鉴权所需的API Key,可设置多个。
        ApiData apiData = ApiData.builder()
                .apiKey("fb721ca5e15756431b3653e6e23c4c58")
                .apiSecret("ZDM3YzNiODI0OTQ3YjA5OWFkYTdmZDA2")
                .appId("6080e0d5")
                .build();
        configuration.setKeyList(Arrays.asList(apiData));
        // 4. 设置请求时 key 的使用策略，默认实现了：随机获取 和 固定第一个Key 两种方式。
        configuration.setKeyStrategy(new FirstKeyStrategy<ApiData>());
//        configuration.setKeyStrategy(new RandomKeyStrategy<ApiData>());
        // 5. 设置代理，若不需要可不设置
//        configuration.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890)));
        // 6. 创建 session 工厂，制造不同场景的 session
        SparkSessionFactory factory = new DefaultSparkSessionFactory(configuration);
        aggregationSession = factory.openAggregationSession();
    }

    public static AggregationSession getAggregationSession(){
        return aggregationSession;
    }

    public static String getResult(ChatRequest request)  {

        final boolean[] end = {false};
        final String[] result = {""};

        AggregationSessionUtil.getAggregationSession().
                getChatSession().chat(new ChatListener(request) {
                    @SneakyThrows
                    @Override
                    public void onChatError(ChatResponse chatResponse) {
                        System.out.println(chatResponse);
                        end[0] = true;
                    }

                    @Override
                    public void onChatOutput(ChatResponse chatResponse) {
                        result[0] += (chatResponse.getChatPayload().getChoice().getTexts().get(0).getContent());
                    }

                    @Override
                    public void onChatEnd() {
                        System.out.println("当前会话结束了");
                        end[0] = true;
                    }
                });

        while (true){
            if(end[0] == true){
                return result[0];
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ignore) {

            }
        }

    }


}
