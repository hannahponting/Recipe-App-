package com.recipe.utilities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Cuisine {
    French("French"), Chinese("Chinese"), Japanese("Japanese"), Italian("Italian"),
    Greek("Greek"), Spanish("Spanish"),
    Indian("Indian"), American("American"), International("International"),
    Mediterranean("Mediterranean"), Mexican("Mexican"), Asian("Asian");

    public String getCuisineName() {
        return cuisineName;
    }
    @JsonValue
    private String cuisineName;
    Cuisine(String name) {
        this.cuisineName = name;
    }
}
