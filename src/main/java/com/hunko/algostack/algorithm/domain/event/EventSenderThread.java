package com.hunko.algostack.algorithm.domain.event;

import com.hunko.algostack.algorithm.domain.event.exception.JsonDeSerializeException;
import com.hunko.algostack.algorithm.domain.event.queue.AlgorithmEventQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
public class EventSenderThread extends Thread {

    private final AlgorithmEventQueue algorithmEventQueue;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                Object event = algorithmEventQueue.poll();
                if(event == null){
                    try {
                        TimeUnit.SECONDS.sleep(30);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    continue;
                }
                applicationEventPublisher.publishEvent(event);
            }catch (JsonDeSerializeException e){
                log.error(e.getMessage(),e);
            }
        }
        log.debug("EventSenderThread has been interrupted");
    }
}
