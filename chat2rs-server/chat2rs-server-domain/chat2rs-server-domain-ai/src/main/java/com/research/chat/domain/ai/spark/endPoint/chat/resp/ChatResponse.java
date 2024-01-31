package com.research.chat.domain.ai.spark.endPoint.chat.resp;

import com.research.chat.domain.ai.spark.endPoint.chat.ChatHeader;
import com.research.chat.domain.ai.spark.endPoint.chat.ChatPayload;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatResponse {

    @JsonProperty("header")
    private ChatHeader chatHeader;
    @JsonProperty("payload")
    private ChatPayload chatPayload;
}
