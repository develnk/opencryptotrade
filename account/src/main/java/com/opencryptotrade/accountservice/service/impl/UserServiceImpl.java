package com.opencryptotrade.accountservice.service.impl;

import com.opencryptotrade.commons.user.domain.Role;
import com.opencryptotrade.commons.user.domain.RoleType;
import com.opencryptotrade.commons.user.domain.User;
import com.opencryptotrade.commons.user.dto.UserAccount;
import com.opencryptotrade.commons.user.dto.UserDto;
import com.opencryptotrade.commons.user.repository.RoleRepository;
import com.opencryptotrade.commons.user.repository.UserRepository;
import com.opencryptotrade.accountservice.service.UserService;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserDto create(UserDto userDto) {
		User userWithDuplicateUsername = userRepository.findByUsername(userDto.getLogin());
		if(userWithDuplicateUsername != null && userDto.getId() != userWithDuplicateUsername.getId()) {
			log.error(String.format("Duplicate username %s", userDto.getLogin()));
			throw new IllegalArgumentException("Duplicate username.");
		}
		User userWithDuplicateEmail = userRepository.findByEmail(userDto.getEmail());
		if(userWithDuplicateEmail != null && userDto.getId() != userWithDuplicateEmail.getId()) {
			log.error(String.format("Duplicate email %s", userDto.getEmail()));
			throw new IllegalArgumentException("Duplicate email.");
		}

		OffsetDateTime currentTime = OffsetDateTime.now();
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setUsername(userDto.getLogin());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setCreated(currentTime);
		user.setUpdated(currentTime);
		if (userDto.getRole().size() >= 1) {
			List<RoleType> roleTypes = new ArrayList<>();
			userDto.getRole().stream().map(role -> roleTypes.add(RoleType.valueOf(role)));
			// @TODO Need refactor
			user.setRoles(roleRepository.find(userDto.getRole()));
		}
		User savedUser = userRepository.save(user);
		log.info("New user has been created: {}", userDto.getLogin());
		return savedUser.toUserDto();
	}

	@Override
	public User update(UserDto user) {
		Javers javers = JaversBuilder.javers().build();
		User foundUser = userRepository.findByUsername(user.getLogin());
		if (foundUser == null) {
			throw new UsernameNotFoundException("Unknown user!");
		}
		UserDto foundUserDto = foundUser.toShortUserDto();
		Diff diff = javers.compare(user, foundUserDto);
		if (diff.hasChanges()) {
			foundUser.setEmail(user.getEmail());
			// @TODO Need refactor
			foundUser.setRoles(roleRepository.find(user.getRole()));
			foundUser.setUpdated(OffsetDateTime.now());
			userRepository.save(foundUser);
		}

		return foundUser;
	}

	@Override
	public List<UserAccount> findAll() {
		List<UserAccount> users = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(user -> users.add(user.toUserAccount()));
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
