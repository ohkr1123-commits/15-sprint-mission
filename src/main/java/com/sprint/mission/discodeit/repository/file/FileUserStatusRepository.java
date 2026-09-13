package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FileUserStatusRepository implements UserStatusRepository {

    private static final String FILE_PATH = "userStatuses.ser";


    // UserStatus 저장
    @Override
    public void save(UserStatus userStatus) {

        Map<UUID, UserStatus> userStatuses = load();

        userStatuses.put(
                userStatus.getUserId(),
                userStatus
        );

        saveAll(userStatuses);
    }


    // userId로 UserStatus 조회
    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {

        Map<UUID, UserStatus> userStatuses = load();

        return Optional.ofNullable(
                userStatuses.get(userId)
        );
    }


    // userId로 UserStatus 삭제
    @Override
    public void deleteByUserId(UUID userId) {

        Map<UUID, UserStatus> userStatuses = load();

        userStatuses.remove(userId);

        saveAll(userStatuses);
    }


    // 전체 데이터를 파일에 저장
    private void saveAll(
            Map<UUID, UserStatus> userStatuses
    ) {

        try (
                ObjectOutputStream outputStream =
                        new ObjectOutputStream(
                                new FileOutputStream(FILE_PATH)
                        )
        ) {

            outputStream.writeObject(userStatuses);

        } catch (IOException e) {

            throw new RuntimeException(
                    "UserStatus 저장 중 오류가 발생했습니다.",
                    e
            );
        }
    }


    // 파일에서 전체 데이터 읽기
    @SuppressWarnings("unchecked")
    private Map<UUID, UserStatus> load() {

        File file = new File(FILE_PATH);

        // 파일이 아직 없으면 빈 Map 생성
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (
                ObjectInputStream inputStream =
                        new ObjectInputStream(
                                new FileInputStream(file)
                        )
        ) {

            return (Map<UUID, UserStatus>)
                    inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {

            throw new RuntimeException(
                    "UserStatus 조회 중 오류가 발생했습니다.",
                    e
            );
        }
    }
}