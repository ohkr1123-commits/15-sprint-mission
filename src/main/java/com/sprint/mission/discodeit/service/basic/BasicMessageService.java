package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.List;
import java.util.UUID;

public class BasicMessageService implements MessageService {

    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    public BasicMessageService(MessageRepository messageRepository, ChannelRepository channelRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Message create(UUID channelId, UUID userId, String content) {

        // channelId 존재하는지 검사
        if (!channelRepository.existsById(channelId)) {
            return null;
        } // 없으면 생성하지 않음

        // userId 존재하는지 검사
        if (!userRepository.existsById(userId)) {
            return null;
        } // 없으면 생성하지 않음

        Message message = new Message(channelId, userId, content);

        messageRepository.save(message);

        return message;
    }

    @Override
    public Message read(UUID id) {

        return messageRepository.findById(id).orElse(null);
    }

    @Override
    public List<Message> readAll() {

        return messageRepository.findAll();
    }

    @Override
    public Message update(UUID id, String content) {

        Message message = messageRepository.findById(id).orElse(null);

        if (message == null) {
            return null;
        }

        message.setContent(content);

        messageRepository.save(message);

        return message;
    }

    @Override
    public void delete(UUID id) {

        messageRepository.deleteById(id);
    }
}