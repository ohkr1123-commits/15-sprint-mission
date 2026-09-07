package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.util.UUID;
import java.io.Serializable;

@Getter
public class User implements Serializable {

    //필드
    private UUID id;
    private Long createdAt;
    private Long updatedAt;
    private String name;
    private String email;
    private String password;

    //생성자 초기화
    public User(String name, String email, String password) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    //메서드 setter을 적용하지 않아서 그대로 둠
    public void setUpdatedAt() { this.updatedAt = System.currentTimeMillis(); }

    public void setPassword(String password) { this.password = password; }

    public void setEmail(String email) { this.email = email; }

    public void setName(String name) { this.name = name; }
}
