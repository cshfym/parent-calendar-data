package com.parentcalendar.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "user")
class Test {

    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id

    @Column(name = "active")
    Boolean active

}
