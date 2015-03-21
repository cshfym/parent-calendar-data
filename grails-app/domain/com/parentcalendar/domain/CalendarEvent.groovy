package com.parentcalendar.domain

class CalendarEvent {

    Long id
    boolean active
    Date createDate
    Date updateDate

    static belongsTo = [ calendar: Calendar ]


    @Override
    public String toString() {
        "CalendarEvent [ id: $id, active: $active, createDate: $createDate, updateDate: $updateDate ]"
    }
}

