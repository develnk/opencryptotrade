package com.opencryptotrade.authservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencryptotrade.authservice.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "Users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@JsonIgnore
	private Long id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "User_ROLES", joinColumns = @JoinColumn(name ="USER_ID"), inverseJoinColumns = @JoinColumn(name="ROLE_ID"))
	private Set<Role> roles;

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public List<GrantedAuthority> getAuthorities() {
		return null;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserDto toUserDto(){
		UserDto userDto = new UserDto();
		userDto.setId(this.id);
		userDto.setEmail(this.email);
		userDto.setFirstName(this.firstName);
		userDto.setLastName(this.lastName);
		userDto.setUsername(this.username);
		userDto.setRole(this.roles.stream().map(role -> role.getName().toString()).collect(Collectors.toList()));
		return userDto;
	}
}
