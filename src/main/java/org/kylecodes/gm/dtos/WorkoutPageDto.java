package org.kylecodes.gm.dtos;

import java.util.List;

public class WorkoutPageDto {
    private List<WorkoutDto> content;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean isLastPage;

    public WorkoutPageDto(List<WorkoutDto> workoutDtos, Integer pageNo, Integer pageSize, Long totalElements, Integer totalPages, Boolean isLastPage) {
        this.content = workoutDtos;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.isLastPage = isLastPage;
    }

    public WorkoutPageDto() {
    }

    public List<WorkoutDto> getContent() {
        return content;
    }

    public void setContent(List<WorkoutDto> content) {
        this.content = content;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Boolean getLastPage() {
        return isLastPage;
    }

    public void setLastPage(Boolean lastPage) {
        isLastPage = lastPage;
    }
}
