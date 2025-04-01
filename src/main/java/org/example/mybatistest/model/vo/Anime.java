package org.example.mybatistest.model.vo;

import java.util.UUID;

public record Anime(String uuid, String title, String description, String createdAt, Integer votes) {

}
