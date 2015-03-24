package com.parentcalendar.domain

import com.parentcalendar.domain.auth.User

class Calendar {

    Long id
    boolean active
    Date createDate
    Date updateDate
    String description
    String color
    Boolean _default

    User user

    static hasMany = [ events: CalendarEvent ]

    static constraints = {
        description nullable: true
        updateDate nullable: true
    }

    static mapping = {
        events lazy: false
    }

    @Override
    public String toString() {
        "Calendar [ id: $id, active: $active, createDate: $createDate, " +
                "updateDate: $updateDate, description: $description, calendarEvents: $events?.size(), default: $_default ]"
    }
}

