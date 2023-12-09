package com.egor.tasks.converters.output;

import com.egor.tasks.dto.output.OutputCommentsDto;
import com.egor.tasks.dto.output.OutputUserDto;
import com.egor.tasks.entity.Comments;
import com.egor.tasks.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentsOutputMapper {
    OutputCommentsDto toDisplayDto(Comments comments);
    OutputUserDto toDisplayUserDto(User user);
}
