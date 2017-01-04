package com.hospital.organization.service;

import java.util.Scanner;

public class GetDataFromUser {

	public static String getTextMessage(String txt){
		System.out.println(txt);
		Scanner scan = new Scanner(System.in);
		String value = scan.nextLine();
		return value;
	}
	
	public static int getNumberMessage(String txt){
		System.out.println(txt);
		Scanner scan = new Scanner(System.in);
		int value = scan.nextInt();
		return value;
	}
	
	public static void startMenu(){
		System.out.println("");
		System.out.println("************ MENU ************");
		System.out.println("1. add doctor");
		System.out.println("2. add patient");
		System.out.println("3. update doctor payment");
		System.out.println("4. list doctors with payment");
		System.out.println("5. list patients");
		System.out.println("6. remove patient");
		System.out.println("************ MENU ************");
		System.out.println("");
	}
}
