package com.user.web.userweb.validation;

import java.util.Arrays;

public class EmailValidator {

	public Boolean validate(String email) {

		return validateEmty(email) && validateAtSign(email) && validateSymbols(email) && validateDomain(email)
				&& validateTLD(email);
	}

	private boolean validateEmty(String email) {
		if (email != null && !email.isBlank()) {
			return true;
		}
		return false;
	}

	private boolean validateAtSign(String email) {

		int count = 0;
		for (int i = 0; i < email.length(); i++) {
			if (email.charAt(i) == '@')
				count++;
		}
		return count == 1;
	}

	private boolean validateSymbols(String email) {
		var emailLocalPathString = email.substring(0, email.indexOf("@"));
		var emailLocalPathChar = emailLocalPathString.toCharArray();
		String validCharacters = "!#$%&'*+-/=?^_`{|}~";

		for (char symbol : emailLocalPathChar) {
			if (!Character.isLetterOrDigit(symbol) && symbol != '.'
					&& !validCharacters.contains(String.valueOf(symbol)))
				return false;
		}

		return 0 < emailLocalPathChar.length && emailLocalPathChar.length < 64 && emailLocalPathChar[0] != '.'
				&& emailLocalPathChar[emailLocalPathChar.length - 1] != '.';
	}

	private boolean validateDomain(String email) {

		var emailDomainString = email.substring(email.indexOf("@") + 1);
		var emailDomain = emailDomainString.split("\\.");

		if (emailDomainString.contains("--") || emailDomainString.isBlank() || emailDomainString == null
				|| emailDomainString.length() > 254) {
			return false;
		}

		for (int i = 0; i < emailDomain.length - 1; i++) {
			var hostname = emailDomain[i].toCharArray();

			for (var symbol : hostname) {
				if (!Character.isLetterOrDigit(symbol) && symbol != '.' && symbol != '-')
					return false;
			}

			if (hostname[0] == '-' || hostname[hostname.length - 1] == '-') {
				return false;
			}
		}

		return true;
	}

	private boolean validateTLD(String email) {
		var tld = email.substring(email.lastIndexOf(".") + 1);
		String[] invalidTLD = " !\"#$%&'()*+,/:;<=>?@[\\]^_`{|}~123456789".split("");

		String[] tlds = tld.split("\\.");
		for (var symbol : tlds) {
			if (symbol.length() < 2 || symbol.length() > 63) {
				return false;
			}
			boolean hasIllegalChars = Arrays.stream(invalidTLD).anyMatch(symbol::contains);
			if (hasIllegalChars) {
				return false;
			}
		}
		return true;
	}
}
