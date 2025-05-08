package org.dreemz.t1academy.controller;

import lombok.RequiredArgsConstructor;
import org.dreemz.t1academy.TaskFilter;
import org.dreemz.t1academy.dto.TaskDto;
import org.dreemz.t1academy.service.TaskService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PostMapping
    public TaskDto create(@RequestBody TaskDto dto) {
       return taskService.create(dto);
    }

    @GetMapping("/{id}")
    public TaskDto getOne(@PathVariable Long id) {
        return taskService.getOne(id);
    }

    @DeleteMapping("/{id}")
    public TaskDto delete(@PathVariable Long id) {
       return taskService.delete(id);
    }

    @PutMapping("/{id}")
    public TaskDto update(@PathVariable Long id, @RequestBody TaskDto dto) {
        return taskService.update(id, dto);
    }

    @GetMapping
    public PagedModel<TaskDto> getAll(@ModelAttribute TaskFilter filter, Pageable pageable) {
        return taskService.getAll(filter, pageable);
    }
}

