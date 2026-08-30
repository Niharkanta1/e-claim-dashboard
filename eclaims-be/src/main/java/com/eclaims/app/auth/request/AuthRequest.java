package com.eclaims.app.auth.request;

import lombok.Data;

@Data
public class AuthRequest {
	private String username;
	private String password;
	private String currentPassword;
	private String newPassword;
	private String email;
}
