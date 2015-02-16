package com.parentcalendar.domain

import javax.persistence.*

@Entity
@Table(name = "gps_data")
class TestData extends Persistable {

  public TestData() { }

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name = "id")
  Long id

  @Column(name = "user_id")
  String userId

  @Column(name = "payload_class")
  String payloadClass

  @Column(name = "payload")
  @Lob
  byte[] payload

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "create_date", columnDefinition="DATETIME")
  Date createDate

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "update_date", columnDefinition="DATETIME")
  Date updateDate

  @Transient
  final static int TTL = 30

  Long getId() { id }
  void setId(Long id) { this.id = id }

  String getUserId() { userId }
  void setUserId(String userId) { this.userId = userId }

  String getPayloadClass() { payloadClass }
  void setPayloadClass(String payloadClass) { this.payloadClass = payloadClass }

  byte[] getPayload() { payload }
  void setPayload(byte[] payload) { this.payload = payload }

  Date getCreateDate() { createDate }
  void setCreateDate(Date createDate) { this.createDate = createDate }

  Date getUpdateDate() { updateDate }
  void setUpdateDate(Date updateDate) { this.updateDate = updateDate }

  int getTTL() { TTL }
}
