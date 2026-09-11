package com.sprint.mission.discodeit.dto.ChannelDto;

import java.util.UUID;

public record PublicChannelCreatRequest(

        UUID ownerId,
        String channelName,
        String channelDescription

) {
}