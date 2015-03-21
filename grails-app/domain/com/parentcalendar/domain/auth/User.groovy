package com.parentcalendar.domain.auth


class User  {

    Long id
    Long version
    String email
    String username
    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

}

