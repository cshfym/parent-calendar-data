package com.parentcalendar.services.db

import com.parentcalendar.domain.TestData
import grails.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class GenericDataService extends BaseEntityManager {

  def persistTestData(TestData data) {
    data.setCreateDate(new Date())
    data.setPayloadClass(TestData.getName())
    create(data)
  }
}
