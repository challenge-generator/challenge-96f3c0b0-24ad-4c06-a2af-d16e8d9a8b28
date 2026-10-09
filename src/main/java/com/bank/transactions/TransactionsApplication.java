package com.bank.transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableWebFlux
public class TransactionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionsApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> healthCheck() {
        return route()
                .GET("/health", request -> ok().bodyValue(new HealthCheckResponse("UP"))
                        .then(Mono.empty()))
                .build();
    }

    public record HealthCheckResponse(String status) {}
}