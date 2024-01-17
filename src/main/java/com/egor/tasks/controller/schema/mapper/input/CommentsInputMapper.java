package com.egor.tasks.controller.schema.mapper.input;

import com.egor.tasks.controller.schema.dto.input.CreateCommentsDto;
import com.egor.tasks.dao.entity.Comments;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentsInputMapper {
    Comments map(CreateCommentsDto createCommentsDto);
}
