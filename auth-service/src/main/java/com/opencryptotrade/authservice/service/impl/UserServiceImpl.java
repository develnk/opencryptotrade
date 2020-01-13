package com.opencryptotrade.authservice.service.impl;

import com.opencryptotrade.authservice.domain.Role;
import com.opencryptotrade.authservice.domain.RoleType;
import com.opencryptotrade.authservice.domain.User;
import com.opencryptotrade.authservice.dto.UserDto;
import com.opencryptotrade.authservice.repository.RoleRepository;
import com.opencryptotrade.authservice.repository.UserRepository;
import com.opencryptotrade.authservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public void create(UserDto userDto) {
		User userWithDuplicateUsername = userRepository.findByUsername(userDto.getUsername());
		if(userWithDuplicateUsername != null && userDto.getId() != userWithDuplicateUsername.getId()) {
			log.error(String.format("Duplicate username %s", userDto.getUsername()));
			throw new IllegalArgumentException("Duplicate username.");
		}
		User userWithDuplicateEmail = userRepository.findByEmail(userDto.getEmail());
		if(userWithDuplicateEmail != null && userDto.getId() != userWithDuplicateEmail.getId()) {
			log.error(String.format("Duplicate email %s", userDto.getEmail()));
			throw new IllegalArgumentException("Duplicate email.");
		}

		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setUsername(userDto.getUsername());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		if (userDto.getRole().size() >= 1) {
			List<RoleType> roleTypes = new ArrayList<>();
			userDto.getRole().stream().map(role -> roleTypes.add(RoleType.valueOf(role)));
			user.setRoles(roleRepository.find(userDto.getRole()));
		}
		userRepository.save(user);
		log.info("New user has been created: {}", userDto.getUsername());
	}

	@Override
	public List<UserDto> findAll() {
		List<UserDto> users = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(user -> users.add(user.toUserDto()));
		return users;
	}

	@Override
	public User findOne(long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public void delete(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null){
			log.error("Invalid username or password.");
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		Set<GrantedAuthority> grantedAuthorities = getAuthorities(user);


		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}

	private Set<GrantedAuthority> getAuthorities(User user) {
		Set<Role> roleByUserId = user.getRoles();
		return roleByUserId.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toString().toUpperCase())).collect(Collectors.toSet());
	}

}
