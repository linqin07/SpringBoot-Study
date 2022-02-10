package com.convert;

import com.pojo.User;
import com.util.JacksonUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = JacksonUtil.class)
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    @Mappings({
            // 转化的名称如果一样，写不写都一样
//            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "userAge", target = "userAge", ignore = true),
            @Mapping(source = "userName", target = "userName", qualifiedByName = "toJsonString"),
    })
    UserDTO convert(User user);

}
