package undertaken.lab1.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

public class RequestCounterService {

    private RequestCounterService() {}
    private final static AtomicInteger REQUEST_COUNT = new AtomicInteger(0);

    public static synchronized void incrementRequestCount() {
        REQUEST_COUNT.incrementAndGet();
    }

    public static synchronized void getRequestCount() {
        System.out.println(REQUEST_COUNT.get());
    }

}