package com.github.korbeckik.auth.dto.response;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class MessageResponse extends ResponseEntity<MessageResponse.Response> {

    public MessageResponse(String message, HttpStatusCode status) {
        super(new Response(message), status);
    }

    public record Response(String message) {
    }
}