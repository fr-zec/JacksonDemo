package com.we2seek.jacksondemo.work;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    private Map<String, Event> events;

    public Response() {
    }

    public Response(Map<String, Event> events) {
        this.events = events;
    }

    public Map<String, Event> getEvents() {
        return events;
    }

    public void setEvents(Map<String, Event> events) {
        this.events = events;
    }

    public Long getCategoryId(final String event) {
        if (null != event && null != events) {
            Event e = events.getOrDefault(event, new Event());
            Category category = e.getCategory();
            
            if (null != category) {
                return category.getId();
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("events", events)
                .build();
    }

}
