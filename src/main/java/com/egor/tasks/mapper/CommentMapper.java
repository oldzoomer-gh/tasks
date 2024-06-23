package com.egor.tasks.mapper;

import com.egor.tasks.dto.input.comments.CreateCommentDto;
import com.egor.tasks.dto.input.comments.EditCommentDto;
import com.egor.tasks.dto.output.comments.CommentOutputDto;
import com.egor.tasks.dto.output.users.UserDto;
import com.egor.tasks.entity.Comments;
import com.egor.tasks.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    Comments map(CreateCommentDto dto);

    Comments map(EditCommentDto dto);

	CommentOutputDto map(Comments comment);

    UserDto map(User user);
}
