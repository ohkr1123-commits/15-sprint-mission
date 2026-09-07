package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
public class Channel implements Serializable {

    private UUID id;
    private Long createdAt;
    private Long updatedAt;
    private String channelName;


    public Channel(String channelName) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
        this.channelName = channelName;
    }

    public void update(String channelName) {
        this.channelName = channelName;
        this.updatedAt = System.currentTimeMillis();
    }
}