package com.media.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.media.entity.Role;
import com.media.entity.User;

import ch.qos.logback.classic.Logger;

@Repository
public class UserDao {
	private static final Logger LOGGER = (Logger)LoggerFactory.getLogger(UserDao.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleDao roleDao;
	
	@Transactional
	public void addUser(User user) {
		LOGGER.info("Adding new user [{}]", user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role newrole = new Role();
		newrole.setName("ROLE_ADMIN");
		roleDao.addRole(newrole);
        user.setRoles(Arrays.asList(newrole));
		sessionFactory.getCurrentSession().save(user);
	}
	
	@Transactional
	public User findByEmail(String email) {
		LOGGER.info("Find User by email [{}]", email);
		User user = null;
		try {
			CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(User.class);
			Root<User> model = cq.from(User.class);
			cq.where(cb.equal(model.get("email"), email));
			TypedQuery<User> q = sessionFactory.getCurrentSession().createQuery(cq);
			user = q.getSingleResult();
		} catch (NoResultException e) {
			LOGGER.warn("Exception: ", e);
			user = null;
		}
		return user;
	}
	
	@Transactional
	public List<User> findAllUsers() {
		CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		cq.from(User.class);
		return sessionFactory.getCurrentSession().createQuery(cq).getResultList();
	}

	@Transactional
	private void checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        roleDao.addRole(role);
    }
}
