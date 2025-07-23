package com.cacique.caciqueci.business.models.wrapper;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ListDataWrapper<T> {
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    private List<T> data;
}
