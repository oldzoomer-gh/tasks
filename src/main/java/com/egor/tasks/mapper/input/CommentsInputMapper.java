package com.egor.tasks.mapper.input;

import com.egor.tasks.dto.input.CreateCommentsDto;
import com.egor.tasks.entity.Comments;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentsInputMapper {
    Comments map(CreateCommentsDto createCommentsDto);
}
