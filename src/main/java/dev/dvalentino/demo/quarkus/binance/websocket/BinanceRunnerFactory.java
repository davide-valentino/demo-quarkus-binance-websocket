package dev.dvalentino.demo.quarkus.binance.websocket;

import dev.dvalentino.demo.quarkus.binance.websocket.client.BinanceWebSocketClient;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Session;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ApplicationScoped
public class BinanceRunnerFactory {
    final String binanceUrl;

    public BinanceRunnerFactory(
            @ConfigProperty(name = "binance.url") final String binanceUrl) {
        this.binanceUrl = binanceUrl;
    }

    public Runnable create(final String symbol) {

        try {
            final URI uri = new URI(binanceUrl.replace("{symbol}", symbol));
            return () -> {
                try (Session session = ContainerProvider.getWebSocketContainer().connectToServer(
                        BinanceWebSocketClient.class,
                        uri)) {
                    Log.info("Connection with websocket session id: " + session.getId() + " established");
                    Thread.currentThread().join();
                } catch (DeploymentException | IOException | InterruptedException e) {
                    Log.error("Something went wrong on BinanceRunner or thread interrupted");
                    throw new RuntimeException(e);
                }
            };

        } catch (URISyntaxException e) {
            Log.error("Invalid binance url", e);
            throw new RuntimeException(e);
        }
    }
}
