package com.sprint.mission.discodeit.dto.User;

import java.util.UUID;

public record UserUpdateRequest(

        String name,
        String email,
        String password
) {
}
