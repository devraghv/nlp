package com.nlp.pro;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NLPTokenizer {
	private NLPTokenFilter nlpTokenFilter;
	private String actual;
	private String _inprocess;

	public NLPTokenizer() {
		// TODO Auto-generated constructor stub
	}

	public NLPTokenizer(String actual) {
		// TODO Auto-generated constructor stub
		this.actual = actual;
	}

	public NLPTokenizer(String actual, String _inprocess) {
		// TODO Auto-generated constructor stub
		this.actual = actual;
		this._inprocess = _inprocess;
	}

	public void wordTokenized() throws IOException {
		// removing stop words
		nlpTokenFilter = new NLPTokenFilter();
		StringBuilder builder = NLPStopwords.removeStopWords(actual);
		File tokenizeFile = new File(NLPConstant.TOKENIZEPARENTDIR + "/tokenize" + _inprocess);
		if (!tokenizeFile.exists()) {
			tokenizeFile.createNewFile();
		}

		builder = nlpTokenFilter.tokeFilter(actual, builder);
		BufferedWriter writer = new BufferedWriter(new FileWriter(tokenizeFile));
		writer.write(builder.toString());
		// do stuff
		writer.close();
		System.out.println("Write in a " + tokenizeFile.getName());

	}

}
