package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class JCFUserStatusRepository implements UserStatusRepository {

    private final Map<UUID, UserStatus> userStatuses
            = new ConcurrentHashMap<>();

    @Override
    public void save(UserStatus userStatus) {
        userStatuses.put(
                userStatus.getUserId(),
                userStatus
        );
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        return Optional.ofNullable(
                userStatuses.get(userId)
        );
    }

    @Override
    public void deleteByUserId(UUID userId) {
        userStatuses.remove(userId);
    }
}
