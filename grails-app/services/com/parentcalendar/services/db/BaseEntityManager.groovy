package com.parentcalendar.services.db

import com.parentcalendar.domain.User
import com.parentcalendar.domain.common.Persistable
import org.hibernate.Criteria
import org.hibernate.HibernateException
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.hibernate.criterion.Restrictions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BaseEntityManager {

  @Autowired
  HibernateConfigurationService configurationService

  private static SessionFactory factory

  public BaseEntityManager() { }

  protected SessionFactory getSessionFactory() {

      if (!factory) {
        try {
            factory = configurationService.getHibernateConfiguration().buildSessionFactory()
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex)
            throw new ExceptionInInitializerError(ex)
        }
      }
      factory
  }

  /* Find Single Entity */
  public <T extends Persistable> T find(Class<T> type, Long id) {

    Persistable obj = null
    Session session = getSessionFactory().openSession()
    Transaction tx

    try {
      tx = session.beginTransaction()
      obj = session.get(type, id)
      tx.commit()
    } catch (HibernateException e) {
      if (tx) { tx.rollback() }
      throw e
    } finally {
      session.close()
    }

    obj
  }

  /* Find Single Entity By Attribute */
  public <T extends Persistable> T findBy(Class<T> type, String property, Object value) {

    Persistable obj = null
    Session session = getSessionFactory().openSession()
    Transaction tx

    Criteria criteria = session.createCriteria(type)
              .add(Restrictions.eq(property, value))

    try {
        tx = session.beginTransaction()
        obj = criteria.uniqueResult()
        tx.commit()
    } catch (HibernateException e) {
        if (tx) { tx.rollback() }
        throw e
    } finally {
        session.close()
    }

    obj
  }

    /* Find Single Entity By Attribute */
    public <T extends Persistable> T findByWithUser(Class<T> type, String property, Object value, User user) {

        Persistable obj = null
        Session session = getSessionFactory().openSession()
        Transaction tx

        Criteria criteria = session.createCriteria(type)
                .add(Restrictions.eq(property, value))
                .add(Restrictions.eq("user.id", user.id))

        try {
            tx = session.beginTransaction()
            obj = criteria.uniqueResult()
            tx.commit()
        } catch (HibernateException e) {
            if (tx) { tx.rollback() }
            throw e
        } finally {
            session.close()
        }

        obj

    }

  /* Find All Entities */
  public <T extends Persistable> List<T> findAll(String type) {

    List<T> result = []

    Session session = getSessionFactory().openSession()
    Transaction tx

    try {
      tx = session.beginTransaction()
      List data = session.createQuery("FROM " + type).list()
      data.each {
        result << it
      }
      tx.commit()
    } catch (HibernateException e) {
      if (tx) {
        tx.rollback()
      }
      throw e
    } finally {
      session.close()
    }
    result
  }

  /* Find All Entities by UserID */
  public <T extends Persistable> List<T> findAllByUser(Class<T> type, Long userId) {

    List<T> result = []

    Session session = getSessionFactory().openSession()
    Transaction tx = null

    Criteria criteria = session.createCriteria(type)
            .add(Restrictions.eq("user.id", userId))

    try {
      tx = session.beginTransaction()
      List data = criteria.list()
      data.each {
         result << it
      }
      tx.commit()
    } catch (HibernateException e) {
      if (tx != null) {
        tx.rollback()
      }
      throw e
    } finally {
      session.close()
    }

    result
  }

  /* Create Entity */
  public <T extends Persistable> T create(Persistable object) {

    Session session = getSessionFactory().openSession()
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

    Session session = getSessionFactory().openSession()
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

    Session session = getSessionFactory().openSession()
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
