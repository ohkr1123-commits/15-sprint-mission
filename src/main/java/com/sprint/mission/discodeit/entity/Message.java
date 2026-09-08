package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Message implements Serializable {

    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID channelId;
    private UUID userId;
    private String content;


    public Message(UUID channelId, UUID userId, String content) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
        this.channelId = channelId;
        this.userId = userId;
        this.content = content;

    }

    public void setContent(String content) {
        this.content = content;
        this.updatedAt = Instant.now();
    }
}