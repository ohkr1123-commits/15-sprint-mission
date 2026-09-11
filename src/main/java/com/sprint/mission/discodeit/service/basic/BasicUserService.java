package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserDto.UserFindRequest;
import com.sprint.mission.discodeit.dto.UserDto.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.dto.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.UserDto.UserCreateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

            if (user.getName().equals(userRequest.name())) {
                throw new IllegalArgumentException("이미 사용 중인 이름입니다.");
            }

            if (user.getEmail().equals(userRequest.email())) {
                throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
            }
        }

        // ③ 중복이 없으면 기존처럼 User 생성
        User user = new User(
                userRequest.name(),
                userRequest.email(),
                userRequest.password()
        );

        // ④ 선택적으로 프로필 이미지 등록
        if (profileRequest != null) {

            BinaryContent profile = new BinaryContent(
                    profileRequest.contentType(),
                    profileRequest.fileName(),
                    profileRequest.fileSize(),
                    profileRequest.content()
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
    public UserFindRequest read(UUID id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        UserStatus status =
                userStatusRepository.findByUserId(id).orElse(null);

        boolean online = status != null && status.isOnline();

        return new UserFindRequest(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getName(),
                user.getEmail(),
                user.getProfileId(),
                online
        );
    }

    @Override
    public List<UserFindRequest> readAll() {

        List<User> users = userRepository.findAll();
        List<UserFindRequest> result = new ArrayList<>();

        for (User user : users) {

            UserStatus status =
                    userStatusRepository.findByUserId(user.getId()).orElse(null);

            boolean online = status != null && status.isOnline();

            result.add(new UserFindRequest(
                    user.getId(),
                    user.getCreatedAt(),
                    user.getUpdatedAt(),
                    user.getName(),
                    user.getEmail(),
                    user.getProfileId(),
                    online
            ));
        }

        return result;
    }

    @Override
    public User update(    UUID id,
                           UserUpdateRequest userRequest,
                           BinaryContentCreateRequest profileRequest) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());

        if (profileRequest != null) {

            // 기존 프로필이 있으면 삭제
            if (user.getProfileId() != null) {
                binaryContentRepository.deleteById(user.getProfileId());
            }

            // 새로운 프로필 생성
            BinaryContent newProfile = new BinaryContent(
                    profileRequest.contentType(),
                    profileRequest.fileName(),
                    profileRequest.fileSize(),
                    profileRequest.content()
            );

            // 새로운 프로필 저장
            binaryContentRepository.save(newProfile);

            // User가 새로운 프로필을 가리키도록 변경
            user.setProfileId(newProfile.getId());
        }

        user.setUpdatedAt();
        userRepository.save(user);

        return user;
    }

    @Override
    public void delete(UUID id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return;
        }

        if (user.getProfileId() != null) {
            binaryContentRepository.deleteById(user.getProfileId());
        }
        userStatusRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }
}