package com.hunko.algostack.algorithm.domain.event.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunko.algostack.algorithm.domain.event.entity.AlgorithmEvent;
import com.hunko.algostack.algorithm.domain.event.exception.JsonDeSerializeException;
import com.hunko.algostack.algorithm.domain.event.exception.JsonSerializeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final ObjectMapper objectMapper;

    public Object toEvent(AlgorithmEvent algorithmEvent) throws JsonDeSerializeException {
        try {
            Class<?> aClass = Class.forName(algorithmEvent.getEventName());
            return objectMapper.readValue(algorithmEvent.getData().toString(), aClass);
        } catch (ClassNotFoundException | JsonProcessingException e) {
            throw new JsonDeSerializeException(String.format("eventName : %s data : %s 역직렬화가 불가능합니다", algorithmEvent.getEventName(), algorithmEvent.getData()), e);
        }
    }

    public AlgorithmEvent toEntity(Object event) throws JsonSerializeException {

        try {
            String eventName = event.getClass().getName();
            String eventString = objectMapper.writeValueAsString(event);
            return new AlgorithmEvent(eventName, eventString);
        } catch (JsonProcessingException e) {
            throw new JsonSerializeException(String.format("eventName : %s data : %s 역직렬화가 불가능합니다", event.getClass().getSimpleName(), event), e);
        }
    }
}
