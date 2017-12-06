package org.sdrc.scpstamilnadu.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */
@Entity
@Table(name = "user_tbl")
public class User implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7162562566750585757L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column
	private String name;

	@Column(name = "user_name")
	private String userName;

	@Column
	private String password;

	@Column
	private String email;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column
	private String otp;

	@Column(name = "invalid_attempts",columnDefinition="smallint default '0'")
	private short invalidAttempts;

	@Column(name = "last_otp_generated_time")
	private Date otpGeneratedDateTime;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@ManyToOne
	@JoinColumn(name = "role_id_fk")
	private Role role;

	@ManyToOne
	@JoinColumn(name = "agency_id_fk")
	private Agency agency;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<UserAreaMapping> userAreaMappings;

	// getter setters
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public List<UserAreaMapping> getUserAreaMappings() {
		return userAreaMappings;
	}

	public void setUserAreaMappings(List<UserAreaMapping> userAreaMappings) {
		this.userAreaMappings = userAreaMappings;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Date getOtpGeneratedDateTime() {
		return otpGeneratedDateTime;
	}

	public void setOtpGeneratedDateTime(Date otpGeneratedDateTime) {
		this.otpGeneratedDateTime = otpGeneratedDateTime;
	}

	public short getInvalidAttempts() {
		return invalidAttempts;
	}

	public void setInvalidAttempts(short invalidAttempts) {
		this.invalidAttempts = invalidAttempts;
	}

	/**
	 * 
	 * @param user
	 * @return returns a MD5 password hash
	 */

	public String generatedEncodedPassword(String userName,String password) {
		MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("MD5");
		return encoder.encodePassword(userName,password);
	}

	public String getPlainPassword(User user) {
		return user.getUserName() + "_" + user.getRole().getRoleId();
	}

}
