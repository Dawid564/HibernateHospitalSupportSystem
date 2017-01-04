package com.hospital.organization.HospitalSupportSystem;

import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hospital.organization.domain.Doctor;
import com.hospital.organization.domain.Patient;
import com.hospital.organization.service.DoctorCapacityComparer;

public class Connector {

	private SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(Doctor.class)
			.buildSessionFactory();

	private SessionFactory sessionFactoryPatient = new Configuration().configure().addAnnotatedClass(Patient.class)
			.buildSessionFactory();

	public Connector() {
	}

	public void removePatient(String firstName, String lastName, List<Patient> list) {

		// TO DO
		// remove from table from database DONE
		// decrease paientQuantity in right doctor row DONE
		Patient patient = new Patient();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getFirstName().equals(firstName)) {
				if( list.get(i).getLastName().equals(lastName)){
					patient = list.get(i);
					break;
				}
			}
		}
		
		// decrease number of actual patient
		decresePatientQuantity(patient.getDoctorLastName());

		// remove patient from table
		Session session = sessionFactoryPatient.getCurrentSession();
		try {
			session.beginTransaction();
			session.delete(patient);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("you shouln't see this message 1");
			e.printStackTrace();
		}
	}

	public void decresePatientQuantity(String lastName) {
		Doctor doc = new Doctor();
		String sqlQuery = "UPDATE Doctor SET patientQuantity = :patientQuantity WHERE lastName = :lastName";
		List<Doctor> list = listDoctors();
		for(Doctor doctor: list){
			//System.out.println("doctor name: " + doctor.getFirstName());
			if (doctor.getLastName().equals(lastName)) {
				doc = doctor;
				break;
			}
		}
		System.out.println(" ");
		System.out.println("doctor name after if: " + doc.getFirstName());
		
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println("Doc last name: " + doc.getLastName());
//			if (list.get(i).equals(lastName)) {
//				doc = list.get(i);
//				break;
//			}
//		}
		
		try {
			int patientQuantity = doc.getPatientQuantity();
			System.out.println("Doc last name: " + patientQuantity);
			//doc.setPatientQuantity(patientQuantity - 1);

			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(sqlQuery);
			query.setParameter("patientQuantity", patientQuantity - 1);
			query.setParameter("lastName", lastName);
			//session.update(doc);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("you shouln't see this message 2");
			e.printStackTrace();
		}
	}

	public void displayPatient(List<Patient> list) {
		for (Patient pat : list) {
			System.out.println(pat.getFirstName() + " " + pat.getDisease());
		}
	}

	public List<Patient> listPatients() {
		String sqlQuery = "FROM Patient";
		List<Patient> listPatient;
		Session sessionPatient = sessionFactoryPatient.getCurrentSession();
		try {
			sessionPatient.beginTransaction();
			Query query = sessionPatient.createQuery(sqlQuery);
			listPatient = query.list();
			sessionPatient.getTransaction().commit();
			return listPatient;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void addPatient(String firstName, String lastName, String disease) {
		Session session = sessionFactoryPatient.getCurrentSession();

		try {
			Doctor operateDoctor = getFreeDoctor();
			session.beginTransaction();
			// Patient patient = new Patient(firstName, lastName, disease,
			// getFreeDoctor());
			// get Doctor with the most low quantity of patients
			Patient patient = new Patient(firstName, lastName, disease, operateDoctor.getLastName());
			session.save(patient);
			session.getTransaction().commit();
			
			
			
			// update patientQuantity in doctor table
			Session sessionDoctor = sessionFactory.getCurrentSession();
			sessionDoctor.beginTransaction();
			if(sessionDoctor.isConnected()){
				System.out.println("connected");
			}else{
				System.out.println("not connected");			
			}
			int quantity = operateDoctor.getPatientQuantity();
			operateDoctor.setPatientQuantity(quantity + 1);
			sessionDoctor.update(operateDoctor);
			sessionDoctor.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Doctor getFreeDoctor() { // sort list by quantity of patient
		List<Doctor> list = listDoctors();
		Collections.sort(list, new DoctorCapacityComparer());
		for (Doctor doc : list) {
			System.out.println(doc.getLastName());
		}
		Doctor firstDoc = list.get(0);
		return firstDoc;
	}

	public List<Doctor> listDoctors() { // list all object doctor from database
		String sqlQuery = "FROM Doctor";
		List<Doctor> docList;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(sqlQuery);
			docList = query.list();
			// for(Doctor doc : docList){
			// System.out.println("" + doc.getFirstName() + " " +
			// doc.getLastName() + " " + doc.getPayment());
			// }
			session.getTransaction().commit();
			return docList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// sessionFactory.close();
		}
		return null;
	}

	public boolean updateDoctorPayment(String firstName, String lastName, int payment) {
		String sqlQuery = "UPDATE Doctor SET payment = :payment WHERE firstName = :firstName AND lastName = :lastName";
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(sqlQuery);
			query.setParameter("payment", payment);
			query.setParameter("firstName", firstName);
			query.setParameter("lastName", lastName);
			query.executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			// sessionFactory.close();
		}
	}

	public boolean createNewDoctor(String firstName, String lastName, int payment) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Doctor doctor = new Doctor(firstName, lastName, payment);
			session.save(doctor);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println("ERROR!!");
			e.printStackTrace();
		} finally {
			// sessionFactory.close();
		}
		return false;
	}

	public void testConnection() {
		Session session = sessionFactory.getCurrentSession();
		try {
			Doctor doc = new Doctor("Aga", "Agowska", 2000);
			session.beginTransaction();
			session.save(doc);
			session.getTransaction().commit();
			System.out.println("Conected successfully");
		} catch (Exception e) {
			System.out.println("ERROR!!!");
			e.printStackTrace();
		} finally {
			// sessionFactory.close();
		}
	}
}
