package com.user.web.userweb.validation;

import java.util.HashMap;
import java.util.Map;

public class PhoneValidator {
	
	Map<String,Integer> _validationRules = new HashMap<>();
	public PhoneValidator(Map<String, Integer> validationRules) {
		_validationRules = validationRules;
	}

	public Boolean validate(String number) {
		return validateCharacters(number) && validateLength(number);
	}
	
	private Boolean validateCharacters(String number) {
		 
		char[] phoneNumber = number.toCharArray(); 
		
		if(phoneNumber[0] == '+') {
			for(int i = 1;i<phoneNumber.length;i++) {
				if(Character.isDigit(phoneNumber[i])) {
					return true;
				}
			}
		}else {
			for(int i = 0;i<phoneNumber.length;i++) {
				if(Character.isDigit(phoneNumber[i])) {
					return true;
				}
			}
		}
		return false;
	}
	
	private Boolean validateLength(String number) {
		
		String phoneNumber = convert(number);
		for (Map.Entry<String, Integer> pair : _validationRules.entrySet()) {
		   if(phoneNumber.length() - pair.getKey().length() == pair.getValue()) {
			   return true;
			   //+37064891365
			   // 12 - 4 = 8
		   }
		}
		return false;
	}
	
	private String convert(String number) {
		
		if(number.charAt(0) == '8') {
		 return "+370" + number.substring(1);
		}
		
		return number;
	}
	
	private void addPhnoeNumberRules(String prefix, int length) {
		_validationRules.put(prefix, length);
	}

}
