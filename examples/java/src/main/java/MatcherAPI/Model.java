package MatcherAPI;

public class Model {
	
	public class Property {
		public int id;
		public String prod;
		public String topic;
		public String text_content;
		public String xpath;
	}
	
	public class Review {
		public String id;
		public String content;
		public String prod;
		public int page;
	}
	
	public class Relationship {
		public String best_sentence;
		public int related_property_id;
		public String related_review_id;
		public double sentiment;
	}
	
}
