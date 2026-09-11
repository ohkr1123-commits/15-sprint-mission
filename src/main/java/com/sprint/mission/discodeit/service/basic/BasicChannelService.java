package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ChannelDto.ChannelFindRequest;
import com.sprint.mission.discodeit.dto.ChannelDto.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.ChannelDto.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.ChannelDto.PublicChannelCreatRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;

    @Override
    public Channel createPublic(PublicChannelCreatRequest request) {

        Channel channel = new Channel(
                request.ownerId(),
                request.channelName(),
                request.channelDescription(),
                ChannelType.PUBLIC
        );

        channelRepository.save(channel);

        return channel;
    }

    @Override
    public Channel createPrivate(PrivateChannelCreateRequest request) {

        // PRIVATE 채널 생성
        Channel channel = new Channel(
                request.ownerId(),
                null,
                null,
                ChannelType.PRIVATE
        );

        channelRepository.save(channel);

        // 참여 User마다 ReadStatus 생성
        for (UUID userId : request.userIds()) {

            ReadStatus readStatus = new ReadStatus(
                    channel.getId(),
                    userId
            );

            readStatusRepository.save(readStatus);
        }

        return channel;
    }

    @Override
    public List<ChannelFindRequest> findAllByUserId(UUID userId) {
        // PUBLIC 전체
        // PRIVATE는 해당 userId가 참여한 채널만
        return null;
    }

    @Override
    public Channel update(UUID id, ChannelUpdateRequest request) {
        // PRIVATE면 수정 불가
        // PUBLIC이면 수정
        return null;
    }

    @Override
    public void delete(UUID id) {
        // Message 삭제
        // ReadStatus 삭제
        // Channel 삭제
    }
}