package com.nlp.pro;

public class NLPTokenFilter {
	private StringBuilder filterTokens = null;

	/**
	 * Tokenize String <br>
	 * (a) <b>Date time parser</b> After Tokenize word if date time format
	 * create multiple token , create one token. Example :(1)
	 * "24/Jan/2015 12:24:60 AM" (2) "24/Jan/2015 12:56 AM" (3)
	 * "24/Jan/2015 This is Test". In first example date and time tokenized in
	 * (24/Jan/2015 , 12 , 24 , 60 , AM ) , convert one token(24/Jan/2015
	 * 12:24:60 AM ),second example tokenized in (24/Jan/2015 , 12 , 56 , AM) ,
	 * convert one token ((24/Jan/2015 12:56 AM )) but in third there is no time
	 * instance so token is (24/Jan/2015) <br>(b)<b>Currency conversion</b>
	 * 
	 * @param actual
	 * @param tokenize
	 * @return {@link StringBuilder}
	 */
	public StringBuilder tokeFilter(String actual, StringBuilder tokenize) {
		filterTokens = new StringBuilder();
		String[] tokens = tokenize.toString().split("\n");
		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i].replaceAll("^[^a-zA-Z0-9]+", "")
					.replaceAll("[^a-zA-Z0-9]+$", "");
			String time = "";

			if (NLPUtils._regexMatch(token, NLPConstant.DATEREGEX)) {
				try {
					time = tokens[i + 1];
					if (NLPUtils._regexMatch(time, NLPConstant.TIMEREGEX)) {
						i += 1;
						String dateTime = token + " " + time;
						if (NLPUtils._regexMatch(tokens[i + 1],
								NLPConstant.MERIDIEM)) {
							dateTime += " " + tokens[i + 1];
							i += 1;
						}

						filterTokens.append(dateTime + "\n");
					} else {
						filterTokens.append(token + "\n");
					}

				} catch (Exception e) {
					System.out
							.println("Date Conversion => No time string after date");
					filterTokens.append(token + "\n");
				}

			} else if (NLPUtils._regexMatch(token, NLPConstant.EMAILREGEX)) {
				filterTokens.append(token + "\n");

			} else if (checkIscurrency(token)) {
				System.out.println("Currency found "+token);
				try {
					String currency = Integer.parseInt(filterTokens
							.substring(
									filterTokens.substring(0,
											filterTokens.lastIndexOf("\n"))
											.lastIndexOf("\n"),
									filterTokens.length())
							.replaceAll("[^0-9]", "").trim())
							+ " " + token;
					String rest = filterTokens.substring(0, filterTokens
							.substring(0, filterTokens.lastIndexOf("\n"))
							.lastIndexOf("\n"))
							+ "\n";
					filterTokens.delete(0, tokenize.length());
					filterTokens.append(rest);
					filterTokens.append(currency + "\n");
				} catch (Exception e) {
					System.out
							.println("Currency Conversion => No currency found");
					filterTokens.append(token + "\n");

				}

			} else {
				filterTokens.append(token + "\n");

			}

		}
		return filterTokens;
	}

	public boolean checkIscurrency(String string) {
		String[] currencies = NLPConstant.CURRENCY_SYMBOL;
		for (String currency : currencies) {
			if (currency.equalsIgnoreCase(string)) {
				return true;
			}
		}
		return false;
	}
}
