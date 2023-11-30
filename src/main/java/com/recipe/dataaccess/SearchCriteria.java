package com.recipe.dataaccess;

public class SearchCriteria {
    public SearchCriteria(String key, String operation, Object value){
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
    public String getKey() {
        return key;
    }

    public String getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }

    private String key;
    private String operation;
    private Object value;
}
