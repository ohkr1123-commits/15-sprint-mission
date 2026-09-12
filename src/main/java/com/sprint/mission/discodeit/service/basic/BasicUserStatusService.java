package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService {

    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;
}
