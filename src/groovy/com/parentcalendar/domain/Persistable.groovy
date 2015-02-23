package com.parentcalendar.domain

import grails.validation.Validateable

@Validateable
class Persistable implements Serializable {

  // Default TTL in seconds
  private static final int TTL = 600

}
