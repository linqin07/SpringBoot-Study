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
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "userAge", target = "userAge", ignore = true),
            @Mapping(source = "userName", target = "userName", qualifiedByName = "toJsonString"),
    })
    UserDTO convert(User user);

}
