package com.hospital.organization.service;

import java.util.Comparator;

import com.hospital.organization.domain.Doctor;

public class DoctorCapacityComparer implements Comparator<Doctor>{

	public int compare(Doctor o1, Doctor o2) {
		// TODO Auto-generated method stub
		return o1.getPatientQuantity() - o2.getPatientQuantity();
	}
}
