package org.alexreverse.controller.payload;

import java.util.UUID;

public record NewPostPayload(String title, String description, UUID userId) {}
