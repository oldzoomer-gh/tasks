package com.egor.tasks.controller.dto.converters.input;

import com.egor.tasks.controller.dto.input.CreateCommentsDto;
import com.egor.tasks.entity.Comments;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentsInputMapper {
    Comments map(CreateCommentsDto createCommentsDto);
}
