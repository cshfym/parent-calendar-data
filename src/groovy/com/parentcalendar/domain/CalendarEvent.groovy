package com.parentcalendar.domain

import com.parentcalendar.domain.common.Persistable
import com.parentcalendar.services.exclusion.EntityExclude

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@Table(name = "calendar_event")
class CalendarEvent extends Persistable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id

    @EntityExclude // Exclude from JSON response
    @JoinColumn (name = "calendar_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    Calendar calendar

    @Column (name = "active")
    boolean active

    @Column (name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date createDate

    @Column (name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date updateDate

    @Override
    public String toString() {
        "CalendarEvent [ id: $id, calendar: $calendar.id, active: $active, createDate: $createDate, updateDate: $updateDate ]"
    }
}
