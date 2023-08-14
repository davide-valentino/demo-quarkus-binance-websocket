package dev.dvalentino.demo.quarkus.binance.websocket;

import io.quarkus.logging.Log;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@ApplicationScoped
public class DemoQuarkusBinanceWebsocketApp {
    @Inject
    BinanceRunnerFactory binanceRunnerFactory;

    private ExecutorService executorService;
    private Future<?> poolingFuture;

    void onStart(@Observes StartupEvent ev) {
        executorService = Executors.newCachedThreadPool();

        try {
            poolingFuture = executorService.submit(binanceRunnerFactory.create("btcusdt"));
        } catch (Exception e) {
            e.printStackTrace();
            executorService.shutdown();
            System.exit(-1);
        }
    }

    void onStop(@Observes ShutdownEvent ev) {
        Log.info("The application is stopping...");
        try {
            poolingFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

}
