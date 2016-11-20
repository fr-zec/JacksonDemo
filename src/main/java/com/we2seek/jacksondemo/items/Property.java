package com.we2seek.jacksondemo.items;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Property {
@JsonProperty("property_id")
    public String pId;
    @JsonProperty("property_name")
    public String pName;

    public Property(String pId, String pName) {
        this.pId = pId;
        this.pName = pName;
    }

    @Override
    public String toString() {
        return "Property{" + "pId=" + pId + ", pName=" + pName + '}';
    }
}
