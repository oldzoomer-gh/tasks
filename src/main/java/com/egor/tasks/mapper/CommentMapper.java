package com.egor.tasks.mapper;

import com.egor.tasks.dto.CommentDto;
import com.egor.tasks.dto.UserDto;
import com.egor.tasks.entity.Comments;
import com.egor.tasks.entity.User;
import com.egor.tasks.mapper.util.PasswordEncoderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = PasswordEncoderMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    Comments map(CommentDto dto);
	CommentDto map(Comments comment);
    UserDto map(User user);
}
