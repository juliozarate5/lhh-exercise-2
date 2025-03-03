package com.lhh.ms.token.application.filter;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class LogginFilter implements Filter {

    private final MeterRegistry meterRegistry;

    public LogginFilter(final MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String path = httpRequest.getRequestURI();

        // Registro de la petición
        log.info("Processing request: {} {}", httpRequest.getMethod(), path);

        // Incrementa el contador de métricas para la ruta solicitada
        meterRegistry.counter("requests.processed", "path", path).increment();

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
