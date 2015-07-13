package com.nlp.pro;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;
import org.jsoup.Jsoup;

public class NLPUtils {
	public static CharArraySet _getStopWordSet() {
		String[] stopWords = NLPConstant.STOPWORDS;
		CharArraySet stopWordsSet = CharArraySet.copy(Version.LUCENE_40, StandardAnalyzer.STOP_WORDS_SET);
		for (int i = 0; i < stopWords.length; i++) {
			stopWordsSet.add(stopWords[i]);

		}
		return stopWordsSet;
	}

	public static int _fileCount(String directoryPath) throws IOException {
		return new File(directoryPath).list().length;

	}

	public static StringBuilder _getFileData(File file) throws FileNotFoundException {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(file);
		StringBuilder words = new StringBuilder();
		while (sc.hasNext()) {
			words.append(sc.next() + " ");
		}
		return words;

	}

	public static boolean _regexMatch(String _string, String regexName) {
		// TODO Auto-generated method stub
		return Pattern.compile(regexName).matcher(_string).matches();
	}

	public static String _htmlParser(String text) {
		return StringEscapeUtils.escapeHtml(Jsoup.parse(text).text());
	}
}
