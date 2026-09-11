package com.sprint.mission.discodeit.dto.User;

import java.util.UUID;

public record UserUpdateRequest(
        UUID id,
        String name,
        String email,
        String password
) {
}
