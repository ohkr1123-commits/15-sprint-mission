package com.sprint.mission.discodeit.dto.User;

import java.time.Instant;
import java.util.UUID;

public record UserFindRequest(
        UUID id,
        Instant createdAt,
        Instant updatedAt,
        String name,
        String email,
        UUID profileId,
        boolean online
) {}