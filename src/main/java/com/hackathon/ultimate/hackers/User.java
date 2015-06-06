package com.hackathon.ultimate.hackers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents User table.
 * 
 * @author asif
 */

@Entity
@Table(name = "User")
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString
public class User {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private int id;

	/**
	 * Name of user.
	 */
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	/**
	 * Mobile no.
	 */
	@Column(name = "mobile", nullable = false, length = 13)
	private String mobile;

	/**
	 * Address of user.
	 */
	@Column(name = "address", nullable = false, length = 400)
	private String address;

	/**
	 * Email Id of user.
	 */
	@Column(name = "email", nullable = false, length = 50, unique = true)
	private String email;

	/**
	 * Hash of password of user.
	 */
	@Column(name = "password", nullable = false, length = 500)
	private String password;
	
	/**
	 * Default constructor for hibernate.
	 */
	private User() {
	}

	public User(final String name, final String mobile, final String address,
			final String email, final String password) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.address = address;
		this.email = email;
		this.password = password;
	}
}
