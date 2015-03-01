package com.parentcalendar.domain.common

import grails.validation.Validateable

@Validateable
class Persistable implements Serializable {

  // Default TTL in seconds
  private static final int TTL = 600

}
