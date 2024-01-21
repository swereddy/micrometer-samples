package com.example.micrometer;

import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BaggageReactiveConsumerApplication {

    public static void main(String... args) {
        new SpringApplication(BaggageReactiveConsumerApplication.class).run(args);
    }

}

@RestController
class BaggageController {

    private static final Logger log = LoggerFactory.getLogger(BaggageController.class);

    private final Tracer tracer;


    BaggageController(Tracer tracer) {
        this.tracer = tracer;
    }

    @GetMapping("/")
    public String span() {
        log.info("Contains the following baggage {}", this.tracer.getAllBaggage());
        String traceId = this.tracer.currentSpan().context().traceId();
        log.info("<ACCEPTANCE_TEST> <TRACE:{}> Hello from producer", traceId);
        return traceId;
    }

    @PostMapping("/consumer")
    public String spanPost(@RequestBody String consumeRequest) {
        log.info("Contains the following baggage {}", this.tracer.getAllBaggage());
        String traceId = this.tracer.currentSpan().context().traceId();
        log.info("<ACCEPTANCE_TEST> <TRACE:{}> Hello from producer", traceId);
        return traceId;
    }

}

