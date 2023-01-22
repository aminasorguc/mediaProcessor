package com.media.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.media.config.TestConfiguration;
import com.media.dao.UserDao;
import com.media.entity.User;

import ch.qos.logback.classic.Logger;

@ContextConfiguration(classes = TestConfiguration.class)
@WebMvcTest(UserController.class)
@Disabled
public class RegistrationIT {
	
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(RegistrationIT.class);
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserDao userDAO;
	
	@BeforeAll
	public static void setup(@Autowired DataSource dataSource) throws SQLException {
		try (Connection conn = dataSource.getConnection()) {
			ScriptUtils.executeSqlScript(conn, new ClassPathResource("data.sql"));
		}
	}
	
	@AfterAll
	public static void destroy(@Autowired DataSource dataSource) throws SQLException {
		try (Connection conn = dataSource.getConnection()) {
			ScriptUtils.executeSqlScript(conn, new ClassPathResource("drop-table.sql"));
		}
	}

	@Test
    public void registrationTest() throws Exception {
		User user = new User();
		user.setEmail("test@test.com");
		user.setFirstName("test");
		user.setLastName("test");
		user.setPassword("testing");
		
		mockMvc.perform(post("/register/save")
				.flashAttr("user", user))
				.andExpect(status().is(302));
		
		User createdUser = userDAO.findByEmail(user.getEmail());

		LOGGER.info("registered user [{}]", createdUser);
		assertNotNull(createdUser);
    }
}
