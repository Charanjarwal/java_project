package service;

import java.util.regex.Pattern;

public class ValidateContent {
	
	public boolean validateunamepass(String username,String password) {
		
		
		if((Pattern.matches("[a-zA-Z]{6,10}",username))&&(Pattern.matches("[a-zA-Z0-9]{5,10}", password))) {
	        System.out.println("  ");	
			System.out.println("User Registered Succesfully");
			System.out.println("Now fill the Next Details");
			System.out.println("  ");
			return true;
			
		}
		else {
			
			System.out.println("please Enter username and pass in correct format");
		}
		
		
		
		
		
		return false;
		
		
	}
	
	
	

}
