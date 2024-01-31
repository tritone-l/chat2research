package com.research.chat.domain.ai;


import com.research.chat.domain.ai.spark.achieve.ApiData;
import com.research.chat.domain.ai.spark.achieve.Configuration;
import com.research.chat.domain.ai.spark.achieve.defaults.DefaultSparkSessionFactory;
import com.research.chat.domain.ai.spark.achieve.defaults.listener.ChatListener;
import com.research.chat.domain.ai.spark.achieve.defaults.listener.DocumentChatListener;
import com.research.chat.domain.ai.spark.achieve.standard.SparkSessionFactory;
import com.research.chat.domain.ai.spark.achieve.standard.interfaceSession.AggregationSession;
import com.research.chat.domain.ai.spark.common.strategy.impl.FirstKeyStrategy;
import com.research.chat.domain.ai.spark.endPoint.chat.ChatText;
import com.research.chat.domain.ai.spark.endPoint.chat.req.ChatRequest;
import com.research.chat.domain.ai.spark.endPoint.chat.req.DocumentChatRequest;
import com.research.chat.domain.ai.spark.endPoint.chat.resp.ChatResponse;
import com.research.chat.domain.ai.spark.endPoint.chat.resp.DocumentChatResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ChatApiTest {

    private AggregationSession aggregationSession;

    @Before
    public void before() {
        // 1. 创建配置类
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
        this.aggregationSession = factory.openAggregationSession();
    }

    /**
     * 测试聊天功能
     */
    @Test
    public void test_chat() {
        // 创建参数
        ChatRequest request = ChatRequest.baseBuild("把接下去这段文本的人名提取出来,要非常确认，用逗号分隔,否则返回'EMPTY'：Glutamine Production " +
                "by " +
                "Glul" +
                " " +
                "Promotes" +
                " " +
                "Thermogenic Adipocyte Differentiation Through Prdm9-Mediated H3K4me3 and Transcriptional Reprogramming ,2,6,7 Diabetes 2023;72:1574–1596 | https://doi.org/10.2337/db23-0162 Thermogenic adipocytes have been extensively investigated because of their energy-dissipating property and therapeutic potential for obesity and diabetes. Besides serving as fuel sources, accumulating evidence suggests that intermediate metabolites play critical roles in multiple biological processes. However, their role in adi");

        final boolean[] end = {false};

        // 设置参数并发起请求，监听事件信息
        aggregationSession.getChatSession().chat(new ChatListener(request) {
            @SneakyThrows
            @Override
            public void onChatError(ChatResponse chatResponse) {
                System.out.println(chatResponse);
                end[0] = true;
            }

            @Override
            public void onChatOutput(ChatResponse chatResponse) {
                System.out.println(chatResponse.getChatPayload().getChoice().getTexts().get(0).getContent());
            }
            @Override
            public void onChatEnd() {
                end[0] = true;
            }
        });
        // 等待会话结束
        while (true){
            if ( end[0] == true){
                break;
            }
            try {
                Thread.sleep(30L);
            } catch (InterruptedException ignore) {
            }
        }
    }

    /**
     * 测试多轮聊天功能
     */
    @Test
    public void test_chat_multiple() {
        // 创建参数
        ChatRequest request = ChatRequest.baseBuild("1+1=");
        // 设置第一轮对话的结果
        ChatText chatText1 = ChatText.baseBuild(ChatText.Role.ASSISTANT, "2");
        // 设置第二轮对话的问题
        ChatText chatText2 = ChatText.baseBuild(ChatText.Role.USER, "2+2=");
        // 将对话过程注入到参数当中
        request.getChatPayload().getMessage().getChatTexts().add(chatText1);
        request.getChatPayload().getMessage().getChatTexts().add(chatText2);
        // 设置参数并发起请求，监听事件信息
        aggregationSession.getChatSession().chat(new ChatListener(request) {
            @Override
            public void onChatError(ChatResponse chatResponse) {
                System.out.println(chatResponse);
            }

            @Override
            public void onChatOutput(ChatResponse chatResponse) {
                System.out.print(chatResponse.getChatPayload().getChoice().getTexts().get(0).getContent());
            }

            @Override
            public void onChatEnd() {
                System.out.println("当前会话结束了");
            }

        });
        // 等待会话结束
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试文档对话功能
     */
    @Test
    public void test_document_chat() {
        // 构建参数
        DocumentChatRequest documentChatRequest = DocumentChatRequest.baseBuild("总结一下故事一说了什么？", Arrays.asList("c42a68fd31964d43b4f57eab11e9a833"));
        // 设置阐述并发起请求
        aggregationSession.getChatSession().documentChat(new DocumentChatListener(documentChatRequest) {
            @Override
            public void onChatError(DocumentChatResponse documentChatResponse) {
                System.err.println(documentChatResponse);
            }

            @Override
            public void onChatOutput(DocumentChatResponse documentChatResponse) {
                System.out.println(documentChatResponse);
            }

            @Override
            public void onChatEnd() {
                System.out.println("当前会话结束了");
            }
        });
        // 等待会话结束
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}