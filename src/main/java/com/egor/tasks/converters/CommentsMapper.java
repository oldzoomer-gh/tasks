package com.egor.tasks.converters;

import com.egor.tasks.dto.CommentsDto;
import com.egor.tasks.entity.Comments;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    CommentsDto toDto(Comments comments);
    Comments toEntity(CommentsDto commentsDto);
}
