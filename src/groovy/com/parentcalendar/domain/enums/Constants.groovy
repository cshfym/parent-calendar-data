package com.parentcalendar.domain.enums


public enum Constants {

    X_AUTH_ALL_USERS("X-Auth-All-Users"),
    X_AUTH_USER_TOKEN("X-Auth-User-Token")

    String value

    public Constants(String value) {
        this.value = value
    }

}