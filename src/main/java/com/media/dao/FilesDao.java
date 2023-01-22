package com.media.dao;

import java.io.IOException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.media.entity.Files;
import com.media.entity.User;

@Repository
public class FilesDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void store(MultipartFile file, Long usersid) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Files fileDB = new Files();
		fileDB.setName(fileName);
		fileDB.setType(file.getContentType());
		fileDB.setData(file.getBytes());
		User newuser = new User();
		newuser.setId(usersid);
		fileDB.setUser(newuser);
		sessionFactory.getCurrentSession().save(fileDB);
	}

	@Transactional
	public void deleteFile(Long id) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaDelete<Files> criteriaDelete = criteriaBuilder.createCriteriaDelete(Files.class);
		Root<Files> root = criteriaDelete.from(Files.class);
		criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));
		sessionFactory.getCurrentSession().createQuery(criteriaDelete).executeUpdate();
	}
}
