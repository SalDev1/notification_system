package com.example.notification_delivery_sys.rate_limit_filter;

// Filter --> This filter gets added for
// every endpoint that's called within this Spring
// Boot Application, right before the actual endpoint call is
// invoked.

import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

// This is IP based rate limiting to handle abusing of multiple API calls / handling out sudden traffic spike.
@Component
@Order(1)
public class RateLimitingFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    Supplier<BucketConfiguration> bucketConfiguration;

    @Autowired
    ProxyManager<String> proxyManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String key = httpRequest.getRemoteAddr(); // This is based on IP-based rate limiting
        Bucket bucket = proxyManager.builder().build(key,bucketConfiguration);

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        log.debug(">>>>>>>>>>> remainingTokens: {}", probe.getRemainingTokens());
        if(probe.isConsumed()) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("text/plain");
            httpResponse.setHeader("X-Rate-Limit-Retry-After-Seconds", "" + TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill()));
            httpResponse.setStatus(429);
            httpResponse.getWriter().append("Too many requests");
        }
    }
}
