package com.arif.cabex.helper;

import android.util.Patterns;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class CommonOperationHelper {

	public static boolean isValidEmail(String email){
		return  Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	public static PhoneValidity isValidPhoneNumber(String mobNumber, String countryCode) {
		PhoneValidity phoneNumberValidate = new PhoneValidity();
		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		Phonenumber.PhoneNumber phoneNumber;
		PhoneNumberUtil.PhoneNumberType isMobile = null;
		boolean isValid = false;
		try {
			String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
			phoneNumber = phoneNumberUtil.parse(mobNumber, isoCode);
			isValid = phoneNumberUtil.isValidNumber(phoneNumber);
			isMobile = phoneNumberUtil.getNumberType(phoneNumber);
			phoneNumberValidate.setCode(String.valueOf(phoneNumber.getCountryCode()));
			phoneNumberValidate.setPhone(String.valueOf(phoneNumber.getNationalNumber()));
			phoneNumberValidate.setValid(false);

		} catch (NumberParseException | NullPointerException | NumberFormatException e) {
			e.printStackTrace();
		}
		if (isValid && (PhoneNumberUtil.PhoneNumberType.MOBILE == isMobile ||
				PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE == isMobile)) {
			phoneNumberValidate.setValid(true);
		}

		return phoneNumberValidate;
	}

}
