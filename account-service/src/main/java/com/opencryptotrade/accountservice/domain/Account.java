package com.opencryptotrade.accountservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "Accounts")
public class Account implements Serializable {

	private static final long serialVersionUID = 3373812230395363132L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@Setter	@Getter
	@JsonIgnore
	private Long id;

	@Column
	@Setter	@Getter
	@Length(max = 255)
	private String login;

	@Column
	@Setter	@Getter
	private String firstName;

	@Column
	@Setter	@Getter
	private String lastName;

	@Column
	@Setter	@Getter
	private OffsetDateTime lastSeen;

	@Column
	@Setter	@Getter
	@Length(max = 20000)
	private String note;

}
