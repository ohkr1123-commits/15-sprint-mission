package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.UserCreateRequest;
import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserService {

    User create(
            UserCreateRequest userRequest,
            BinaryContentCreateRequest profileRequest
    );

    User read(UUID id);

    List<User> readAll();

    User update(UUID id, String name, String email, String password);

    void delete(UUID id);

}