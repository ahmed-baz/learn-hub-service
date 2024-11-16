package com.learn.hub.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Objects;


@UtilityClass
public class LearHubSpecs {

    public <E> Specification<E> propertyEqual(String property, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null || value.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get(property), value);
            }
        };
    }

    public <E> Specification<E> propertyLike(String property, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null || value.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(property)), "%" + value.toLowerCase() + "%");
            }
        };
    }

    public <T> Specification<T> propertySpec(String keyword, String... propertyNames) {
        return (root, query, builder) -> {
            if (Objects.isNull(keyword) || keyword.isEmpty() || keyword.equalsIgnoreCase("all")) {
                return builder.conjunction();
            }
            String searchKeyword = "%" + keyword.toLowerCase() + "%";
            Predicate[] predicates = Arrays.stream(propertyNames)
                    .map(propertyName -> builder.like(builder.lower(root.get(propertyName)), searchKeyword))
                    .toArray(Predicate[]::new);
            return builder.or(predicates);
        };
    }

    public <T> Specification<T> distinct() {
        return (root, query, cb) -> {
            assert query != null;
            query.distinct(true);
            return null;
        };
    }

}
