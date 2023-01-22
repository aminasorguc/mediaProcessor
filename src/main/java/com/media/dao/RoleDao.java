package com.media.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.media.entity.Role;

import ch.qos.logback.classic.Logger;

@Repository
public class RoleDao {
	private static final Logger LOGGER = (Logger)LoggerFactory.getLogger(UserDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void addRole(Role role) {
		LOGGER.info("Adding new role [{}]", role);
		sessionFactory.getCurrentSession().save(role);
	}

	
	@Transactional
	public Role findByName(String name) {
		LOGGER.info("Find role by name [{}]", name);
		Role role = null;
		try {
			CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
			CriteriaQuery<Role> cq = cb.createQuery(Role.class);
			Root<Role> model = cq.from(Role.class);
			cq.where(cb.equal(model.get("name"), name));
			TypedQuery<Role> q = sessionFactory.getCurrentSession().createQuery(cq);
			role = q.getSingleResult();
		} catch (NoResultException e) {
			LOGGER.warn("Exception: ", e);
			role = null;
		}
		return role;
	}
}
