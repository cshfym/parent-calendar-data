package com.parentcalendar.services.db

import com.parentcalendar.domain.common.Persistable
import com.parentcalendar.domain.enums.RequestScope
import grails.transaction.Transactional
import org.codehaus.groovy.grails.validation.Validateable
import org.springframework.stereotype.Component

/**
 * Data services available to all domain-specific services.
 * Performs argument-level validation.
 */
@Deprecated
@Component
@Validateable
@Transactional
class BaseDataService extends BaseEntityManager {

    public <T extends Persistable> T findById(Class<T> type, Long id) {

        if (!validateType(type)) {
            throw new IllegalArgumentException("Parameter type must not be null.")
        }
        if (!validateId(id)) {
            throw new IllegalArgumentException("Parameter id must be numeric and not null.")
        }

        super.find(type, id)
    }

    /*
    public <T extends Persistable> T findBy(Class<T> type, String property, Object value, RequestScope scope, User user) {

        if (!validateType(type)) {
            throw new IllegalArgumentException("Parameter type must not be null.")
        }

        def result

        switch(scope) {
            case RequestScope.SCOPE_GLOBAL:
                result = super.findBy(type.getSimpleName())
                break
            case RequestScope.SCOPE_REQUESTOR:
                result = super.findByWithUser(type.getSimpleName(), user)
                break
            case RequestScope.SCOPE_USER:
                break
        }

        result
    }
      */

  public <T extends Persistable> List<T> findAll(Class<T> type, RequestScope scope, Long requestUserId, Long userId) {

      if (!validateType(type)) {
          throw new IllegalArgumentException("Parameter type must not be null.")
      }

      def result

      switch(scope) {
          case RequestScope.SCOPE_GLOBAL:
              result = super.findAll(type.getSimpleName())
              break
          case RequestScope.SCOPE_REQUESTOR:
              result = super.findAllByUserId(type, requestUserId)
              break
          case RequestScope.SCOPE_USER:
              result = super.findAllByUserId(type, userId)
              break
      }

      result
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
