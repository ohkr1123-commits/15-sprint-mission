package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.dto.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.UserCreateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public User create(
            UserCreateRequest userRequest,
            BinaryContentCreateRequest profileRequest
    ) {

        // ① 기존 사용자 가져오기
        List<User> users = userRepository.findAll();

        // ② 이름과 이메일 중복 확인
        for (User user : users) {

            if (user.getName().equals(userRequest.getName())) {
                throw new IllegalArgumentException("이미 사용 중인 이름입니다.");
            }

            if (user.getEmail().equals(userRequest.getEmail())) {
                throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
            }
        }

        // ③ 중복이 없으면 기존처럼 User 생성
        User user = new User(
                userRequest.getName(),
                userRequest.getEmail(),
                userRequest.getPassword()
        );

        // ④ 선택적으로 프로필 이미지 등록
        if (profileRequest != null) {

            BinaryContent profile = new BinaryContent(
                    profileRequest.getContentType(),
                    profileRequest.getFileName(),
                    profileRequest.getFileSize(),
                    profileRequest.getContent()
            );
            binaryContentRepository.save(profile);
            user.setProfileId(profile.getId());
        }

        userRepository.save(user);
        UserStatus userStatus = new UserStatus(user.getId());
        userStatusRepository.save(userStatus);

        return user;
    }

    @Override
    public User read(UUID id) {

        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> readAll() {

        return userRepository.findAll();
    }

    @Override
    public User update(UUID id, String name, String email, String password) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setUpdatedAt();

        userRepository.save(user);

        return user;
    }

    @Override
    public void delete(UUID id) {

        userRepository.deleteById(id);
    }
}