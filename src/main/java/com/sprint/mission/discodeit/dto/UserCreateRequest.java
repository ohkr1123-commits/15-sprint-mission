package com.sprint.mission.discodeit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateRequest {

    private String name;
    private String email;
    private String password;
}