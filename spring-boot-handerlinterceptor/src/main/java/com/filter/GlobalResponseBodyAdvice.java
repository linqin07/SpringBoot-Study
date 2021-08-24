package com.filter;

import com.entity.ResultModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Description:
 * @author: LinQin
 * @date: 2020/01/08
 */
@RestControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        if (methodParameter.getMethod().getName().equals("login")) {
            return true;
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof ResultModel) {
            ((ResultModel) o).setPath(serverHttpRequest.getURI().getPath());
            return o;
        }
        // 这里进行封装返回
        ResultModel resultModel = new ResultModel();
        if (isPrimitiveOrVoid(methodParameter.getParameterType())) {
            ObjectNode resultNode = objectMapper.createObjectNode();
            resultNode.putPOJO("result", o);
            o = resultNode;
        }
        resultModel.setData(o);

        String result = null;
        try {
            result = objectMapper.writeValueAsString(resultModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        boolean isString = methodParameter.getParameterType() == String.class;
        //当返回值为字符串时，springMVC会使用StringHttpMessageConverter来处理，返回值必须为String，否则会转换异常
        if (isString) {
            serverHttpResponse.getHeaders().add("Content-Type",
                    "application/json;charset=UTF-8");
            return result;
        }

        return resultModel;
    }

    private boolean isPrimitiveOrVoid(Class<?> returnType) {
        return returnType.isPrimitive()
                || Number.class.isAssignableFrom(returnType)
                || CharSequence.class.isAssignableFrom(returnType)
                || Character.class.equals(returnType)
                || Boolean.class.equals(returnType);
    }
}
