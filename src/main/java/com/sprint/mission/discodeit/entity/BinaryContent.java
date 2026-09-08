package com.sprint.mission.discodeit.entity;


import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class BinaryContent implements Serializable {

    private UUID id;
    private Instant createdAt;
    private String contentType;
    private String fileName;
    private long fileSize;
    private byte[] content;


    public BinaryContent(String contentType, String fileName, long fileSize, byte[] content) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.contentType = contentType;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.content = content;
    }
}
