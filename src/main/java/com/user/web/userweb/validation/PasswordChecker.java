package com.user.web.userweb.validation;

import java.util.Set;

public class PasswordChecker {

	Set<Character> _specialCharacters;
	int _minLength;

	public PasswordChecker(Set<Character> specialCharacters, Integer minLength) {
		_specialCharacters = specialCharacters;
		_minLength = minLength;
	}

	public Boolean validate(String password) {
		return validateLength(password) && validateUpperCase(password) && validateSpecialSymbols(password);
	}

	private Boolean validateLength(String password) {
		if (password.length() >= _minLength) {
			return true;
		}
		return false;
	}

	private Boolean validateUpperCase(String password) {
		for (char symbol : password.toCharArray()) {
			if (Character.isUpperCase(symbol)) {
				return true;
			}
		}
		return false;
	}

	private boolean validateSpecialSymbols(String password) {
		
		String specialCharacters = _specialCharacters.toString();
		
		for (char symbol : password.toCharArray()) {
			if (!Character.isLetterOrDigit(symbol) && specialCharacters.contains(String.valueOf(symbol))) {
				return true;
			}
		}
		return false;
	}
	
	private void addSpecialCharacters(Character character) {
		_specialCharacters.add(character);
	}
}
