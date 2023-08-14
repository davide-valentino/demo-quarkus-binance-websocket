package dev.dvalentino.demo.quarkus.binance.websocket.client;

import dev.dvalentino.demo.quarkus.binance.websocket.data.Message;
import io.quarkus.logging.Log;
import jakarta.websocket.*;

@ClientEndpoint
public class BinanceWebSocketClient {
    private final Message message;

    public BinanceWebSocketClient(final Message message) {
        this.message = message;
    }

    @OnOpen
    public void open(Session session) {
        Log.info("Connected open: " + session.getId() + " " + session.getRequestURI());
    }

    @OnMessage
    void onMessage(String msg) {
        Log.info("Received message: " + msg);
        message.setMessage(msg);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        Log.info("Error: " + session.getId() + " " + throwable.getMessage());
    }

    @OnClose
    public void onClose(Session session) {
        Log.info("Session " + session.getId() + " has ended");
    }

}
