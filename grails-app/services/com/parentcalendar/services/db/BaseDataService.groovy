package com.parentcalendar.services.db

import com.parentcalendar.domain.Persistable
import grails.transaction.Transactional
import org.springframework.stereotype.Service

/**
 * Data services available to all domain-specific services.
 * Performs argument-level validation.
 */
@Service
@Transactional
class BaseDataService extends BaseEntityManager {

  static final EMPTY_STRING = ""

  public <T extends Persistable> T findById(Class<T> type, Long id) {

    if (!validateType(type)) {
      throw new IllegalArgumentException("Parameter type must not be null.")
    }
    if (!validateId(id)) {
      throw new IllegalArgumentException("Parameter id must be numeric and not null.")
    }

    super.find(type, id)
  }

  public <T extends Persistable> T findAll(Class<T> type) {

    if (!validateType(type)) {
      throw new IllegalArgumentException("Parameter type must not be null.")
    }

    super.findAll(type.getName())
  }

  public <T extends Persistable> T create(Persistable object) {

    if (!object) {
      throw new IllegalArgumentException("Parameter object must not be null.")
    }

    super.create(object)
  }

  public void delete(Persistable object) {

    if (!object) {
      throw new IllegalArgumentException("Parameter object must not be null.")
    }

    super.delete(object)
  }

  protected Boolean validateId(Long id) {

    if (!id) {
      return false
    }

    try {
      Long.valueOf(id)
    } catch (NumberFormatException ex) {
      return false
    }

    true
  }

  protected Boolean validateType(Object type) {

    if (!type) {
      return false
    }

    true
  }
}