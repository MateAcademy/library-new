package com.example.demo.utils;

import com.example.demo.dto.sort.SortField;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageRequestUtils {

    @NotNull
    public static <T extends SortField> PageRequest buildPageRequestForPg(@NotNull Integer page, @NotNull Integer pageSize, @NotNull List<T> sortFields, @NotNull Sort.Direction sortDirection) {
        final Sort sort = buildSortForPg(sortFields, sortDirection);
        return PageRequest.of(page - 1, pageSize, sort);
    }

    @NotNull
    public static PageRequest buildPageRequestForPg(@NotNull Integer page, @NotNull Integer pageSize, @NotNull SortField sortField, @NotNull Sort.Direction sortDirection) {
        final Sort.Order order = new Sort.Order(sortDirection, sortField.getField(), sortField.isString(), Sort.NullHandling.NATIVE);
        return PageRequest.of(page - 1, pageSize, Sort.by(order));
    }

    @NotNull
    public static PageRequest buildPageRequestForPg(@NotNull Integer page, @NotNull Integer pageSize, @NotNull SortField sortField, @NotNull String sortDirection) {
        final Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        return buildPageRequestForPg(page, pageSize, sortField, direction);
    }

    @NotNull
    public static <T extends SortField> Sort buildSortForPg(@NotNull List<T> sortFields, @NotNull Sort.Direction sortDirection) {
        final List<Sort.Order> orders = sortFields.stream()
                .map(sortField -> new Sort.Order(sortDirection, sortField.getField(), sortField.isString(), Sort.NullHandling.NATIVE))
                .toList();
        return Sort.by(orders);
    }

    @NotNull
    public static PageRequest buildPageRequestForMongo(@NotNull Integer page, @NotNull Integer pageSize, @NotNull String sortField, @NotNull Sort.Direction sortDirection) {
        final Sort.Order order = new Sort.Order(sortDirection, sortField); // mongo does not support '.ignoreCase()'
        return PageRequest.of(page - 1, pageSize, Sort.by(order));
    }

    @NotNull
    public static PageRequest buildPageRequestForMongo(@NotNull Integer page, @NotNull Integer pageSize, @NotNull String sortField, @NotNull String sortDirection) {
        final Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        return buildPageRequestForMongo(page, pageSize, sortField, direction);
    }

}