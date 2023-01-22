package com.media.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.media.dao.UserDao;
import com.media.entity.Role;
import com.media.entity.User;

import ch.qos.logback.classic.Logger;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userDao.findByEmail(email);
        try {
        	return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRoles()));
        } catch (Exception e) {
        	LOGGER.warn("exception: ", e);
        	throw new UsernameNotFoundException("Invalid username or password.");
		}
    }

    private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
        Collection < ? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }
}

