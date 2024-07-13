package com.example;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

@ApplicationScoped
public class ConsulServiceStarter {

    private ConsulClient consulClient;

    @ConfigProperty(name = "quarkus.application.name")
    String serviceName;

    @ConfigProperty(name = "quarkus.http.port")
    int servicePort;

    void onStart(@Observes StartupEvent ev) {
        startConsulAgent();

        Vertx vertx = Vertx.vertx();
        ConsulClientOptions options = new ConsulClientOptions()
                .setHost("localhost")
                .setPort(8500);

        consulClient = ConsulClient.create(vertx, options);

        // Registrieren Sie den Dienst bei Consul
        vertx.setTimer(5000, id -> {
            consulClient.registerService(new ServiceOptions()
                    .setName(serviceName)
                    .setId(serviceName + "-" + servicePort)
                    .setAddress("localhost")
                    .setPort(servicePort), ar -> {
                if (ar.succeeded()) {
                    System.out.println("Service registered: " + serviceName);
                } else {
                    System.err.println("Failed to register service: " + ar.cause().getMessage());
                }
            });
        });
    }

    private void startConsulAgent() {
        ProcessBuilder builder = new ProcessBuilder(
                "consul", "agent", "-dev", "-client=0.0.0.0");
        try {
            builder.inheritIO().start();
            System.out.println("Consul agent started.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConsulClient getConsulClient() {
        return consulClient;
    }
}
