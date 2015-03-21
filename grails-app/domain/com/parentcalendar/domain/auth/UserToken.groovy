package com.parentcalendar.domain.auth

import com.parentcalendar.domain.common.Persistable

class UserToken extends Persistable {

    Long id
    String token
    Date issued

    User user

    @Override
    public String toString() {
        "UserToken: [ id: $id, user: $user, token: $token, issued: $issued, sessionId: $sessionId ]"
    }
}

