package dev.dvalentino.demo.quarkus.binance.websocket.data;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Message {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
