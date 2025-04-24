package org.dreemz.t1academy.controller;

import lombok.RequiredArgsConstructor;
import org.dreemz.t1academy.TaskFilter;
import org.dreemz.t1academy.dto.TaskDto;
import org.dreemz.t1academy.entity.Task;
import org.dreemz.t1academy.mapper.TaskMapper;
import org.dreemz.t1academy.repo.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;


    @PostMapping
    public TaskDto create(@RequestBody TaskDto dto) {
        if (dto.id() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be null");
        }

        Task task = taskMapper.toEntity(dto);
        Task resultTask = taskRepository.save(task);
        return taskMapper.toTaskDto(resultTask);
    }

    @GetMapping("/{id}")
    public TaskDto getOne(@PathVariable Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        return taskMapper.toTaskDto(taskOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id `%s` not found".formatted(id))));
    }

    @DeleteMapping("/{id}")
    public TaskDto delete(@PathVariable Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            taskRepository.delete(task);
        }
        return taskMapper.toTaskDto(task);
    }

    @PutMapping("/{id}")
    public TaskDto update(@PathVariable Long id, @RequestBody TaskDto dto) {
        if (!dto.id().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be equal");
        }

        Task task = taskRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        taskMapper.updateWithNull(dto, task);
        Task resultTask = taskRepository.save(task);
        return taskMapper.toTaskDto(resultTask);
    }

    @GetMapping
    public PagedModel<TaskDto> getAll(@ModelAttribute TaskFilter filter, Pageable pageable) {
        Specification<Task> spec = filter.toSpecification();
        Page<Task> tasks = taskRepository.findAll(spec, pageable);
        Page<TaskDto> taskDtoPage = tasks.map(taskMapper::toTaskDto);
        return new PagedModel<>(taskDtoPage);
    }
}

