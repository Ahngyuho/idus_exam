package dev.agh.idus.member.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Getter
@Builder
public class PageResponse<T> {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;

    private List<T> result;

    // ✅ from() 메서드에서 Entity -> DTO 변환 기능 추가
    public static <T, R> PageResponse<R> from(Page<T> page, Function<T, R> converter) {
        return PageResponse.<R>builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .result(page.map(converter).getContent()) // ✅ DTO 변환 적용
                .build();
    }
}