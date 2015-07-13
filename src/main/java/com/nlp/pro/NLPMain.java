package com.nlp.pro;

import java.io.File;
import java.io.IOException;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class NLPMain {
	private int fileCount = 0;
	NLPTokenizer tokenizer = null;
	private File file;
	static NounSynset nounSynset;
	static NounSynset[] hyponyms;

	public static void main(String[] args) throws IOException {
		// new NLPMain().start();
		
		System.setProperty("wordnet.database.dir", "/dict/");
		WordNetDatabase database = WordNetDatabase.getFileInstance();
		Synset[] synsets = database.getSynsets("fly", SynsetType.NOUN);
		for (int i = 0; i < synsets.length; i++) {
			nounSynset = (NounSynset) (synsets[i]);
			hyponyms = nounSynset.getHyponyms();
			System.err.println(nounSynset.getWordForms()[0] + ": " + nounSynset.getDefinition() + ") has "
					+ hyponyms.length + " hyponyms");

		}
		for (Synset synset : hyponyms) {
			System.out.println(synset);
		}
	}

	void start() {
		// TODO Auto-generated method stub
		try {
			fileCount = NLPUtils._fileCount(NLPConstant.PARENTDIR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < fileCount - 2; i++) {
			if (i == 1) {
				break;
			}
			String string = "";
			try {
				file = new File(NLPConstant.PARENTDIR + "/" + i + "_0.txt");
				string = NLPUtils._getFileData(file).toString();
			} catch (Exception e) {
				// TODO: handle exception
			}
			tokenizer = new NLPTokenizer(string, i + "_0.txt");
			try {
				tokenizer.wordTokenized();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
	}

}
