package com.opencryptotrade.commons.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencryptotrade.commons.user.dto.UserAccount;
import com.opencryptotrade.commons.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "Users")
@Setter
@Getter
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@JsonIgnore
	private Long id;

	@Column(name = "login")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "User_ROLES", joinColumns = @JoinColumn(name ="user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles;

	@Column(name = "created", columnDefinition= "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime created;

	@Column(name = "updated", columnDefinition= "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime updated;

	@Override
	public List<GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().toString())).collect(Collectors.toList());
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

	public UserDto toUserDto() {
		UserDto userDto = new UserDto();
		userDto.setId(this.getId());
		userDto.setEmail(this.getEmail());
		userDto.setLogin(this.getUsername());
		userDto.setRole(this.roles.stream().map(role -> role.getName().toString()).collect(Collectors.toList()));
		userDto.setCreated(this.getCreated());
		userDto.setUpdated(this.getUpdated());
		return userDto;
	}

	public UserAccount toUserAccount() {
		UserAccount userAccount = new UserAccount();
		userAccount.setId(this.getId());
		userAccount.setLogin(this.getUsername());
		userAccount.setEmail(this.getEmail());
		userAccount.setCreated(this.getCreated());
		userAccount.setUpdated(this.getUpdated());
		userAccount.setRole(this.roles.stream().map(role -> role.getName().toString()).collect(Collectors.toList()));
		return userAccount;
	}

	public UserDto toShortUserDto() {
		UserDto userDto = new UserDto();
		userDto.setEmail(this.getEmail());
		userDto.setLogin(this.getUsername());
		return userDto;
	}

}
