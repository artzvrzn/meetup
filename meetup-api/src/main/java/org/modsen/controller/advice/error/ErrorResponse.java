package org.modsen.controller.advice.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorResponse {
    private final String logref;
    private final String message;

    public ErrorResponse(String message) {
        this.logref = "error";
        this.message = message;
    }
}
