package com.parentcalendar.services.utility

import grails.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class UtilityService {

    def convertToBase64(String payload) {
        def codec = new org.codehaus.groovy.grails.plugins.codecs.Base64Codec()
        codec.encodeAsBase64(payload)
    }

    def decodeFromBase64(byte[] payload) {
        def codec = new org.codehaus.groovy.grails.plugins.codecs.Base64Codec()
        codec.decodeBase64(payload)
    }
}
