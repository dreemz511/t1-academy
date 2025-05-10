package org.dreemz.t1academy.service;

import lombok.AllArgsConstructor;
import org.dreemz.t1academy.util.TaskFilter;
import org.dreemz.t1academy.aspect.annotation.ExceptionHandling;
import org.dreemz.t1academy.aspect.annotation.LogAfterReturning;
import org.dreemz.t1academy.aspect.annotation.LogBefore;
import org.dreemz.t1academy.aspect.annotation.TimeMeasure;
import org.dreemz.t1academy.dto.TaskDto;
import org.dreemz.t1academy.entity.Task;
import org.dreemz.t1academy.mapper.TaskMapper;
import org.dreemz.t1academy.repo.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @LogBefore
    @ExceptionHandling
    public TaskDto create(TaskDto dto) {
        if (dto.id() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be null");
        }

        Task task = taskMapper.toEntity(dto);
        Task resultTask = taskRepository.save(task);
        return taskMapper.toTaskDto(resultTask);
    }

    @LogBefore
    @ExceptionHandling
    public TaskDto getOne(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id `%s` not found".formatted(id)));
        return taskMapper.toTaskDto(task);
    }

    @LogBefore
    public TaskDto delete(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            taskRepository.delete(task);
        }
        return taskMapper.toTaskDto(task);
    }

    @LogBefore
    @ExceptionHandling
    public TaskDto update(Long id, TaskDto dto) {
        if (!dto.id().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be equal");
        }

        Task task = taskRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id `%s` not found".formatted(id)));
        taskMapper.updateWithNull(dto, task);
        Task resultTask = taskRepository.save(task);
        return taskMapper.toTaskDto(resultTask);
    }

    @LogAfterReturning
    @TimeMeasure
    public PagedModel<TaskDto> getAll(TaskFilter filter, Pageable pageable) {
        Specification<Task> spec = filter.toSpecification();
        Page<Task> tasks = taskRepository.findAll(spec, pageable);
        Page<TaskDto> taskDtoPage = tasks.map(taskMapper::toTaskDto);
        return new PagedModel<>(taskDtoPage);
    }
}
