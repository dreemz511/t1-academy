package org.dreemz.t1academy.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
public class LogAspect {
    private final static Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Before(value = "@annotation(org.dreemz.t1academy.aspect.annotation.LogBefore)")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Был вызван метод: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(
            value = "@annotation(org.dreemz.t1academy.aspect.annotation.LogAfterReturning)",
            returning = "result"
    )
    public void logAfter(JoinPoint joinPoint, Object result) {

        if (result instanceof PagedModel<?> pagedModel) {
            log.info("Был вызван метод: {}, Размер страницы: {}, Объектов получено: {}," +
                            "Расположены на странице: {}, Всего страниц: {}",
                    joinPoint.getSignature().getName(),
                    pagedModel.getMetadata().size(),
                    pagedModel.getMetadata().totalElements(),
                    pagedModel.getMetadata().number(),
                    pagedModel.getMetadata().totalPages());
        }
    }

    @AfterThrowing(
            pointcut = "@annotation(org.dreemz.t1academy.aspect.annotation.ExceptionHandling)",
            throwing = "exception"
    )
    public void handleException(JoinPoint joinPoint, RuntimeException exception) {
        log.error("Выброшено исключение в методе: {}, класс: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getName());

        log.error("Получено сообещение: {}",exception.getMessage());
    }

    @Around(value = "@annotation(org.dreemz.t1academy.aspect.annotation.TimeMeasure)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        log.info("Время выполнения метода: {} ms", (end - start));
        return result;
    }
}
