package com.parentcalendar.domain.enums


public enum Constants {

    // Header values
    X_AUTH_ALL_USERS("X-Auth-All-Users"),    // Query should include all user data
    X_AUTH_USER_TOKEN("X-Auth-User-Token"),  // User from which request originated
    X_AUTH_USER_ID("X-Auth-User-Id")         // User-specific data ID

    String value

    public Constants(String value) {
        this.value = value
    }

}