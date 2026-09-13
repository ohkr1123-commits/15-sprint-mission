package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileUserStatusRepository implements UserStatusRepository {

    private static final String FILE_PATH = "userStatuses.ser";

    @Override
    public void save(UserStatus userStatus) {

        Map<UUID, UserStatus> userStatuses = load();

        userStatuses.put(
                userStatus.getUserId(),
                userStatus
        );

        saveAll(userStatuses);
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {

        return load().values()
                .stream()
                .filter(userStatus ->
                        userStatus.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {

        return Optional.ofNullable(
                load().get(userId)
        );
    }

    @Override
    public List<UserStatus> findAll() {

        return new ArrayList<>(
                load().values()
        );
    }

    @Override
    public void deleteById(UUID id) {

        Map<UUID, UserStatus> userStatuses = load();

        userStatuses.entrySet()
                .removeIf(entry ->
                        entry.getValue().getId().equals(id));

        saveAll(userStatuses);
    }

    @Override
    public void deleteByUserId(UUID userId) {

        Map<UUID, UserStatus> userStatuses = load();

        userStatuses.remove(userId);

        saveAll(userStatuses);
    }

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

    @SuppressWarnings("unchecked")
    private Map<UUID, UserStatus> load() {

        File file = new File(FILE_PATH);

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