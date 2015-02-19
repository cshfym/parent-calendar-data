package com.parentcalendar.services.db

import com.parentcalendar.domain.Persistable
import org.hibernate.HibernateException
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.hibernate.cfg.AnnotationConfiguration
import org.springframework.stereotype.Component

@Component
class BaseEntityManager {

  private static SessionFactory factory

  public BaseEntityManager() {
    try {
      factory = new AnnotationConfiguration().configure().buildSessionFactory()
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex)
      throw new ExceptionInInitializerError(ex)
    }
  }

  /* Find Single Entity */
  public <T extends Persistable> T find(Class<T> type, Long id) {

    Persistable obj = null
    Session session = factory.openSession()

    try {
      obj = session.get(type, id)
    } catch (HibernateException e) {
      throw e
    } finally {
      session.close()
    }

    obj
  }

  /* Find All Entities */
  public <T extends Persistable> List<T> findAll(String type) {

    List<T> result = []

    Session session = factory.openSession()
    Transaction tx = null

    try {
      tx = session.beginTransaction()
      List data = session.createQuery("FROM " + type).list()
      data.each {
        result << it
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) {
        tx.rollback()
      }
      throw e
    } finally {
      session.close();
    }
    result
  }

  /* Create Entity */
  public <T extends Persistable> T create(Persistable object) {

    Session session = factory.openSession()
    Transaction tx = null

    try {
      tx = session.beginTransaction()
      session.save(object)
      tx.commit()
    } catch (HibernateException e) {
      if (tx != null) tx.rollback()
      throw e
    } finally {
      session.close()
    }
    object
  }

  /* Update Entity */
  public <T extends Persistable> T update(Persistable object) {

    Session session = factory.openSession()
    Transaction tx = null

    try {
      tx = session.beginTransaction()
      session.update(object)
      tx.commit()
    } catch (HibernateException e) {
      if (tx != null) tx.rollback()
      throw e
    } finally {
      session.close()
    }
    object
  }

  /* Delete Entity */
  public void delete(Persistable obj) {

    Session session = factory.openSession()
    Transaction tx = null

    try {
      tx = session.beginTransaction()
      session.delete(obj)
      tx.commit()
    } catch (HibernateException e) {
      if (tx != null) tx.rollback()
      throw e
    } finally {
      session.close()
    }
  }
}
