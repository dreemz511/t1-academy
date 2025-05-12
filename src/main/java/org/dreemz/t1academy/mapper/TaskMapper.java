package org.dreemz.t1academy.mapper;

import org.dreemz.t1academy.dto.KafkaTaskDto;
import org.dreemz.t1academy.dto.TaskDto;
import org.dreemz.t1academy.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    Task toEntity(TaskDto taskDto);

    TaskDto toTaskDto(Task task);

    KafkaTaskDto toKafkaTaskDto(Task task);
}