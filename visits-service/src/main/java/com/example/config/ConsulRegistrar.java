package com.example.config;



import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.ShutdownEvent;
import io.vertx.core.Vertx;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class ConsulRegistrar {

    @ConfigProperty(name = "quarkus.application.name")
    String serviceName;

    @ConfigProperty(name = "quarkus.http.port")
    int servicePort;

    private ConsulClient consulClient;

    void onStart(@Observes StartupEvent ev) {
        Vertx vertx = Vertx.vertx();
        ConsulClientOptions options = new ConsulClientOptions()
                .setHost("localhost")
                .setPort(8500);
        consulClient = ConsulClient.create(vertx, options);

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
    }

    void onStop(@Observes ShutdownEvent ev) {
        if (consulClient != null) {
            consulClient.deregisterService(serviceName + "-" + servicePort, ar -> {
                if (ar.succeeded()) {
                    System.out.println("Service deregistered: " + serviceName);
                } else {
                    System.err.println("Failed to deregister service: " + ar.cause().getMessage());
                }
            });
        }
    }
}
