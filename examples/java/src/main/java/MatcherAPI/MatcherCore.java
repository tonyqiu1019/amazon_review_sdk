package MatcherAPI;

import Analyzer.DocAnalyzer;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import structures.TokenizeResult;
import structures._Corpus;
import structures._Doc;
import structures._SparseFeature;
import structures._Stn;
import structures._stat;
import topicmodels.markovmodel.HTMM;
import topicmodels.pLSA.pLSA;
import utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

public class MatcherCore {
	public MatcherCore() throws InvalidFormatException, FileNotFoundException, IOException {
		analyzer = new DocAnalyzer("./data/Model/en-token.bin", 
				"./data/Model/en-sent.bin", 
				"./data/Model/en-pos-maxent.bin", 5, null, 1, 5);
		analyzer.LoadStopwords("./data/Model/stopwords.dat");
		analyzer.LoadDirectory("./data/amazon/laptops", ".json");
		analyzer.setFeatureValues("TF", 0);
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./data/htmm.ser"));
		try {
			model = (HTMM) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ois.close();
		
		if (analyzer == null || model == null) {
			System.out.println("Error processing inference model");
			System.exit(-1);
		}
	}
	
	public _Doc inference(String content, String asin, String reviewId) {
		analyzer.setInferenceReviewContent(content);
		analyzer.LoadJsonDoc("./data/2.json");
		_Doc doc = analyzer.getProcessedDoc();
		if (doc == null) return null;
		double score = model.inference(doc);
		return doc;
	}
	
	public static final String[] topics = {
		"Screen Size",
		"Graphics Coprocessor",
		"Processor",
		"RAM",
		"Operating System",
		"Hard Drive",
		"Number of USB 3.0 Ports",
		"Average Battery Life (in hours)",
		"Brand Name",
		"Wireless Type",
		"",
		"Operating System",
		"",
		"",
		""
	};
	
	private DocAnalyzer analyzer;
	private HTMM model;
	
}
