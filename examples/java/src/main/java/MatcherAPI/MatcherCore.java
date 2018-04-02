package MatcherAPI;

import Analyzer.DocAnalyzer;
import opennlp.tools.util.InvalidFormatException;
import structures._Corpus;
import structures._Doc;
import topicmodels.markovmodel.HTMM;
import topicmodels.pLSA.pLSA;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MatcherCore {
	public MatcherCore() throws FileNotFoundException, IOException {
		analyzer = new DocAnalyzer("./data/Model/en-token.bin", 
				"./data/Model/en-sent.bin", 
				"./data/Model/en-pos-maxent.bin", 5, null, 1, 5);
		analyzer.LoadStopwords("./data/Model/stopwords.dat");
		analyzer.LoadDirectory("./data/amazon/laptops", ".json");
		analyzer.setFeatureValues("TF", 0);
		
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./data/htmm.ser"));
			model = (HTMM) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		_Corpus c = analyzer.getCorpus();
		model = new HTMM(50, 1e-9, 1.0+1e-3, c, 15, 1.0+1e-2);
		model.setSentiAspectPrior(false);
		model.LoadPrior("./data/Model/laptops_bootstrapping_test.dat", 5.0);
		model.EMonCorpus();
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./data/htmm2.ser"));
			oos.writeObject(model);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
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
		model.inference(doc);
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
	public static final double threshold = 0.1;
	
	private DocAnalyzer analyzer;
	private HTMM model;
}
