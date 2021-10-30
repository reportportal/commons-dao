package com.epam.ta.reportportal.entity.onboarding;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Antonov Maksim
 */
public class Onboarding implements Serializable {

    private Long id;
    private String page;
    private String data;
    private LocalDateTime availableFrom;
    private LocalDateTime availableTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDateTime getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDateTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    public LocalDateTime getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(LocalDateTime availableTo) {
        this.availableTo = availableTo;
    }
}
