package com.hunko.algostack.shared.event.core;

import com.hunko.algostack.shared.event.core.queue.AlgorithmEventQueue;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheUpdateWorker {

    private final AlgorithmEventQueue algorithmEventQueue;
    private final ApplicationEventPublisher eventPublisher;
    private Thread workerThread;

    @PostConstruct
    public void init(){
        workerThread = new EventSenderThread(algorithmEventQueue, eventPublisher);
        workerThread.start();
    }

    @PreDestroy
    public void destroy(){
        workerThread.interrupt();
        log.debug("worker thread has been interrupted");
    }
}
