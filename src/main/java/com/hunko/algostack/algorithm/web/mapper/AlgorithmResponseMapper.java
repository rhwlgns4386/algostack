package com.hunko.algostack.algorithm.web.mapper;

import com.hunko.algostack.algorithm.domain.vo.AlgorithmReadModel;
import com.hunko.algostack.algorithm.web.dto.AlgorithmResponse;
import com.hunko.algostack.algorithm.web.dto.AlgorithmsResponse;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class AlgorithmResponseMapper {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Map<String, AlgorithmsResponse> toMonthAlgorithmsResponse(List<AlgorithmReadModel> algorithmHistories) {
        Map<LocalDate, List<AlgorithmReadModel>> groups = algorithmHistories.stream().collect(Collectors.groupingBy(AlgorithmReadModel::solvedDate));
        return groups.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().format(dateTimeFormatter),
                        v -> toAlgorithmsResponse(v.getValue())));
    }

    public static AlgorithmsResponse toAlgorithmsResponse(List<AlgorithmReadModel> algorithmHistories) {
        List<AlgorithmResponse> algorithmResponses = algorithmHistories.stream().map(AlgorithmResponseMapper::toAlgorithmResponseFrom).toList();
        return new AlgorithmsResponse(algorithmResponses);
    }

    public static AlgorithmResponse toAlgorithmResponseFrom(AlgorithmReadModel algorithmHistory) {
        return AlgorithmResponse.builder()
                .id(algorithmHistory.id())
                .title(algorithmHistory.title())
                .url(algorithmHistory.url())
                .result(algorithmHistory.result())
                .platform(algorithmHistory.platform())
                .build();
    }
}
