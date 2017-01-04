package com.hospital.organization.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="doctor")
public class Doctor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="lastName")
	private String lastName;
	
	@Column(name="payment")
	private int payment;
	
	@Column(name="patientQuantity")
	private int patientQuantity;
	
	public int getPatientQuantity() {
		return patientQuantity;
	}

	public void setPatientQuantity(int patientQuantity) {
		this.patientQuantity = patientQuantity;
	}

	public Doctor(){}

	public Doctor(String firstName, String lastName, int payment){
		this.firstName = firstName;
		this.lastName = lastName;
		this.payment = payment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}
}
