package org.alexreverse.controller.payload;

public record NewPostPayload(
    String title, String description, String userId, Long translationId) {}
