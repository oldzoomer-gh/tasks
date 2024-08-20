package ru.gavrilovegor519.tasks.dto.output.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String token;
}
