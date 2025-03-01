package com.hub.doomer.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewPostPayload(
    String title, String description) {}
