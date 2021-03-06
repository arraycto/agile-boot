package com.huijiewei.agile.core.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SelectSearchField extends BaseSearchField<SelectSearchField> {
    private Boolean multiple = false;
    private List<?> options;

    public SelectSearchField() {
        this.setType("select");
    }

    public SelectSearchField multiple(Boolean multiple) {
        this.multiple = multiple;

        return this;
    }

    public SelectSearchField options(List<?> options) {
        this.options = options;

        return this;
    }

    @Override
    protected SelectSearchField self() {
        return this;
    }
}
