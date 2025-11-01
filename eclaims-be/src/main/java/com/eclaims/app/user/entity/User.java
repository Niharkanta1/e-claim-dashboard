package com.eclaims.app.user.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "app_user")
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue
	private Long id;

	private String username;

	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> roles = new HashSet<>();
}
