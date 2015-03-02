package com.parentcalendar.domain

import com.parentcalendar.domain.common.Persistable

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@Table(name = "calendar")
class Calendar extends Persistable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id

    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    User user

    @Column (name = "active")
    boolean active

    @Column (name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date createDate

    @Column (name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date updateDate

    @Column (name = "description")
    String description

    @Column (name = "_default")
    Boolean _default

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "calendar")
    List<CalendarEvent> events

    @Override
    public String toString() {
        "Calendar [ id: $id, user: $user, active: $active, createDate: $createDate, " +
                "updateDate: $updateDate, description: $description, calendarEvents: $events?.size(), default: $_default ]"
    }
}
