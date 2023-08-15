package dev.dvalentino.demo.quarkus.binance.websocket.client;

import io.quarkus.logging.Log;
import jakarta.websocket.*;

@ClientEndpoint
public class BinanceWebSocketClient {
    @OnOpen
    public void open(Session session) {
        Log.info("Connected open: " + session.getId() + " " + session.getRequestURI());
    }

    @OnMessage
    void onMessage(String tickerData) {
        Log.info("Incoming ticker data: " + tickerData);
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
