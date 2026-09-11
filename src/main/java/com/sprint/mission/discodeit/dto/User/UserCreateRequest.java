package com.sprint.mission.discodeit.dto.User;

public record UserCreateRequest (

        //유저 생성될때 DTO
        String name,
        String email,
        String password
) {}