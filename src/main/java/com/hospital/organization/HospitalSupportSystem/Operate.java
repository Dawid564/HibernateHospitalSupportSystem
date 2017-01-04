package com.hospital.organization.HospitalSupportSystem;

import com.hospital.organization.service.GetDataFromUser;

public class Operate {
	private Connector connector = new Connector();

	public void run(){
		GetDataFromUser.startMenu();
		try{
			switch(GetDataFromUser.getNumberMessage("Choose one option")){
			case 1:
				System.out.println("Create new User");
				connector.createNewDoctor(GetDataFromUser.getTextMessage("Set First Name"), 
						GetDataFromUser.getTextMessage("Set Last Name"),+
						+ GetDataFromUser.getNumberMessage("Set Payment"));
				run();
				break;
			case 2:	//add patient
				System.out.println("is this even work?");
				connector.addPatient(GetDataFromUser.getTextMessage("What is patient name"), 
						GetDataFromUser.getTextMessage("What is patient Last Name"), 
						GetDataFromUser.getTextMessage("What is patient disease"));
				run();
				break;
			case 3:
				System.out.println("Update Payment");
				connector.updateDoctorPayment(GetDataFromUser.getTextMessage("Set First Name"), 
						GetDataFromUser.getTextMessage("Set Last Name"),
						GetDataFromUser.getNumberMessage("Set Update Payment"));
				run();
				break;
			case 4:
				connector.listDoctors();
				run();
				break;
			case 5:
				connector.displayPatient(connector.listPatients());
				run();
				break;
			case 6: // remove patient
				connector.removePatient(GetDataFromUser.getTextMessage("Patient Name"), 
						GetDataFromUser.getTextMessage("Patient Last Name"), connector.listPatients());
				run();
				break;
			case 7:
				connector.decresePatientQuantity("Prawica");
				run();
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
			run();
		}
		
	}
}
