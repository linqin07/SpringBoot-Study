package com.entity;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/12/21
 */
@Data
public class RequestDataWrapper {
    private static final String PARAMS_KEY = "params";
    private static final String SIGNATURE_KEY = "signature";

    private JsonNode source;
    private JsonNode params;
    private boolean hasParams;
    private boolean hasSignature;
    private String signature;
    private String path;
    private boolean canRead;

    public RequestDataWrapper(boolean canRead) {
        this.canRead = canRead;
    }

    /**
     * 解析json数据
     * @param source
     */
    public void parseJsonNode(JsonNode source){
        if (source != null) {
            this.source = source;
            this.params = source.path(PARAMS_KEY);
            this.hasParams = params != null && !params.isMissingNode() && !params.isNull();
            JsonNode jsonNode = source.path(SIGNATURE_KEY);
            if (!jsonNode.isMissingNode() && !jsonNode.isNull()) {
                this.signature = jsonNode.asText();
                this.hasSignature = !this.signature.trim().isEmpty();
            }
        }
    }
}
