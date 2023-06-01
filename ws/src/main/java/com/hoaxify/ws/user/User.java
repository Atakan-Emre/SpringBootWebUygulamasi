package com.hoaxify.ws.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue
	private long id;
	
	@NotNull(message="{hoaxify.constraint.username.NotNull.message}")
	@Size(min = 4, max=255)
	@UniqueUsername
	private String username;
	//Yorum satırı
	@NotNull(message="{hoaxify.constraint.displayname.NotNull.message}")
	@Size(min = 4, max=255)
	private String displayName;
	
	@NotNull
	@Size(min = 8, max=255)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message="{hoaxify.constrain.password.Pattern.message}")
	private String password;
	
}
