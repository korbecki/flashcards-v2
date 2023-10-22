package com.github.korbeckik.common.dto;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class MessageResponse extends ResponseEntity<MessageResponse.Response> {

    public MessageResponse(String message, HttpStatusCode status) {
        super(new Response(message), status);
    }
    public MessageResponse(HttpStatusCode status) {
        super(status);
    }

    public record Response(String message) {
    }
}