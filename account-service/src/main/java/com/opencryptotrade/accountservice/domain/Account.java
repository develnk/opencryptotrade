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
@Setter
@Getter
public class Account implements Serializable {
	private static final long serialVersionUID = 3373812230395363132L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@JsonIgnore
	private Long id;

	@Column
	@Length(max = 255)
	private String login;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private OffsetDateTime lastSeen;

	@Column
	@Length(max = 20000)
	private String note;

}
