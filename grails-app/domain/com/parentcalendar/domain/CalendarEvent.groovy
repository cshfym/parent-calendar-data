package com.parentcalendar.domain

import com.parentcalendar.domain.auth.User

class CalendarEvent {

    Long id
    Long version
    Calendar calendar
    User user
    Date eventDate
    String description
    Date fromTime
    Date toTime
    boolean allDay
    Date createDate
    Date updateDate
    boolean active

    static belongsTo = [ calendar: Calendar ]

    static constraints = {
        updateDate nullable: true
        description size: 1..1000
    }

    @Override
    public String toString() {
        "CalendarEvent [ id: $id, active: $active, createDate: $createDate, updateDate: $updateDate ]"
    }
}

