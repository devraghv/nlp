package com.nlp.pro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ListCountry {

	public static void main(String[] args) {
		String[] locales = Locale.getISOCountries();
		Currency currency = null;
		String countrycode = "{";
		List<String> currencyi = new ArrayList<String>();
		for (String countryCode : locales) {
			Locale obj = new Locale("", countryCode);
			currency = Currency.getInstance(obj);
			if (currency != null) {
				/*
				 * System.out.println("" + obj.getCountry() + ", " +
				 * obj.getDisplayCountry()+", "+currency.getSymbol());
				 */
				if (!currencyi.contains(currency.getSymbol())) {
					countrycode += "\"" + currency.getSymbol() + "\",";
					currencyi.add(currency.getSymbol());
				}

			}

		}
		countrycode = countrycode.substring(0, countrycode.lastIndexOf(","));
		countrycode += "}";
		System.out.println("Country Code =>" + countrycode);

	}
}