package com.filter;

import com.entity.EmptyBodyCheckingHttpInputMessage;
import com.entity.RequestDataWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * @Description: 拦截器给对象注入数据，保存到request.setAttribute中
 * @author: LinQin
 * @date: 2019/12/21
 */
public class ParamHandle implements HandlerInterceptor {
    private static final String JSON_REQUEST_BODY = "JSON_REQUEST_BODY";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求数据
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
        RequestDataWrapper requestDataWrapper = new RequestDataWrapper(true);
        requestDataWrapper.parseJsonNode(read(servletServerHttpRequest, (HandlerMethod) handler));
        request.setAttribute(JSON_REQUEST_BODY, requestDataWrapper);
        return true;
    }

    /**
     * 求体输入流中读取数据
     * 解析成IsonNode对象
     *
     * @param inputMessage
     * @param handlerMethod
     * @return
     * @throws IOException
     */
    public JsonNode read(@NotNull HttpInputMessage inputMessage, @NotNull HandlerMethod handlerMethod) throws IOException {
        EmptyBodyCheckingHttpInputMessage message;
        try {
            message = new EmptyBodyCheckingHttpInputMessage(inputMessage);
            if (message.hasBody()) {
                return objectMapper.readTree(message.getBody());
            }
            return null;
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
