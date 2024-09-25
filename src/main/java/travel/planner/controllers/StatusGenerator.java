package travel.planner.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import travel.planner.domain.Result;

public class StatusGenerator {
    private Result<?> result;
    private HttpStatus statusDefault;

    public StatusGenerator() {}

    public StatusGenerator(Result<?> result, HttpStatus statusDefault) {
        this.result = result;
        this.statusDefault = statusDefault;
    }

    @Bean
    public HttpStatus getStatus() {
        return switch (result.getStatus()) {
            case INVALID -> HttpStatus.PRECONDITION_FAILED;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            default -> statusDefault;
        };
    }

    @Bean
    public void setResult(Result<?> result) {
        this.result = result;
    }

    @Bean
    public void setStatusDefault(HttpStatus statusDefault) {
        this.statusDefault = statusDefault;
    }
}
