package com.egor.tasks.converters.input;

import com.egor.tasks.dto.input.CreateCommentsDto;
import com.egor.tasks.entity.Comments;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentsInputMapper {
    Comments map(CreateCommentsDto createCommentsDto);
}
