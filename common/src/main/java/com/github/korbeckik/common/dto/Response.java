package com.github.korbeckik.common.dto;

import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class Response extends ResponseEntity<Response.Body> {
    public Response(Object body,  HttpStatusCode statusCode) {
        super(new Body(body), statusCode);
    }

    public Response(Map<String, String> errors,  HttpStatusCode statusCode) {
        super(new Body(errors), statusCode);
    }

    @Data
    public static class Body{
        private Object body;
        private Map<String, String> errors;

        public Body(Object body, Map<String, String> errors) {
            this.body = body;
            this.errors = errors;
        }

        public Body(Object body) {
            this.body = body;
            this.errors = new HashMap<>();
        }

        public Body(Map<String, String> errors) {
            this.body = null;
            this.errors = errors;
        }
    }
}
