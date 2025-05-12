package org.dreemz.t1academy.dto;

/**
 * DTO for {@link org.dreemz.t1academy.entity.Task}
 */
public record TaskDto(Long id, String title, String description, Long userId, String status) {
}