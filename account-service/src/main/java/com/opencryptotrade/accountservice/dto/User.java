package com.opencryptotrade.accountservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

	@NotNull
	@Setter	@Getter
	@Length(min = 3, max = 20)
	private String login;

	@NotNull
	@Setter	@Getter
	@Length(min = 6, max = 40)
	private String password;

	@Setter	@Getter
	private String firstName;

	@Setter	@Getter
	private String lastName;

	@NotNull
	@Setter	@Getter
	private String email;

	@NotNull
	@Setter	@Getter
	private List<String> role;

	@Setter	@Getter
	private String note;

}
