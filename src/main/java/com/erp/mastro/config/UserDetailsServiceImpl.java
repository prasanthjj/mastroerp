package com.erp.mastro.config;

import com.erp.mastro.dto.CurrentUserDetails;
import com.erp.mastro.entities.User;
import com.erp.mastro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Date;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;


	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid Username or Password");
		}

		if (user.getId()!= null){
			getCurrentLoginDate(user.getId());
		}

		return new CurrentUserDetails(user);
	}

	public Date getCurrentLoginDate(Long id){
		User user = userRepository.findById(id).get();
		user.setLastLogin(user.getCurrentLogin());
		user.setLoggedIn(true);
		user.setCurrentLogin(new Date());
		userRepository.save(user);

		return user.getCurrentLogin();
	}


}