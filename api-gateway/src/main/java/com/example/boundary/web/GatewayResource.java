//package com.example.boundary.web;
//
//import com.example.application.CustomersService;
//import com.example.application.ServiceInstance;
//import io.quarkus.vertx.web.Route;
//import io.quarkus.vertx.web.RoutingExchange;
//import io.vertx.core.http.HttpMethod;
//import org.eclipse.microprofile.rest.client.inject.RestClient;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//
//import java.util.List;
//
//
//@ApplicationScoped
//public class GatewayResource {
//
//    @Inject
//    @RestClient
//    CustomersService customersServiceClient;
//
//    @Route(path = "/service1/*", methods = Route.HttpMethod.GET)
//    void routeToService1(RoutingExchange ex) {
//        List<ServiceInstance> instances = customersServiceClient.getServiceInstances("customers-service");
//        if (instances != null && !instances.isEmpty()) {
//            ServiceInstance serviceInstance = instances.get(0); // Pour simplifier, on prend la première instance
//            ex.vertx().createHttpClient().request(HttpMethod.GET, serviceInstance.getPort(), serviceInstance.getAddress(), ex.request().path())
//                    .compose(req -> req.send().compose(resp -> {
//                        ex.response().setStatusCode(resp.statusCode());
//                        return resp.body();
//                    }))
//                    .onSuccess(buffer -> ex.response().end(buffer))
//                    .onFailure(throwable -> ex.response().setStatusCode(500).end(throwable.getMessage()));
//        } else {
//            ex.response().setStatusCode(503).end("Service not available");
//        }
//    }
//
//    @Route(path = "/service2/*", methods = HttpMethod.GET)
//    void routeToService2(RoutingExchange ex) {
//        List<ServiceInstance> instances = customersServiceClient.getServiceInstances("vets-service");
//        if (instances != null && !instances.isEmpty()) {
//            ServiceInstance serviceInstance = instances.get(0); // Pour simplifier, on prend la première instance
//            ex.vertx().createHttpClient().request(HttpMethod.GET, serviceInstance.getPort(), serviceInstance.getAddress(), ex.request().path())
//                    .compose(req -> req.send().compose(resp -> {
//                        ex.response().setStatusCode(resp.statusCode());
//                        return resp.body();
//                    }))
//                    .onSuccess(buffer -> ex.response().end(buffer))
//                    .onFailure(throwable -> ex.response().setStatusCode(500).end(throwable.getMessage()));
//        } else {
//            ex.response().setStatusCode(503).end("Service not available");
//        }
//    }
//}
