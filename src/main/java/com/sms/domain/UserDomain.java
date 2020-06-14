package com.sms.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "user")
public class UserDomain {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	@Column(length = 50)
	String firstName;
	@Column(length = 50)
	String lastName;
	@Column(length = 50)
	String email;
	@Column(length = 10)
	long mobile;
	@Column
	boolean validate;
	@Column(length = 20)
	String loginType;
	@Column(length = 10)
	String role;
	@Column
	Timestamp createdTs;
	@Column
	Timestamp modifiedTs;

	@Column(length = 250)
	String username;

	@Column(length = 250)
	String password;

	@Column(length = 36)
	String resetToken;

	@Column
	Timestamp resetTokenExpiry;

	@Column(length = 36)
	String emailVerifyToken;

	@Column
	boolean emailVerified;

	public String getEmailVerifyToken() {
		return emailVerifyToken;
	}

	public void setEmailVerifyToken(String emailVerifyToken) {
		this.emailVerifyToken = emailVerifyToken;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public Timestamp getResetTokenExpiry() {
		return resetTokenExpiry;
	}

	public void setResetTokenExpiry(Timestamp resetTokenExpiry) {
		this.resetTokenExpiry = resetTokenExpiry;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public Timestamp getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}

	public Timestamp getModifiedTs() {
		return modifiedTs;
	}

	public void setModifiedTs(Timestamp modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
