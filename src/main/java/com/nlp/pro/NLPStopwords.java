package com.nlp.pro;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
import net.htmlparser.jericho.TextExtractor;

public class NLPStopwords {
	private static CharArraySet stopwordSet;

	static {
		stopwordSet = NLPUtils._getStopWordSet();

	}

	/**
	 *
	 * Remove Stop words,IP address,cuts off leading and trailing quotation
	 * marks, parentheses and punctuation already produces a reasonable
	 * performance
	 * 
	 * @param file
	 *            A file instance
	 * @return @StringBuilder
	 * @throws IOException
	 */
	static StringBuilder removeStopWords(String string) throws IOException {
		TextExtractor te = new TextExtractor(new Source(string)) {
			@Override
			public boolean excludeElement(StartTag startTag) {
				return startTag.getName() != HTMLElementName.A;
			}
		};
		string = te.toString();
		TokenStream tokenStream = new WhitespaceTokenizer(Version.LUCENE_40,
				new StringReader(string));

		StringBuilder builder = new StringBuilder();
		tokenStream = new StopFilter(Version.LUCENE_40, tokenStream, stopwordSet);
		CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
		try {
			// reset stream
			tokenStream.reset();
			while (tokenStream.incrementToken()) {
				String term = token.toString();

				String chara = term.replaceAll(NLPConstant.ACCEPT_CHARACTER, "").toString();
				if (!NLPUtils._regexMatch(chara, NLPConstant.IPREGEX) && chara != "") {
					builder.append(chara.toString() + "\n");
				}
			}
			tokenStream.close();
			return builder;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}
}