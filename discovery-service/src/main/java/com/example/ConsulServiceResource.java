package com.example;

import io.vertx.ext.consul.ConsulClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/consul")
public class ConsulServiceResource {

    @Inject
    ConsulServiceStarter consulServiceStarter;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        ConsulClient consulClient = consulServiceStarter.getConsulClient();
        return consulClient != null ? "Consul service is running" : "Consul service is not running";
    }
}
