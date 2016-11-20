package com.we2seek.jacksondemo.items;

import java.util.Map;

/**
 *
 * @author Vitaliy Timchenko
 */
public class Item {
    public String id;
    public String name;
    public Map<String, Property> properties;

    public Item(String id, String name, Map<String, Property> properties) {
        this.id = id;
        this.name = name;
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", name=" + name + ", properties=" + properties + '}';
    }
}
