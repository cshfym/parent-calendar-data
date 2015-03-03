package com.parentcalendar.domain.security

import com.parentcalendar.domain.User
import com.parentcalendar.domain.common.Persistable
import com.parentcalendar.services.exclusion.EntityExclude

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@Table(name = "user_token")
class UserToken extends Persistable {

    @Id
    @EntityExclude
    @GeneratedValue
    @Column(name = "id")
    Long id

    @EntityExclude
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    User user

    @Column (name = "token")
    String token

    @EntityExclude
    @Column (name = "issued")
    @Temporal(TemporalType.TIMESTAMP)
    Date issued

    @Override
    public String toString() {
        "UserToken: [ id: $id, user: $user, token: $token, issued: $issued ]"
    }
}
