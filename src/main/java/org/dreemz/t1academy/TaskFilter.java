package org.dreemz.t1academy;

import org.dreemz.t1academy.entity.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public record TaskFilter(Long userId, String titleStarts) {
    public Specification<Task> toSpecification() {
        return Specification.where(userIdSpec())
                .and(titleStartsSpec());
    }

    private Specification<Task> userIdSpec() {
        return ((root, query, cb) -> userId != null
                ? cb.equal(root.get("userId"), userId)
                : null);
    }

    private Specification<Task> titleStartsSpec() {
        return ((root, query, cb) -> StringUtils.hasText(titleStarts)
                ? cb.like(cb.lower(root.get("title")), titleStarts.toLowerCase() + "%")
                : null);
    }
}