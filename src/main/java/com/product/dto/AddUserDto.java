package com.product.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserDto {
	@NotBlank(message = "User Name Can't Be Empty!")
	private String name;
	@Email(message = "Please Enter Valid Email")
	private String email;
	@NotBlank(message = "Password Can't Be Empty!")
	private String password;
	@NotBlank(message = "Role Can't Be Empty!")
	private String role;
}
