package com.media.controller;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.media.dao.FilesDao;
import com.media.dao.UserDao;
import com.media.entity.User;

import ch.qos.logback.classic.Logger;

@Controller
public class UserController {

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private FilesDao filesDao;

	@GetMapping("index")
	public String home() {
		return "index";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}

	@GetMapping("register")
	public String showRegistrationForm(Model model) {
		try {
			model.addAttribute("user", new User());
		} catch (Exception e) {
			LOGGER.warn("Exception occured : ", e);
		}
		return "register";
	}

	@PostMapping("/register/save")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		User existing = userDao.findByEmail(user.getEmail());
		try {
			if (existing != null) {
				result.rejectValue("email", null, "There is already an account registered with that email");
			}
			if (result.hasErrors()) {
				model.addAttribute("user", user);
				return "register";
			}
			userDao.addUser(user);
		} catch(Exception e) {
			LOGGER.warn("Exception: ", e);
		}
		return "redirect:/register?success";
	}

	@GetMapping("/users")
	public String viewUserAccountForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		String userEmail = userDetails.getUsername();
		try {
			User user = userDao.findByEmail(userEmail);
			model.addAttribute("user", user);
			model.addAttribute("usersid", user.getId());
			model.addAttribute("userfiles", user.getFiles());
		} catch(Exception e) {
			LOGGER.warn("Exception : ", e);
		}
		return "users";
	}

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("files") MultipartFile file, @RequestParam("usersid") Long usersid) {
		try {
			filesDao.store(file, usersid);
			LOGGER.debug("Uploaded file successfully [{}]: ", file.getOriginalFilename());
			return "redirect:/users?success";
		} catch (Exception e) {
			LOGGER.debug("Could not upload the file [{}]: ", file.getOriginalFilename());
			LOGGER.warn("Exception: ", e);
			return "redirect:/users?error";
		}
	}
	
	@PostMapping("/delete")
	public String deleteFile(@RequestParam("fileid") Long fileid) {
		try {
			filesDao.deleteFile(fileid);
			LOGGER.debug("successfully deleted file [{}]: ", fileid);
			return "redirect:/users?deletesuccess";
		} catch (Exception e) {
			LOGGER.debug("Could not delete file  [{}]: ", fileid);
			LOGGER.warn("Exception : ", e);
			return "redirect:/users?deleteerror";
		}
	}

}
