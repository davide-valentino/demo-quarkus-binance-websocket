package dev.dvalentino.demo.quarkus.binance.websocket;

import io.quarkus.logging.Log;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class DemoQuarkusBinanceWebsocketApp {
    @Inject
    BinanceRunnerFactory binanceRunnerFactory;
    private ExecutorService executorService;

    void onStart(@Observes StartupEvent ev) {
        executorService = Executors.newCachedThreadPool();
        executorService.submit(binanceRunnerFactory.create("btcusdt"));
    }

    void onStop(@Observes ShutdownEvent ev) {
        Log.info("The application is stopping...");
        try {
            executorService.shutdown();
        } finally {
            if (!executorService.isTerminated()) {
                Log.error("Cancel non-finished tasks");
            }
            executorService.shutdownNow();
            Log.info("Shutdown finished");
        }
    }
}
