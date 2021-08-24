package com.filter;

import com.entity.RequestDataWrapper;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;

/**
 *
 * @Description: 自定义入参解析器
 * @author: LinQin
 * @date: 2019/12/21
 */
@Component
public class CostomHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String JSON_REQUEST_BODY = "JSON_REQUEST_BODY";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        // 多个参数，会多次经过这里处理
        RequestDataWrapper requestDataWrapper = getRequestBody(nativeWebRequest);
        if (requestDataWrapper == null) {
            String path = null;
//            Assert.isInstanceOf(nativeWebRequest.getClass(), ServletWebRequest.class);
            if (nativeWebRequest instanceof ServletWebRequest) {
                path = ((ServletWebRequest) nativeWebRequest).getRequest().getServletPath();
            }
            throw new IllegalArgumentException(String.format("无法注入'%s'参数，当前不是JSON请求, path=%s", methodParameter.getParameterName(), path != null ? path : methodParameter.getMethod()));
        }
        methodParameter = methodParameter.nestedIfOptional();
        Object arg = readWithRequestData(requestDataWrapper, methodParameter, methodParameter.getNestedGenericParameterType());
        arg = handleNullValue(methodParameter.getParameterName(), arg, methodParameter.getParameterType());

        return adaptArgumentIfNecessary(arg, methodParameter);
    }

    private RequestDataWrapper getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        RequestDataWrapper requestDataWrapper = (RequestDataWrapper) servletRequest.getAttribute(JSON_REQUEST_BODY);

        return requestDataWrapper;
    }

    private Object readWithRequestData(RequestDataWrapper requestDataWrapper, MethodParameter parameter, Type parameterType) throws Exception {

        if (requestDataWrapper.isHasParams()) {
            JsonNode params = requestDataWrapper.getParams();
            if (params.hasNonNull(parameter.getParameterName())) {
                JavaType javaType = getJavaType(parameterType, null);
                JsonNode paramValue = params.path(parameter.getParameterName());
                try {
                    return objectMapper.convertValue(paramValue, javaType);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!BeanUtils.isSimpleProperty(parameter.getNestedParameterType())) {
            // Create attribute instance
            Object attribute = null;
            try {
                attribute = createAttribute(parameter);
            } catch (BindException ex) {

                // Otherwise, expose null/empty value and associated BindingResult
                if (parameter.getParameterType() == Optional.class) {
                    attribute = Optional.empty();
                }
            }
            return attribute;
        }
        return null;
    }

    protected JavaType getJavaType(Type type, @Nullable Class<?> contextClass) {
        TypeFactory typeFactory = this.objectMapper.getTypeFactory();
        return typeFactory.constructType(GenericTypeResolver.resolveType(type, contextClass));
    }

    protected Object createAttribute(MethodParameter parameter) throws Exception {
        MethodParameter nestedParameter = parameter.nestedIfOptional();
        Class<?> clazz = nestedParameter.getNestedParameterType();
        if (clazz.isInterface()) {
            return null;
        }

        Constructor<?> ctor = BeanUtils.findPrimaryConstructor(clazz);
        if (ctor == null) {
            Constructor<?>[] ctors = clazz.getConstructors();
            if (ctors.length == 1) {
                ctor = ctors[0];
            } else {
                try {
                    ctor = clazz.getDeclaredConstructor();
                } catch (NoSuchMethodException ex) {
                    throw new IllegalStateException("No primary or default constructor found for " + clazz, ex);
                }
            }
        }

        Object attribute = null;
        if (ctor.getParameterCount() == 0) {
            // A single default constructor -> clearly a standard JavaBeans arrangement.
            attribute = BeanUtils.instantiateClass(ctor);
        }
        if (parameter != nestedParameter) {
            attribute = Optional.of(attribute);
        }
        return attribute;
    }

    private Object handleNullValue(String name, @Nullable Object value, Class<?> paramType) {
        if (value == null) {
            if (Boolean.TYPE.equals(paramType)) {
                return Boolean.FALSE;
            } else if (paramType.isPrimitive()) {
                if (boolean.class.isAssignableFrom(paramType)) {
                    return false;
                }
                if (long.class.isAssignableFrom(paramType)
                        || int.class.isAssignableFrom(paramType)
                        || short.class.isAssignableFrom(paramType)
                        || float.class.isAssignableFrom(paramType)
                        || double.class.isAssignableFrom(paramType)
                        || byte.class.isAssignableFrom(paramType)) {
                    return 0;
                }
                throw new IllegalStateException("Optional " + paramType.getSimpleName() + " parameter '" + name +
                        "' is present but cannot be translated into a null value due to being declared as a " +
                        "primitive type. Consider declaring it as object wrapper for the corresponding primitive type.");
            }
        }
        return value;
    }

    protected Object adaptArgumentIfNecessary(@Nullable Object arg, MethodParameter parameter) {
        if (parameter.getParameterType() == Optional.class) {
            if (arg == null || (arg instanceof Collection && ((Collection) arg).isEmpty()) ||
                    (arg instanceof Object[] && ((Object[]) arg).length == 0)) {
                return Optional.empty();
            }
            else {
                return Optional.of(arg);
            }
        }
        return arg;
    }
}
