package MatcherAPI;

import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import MatcherAPI.Model.*;

import structures._Doc;
import structures._Stn;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main
{
	public static void main( String[] args )
	        throws IOException, ParseException
	{
		MatcherCore mc = new MatcherCore();
		Gson gson = new Gson();
		Model md = new Model();
		
		post("test", (req, res) -> {			
			try {
				res.type("application/json");
				
				JsonObject jObject = gson.fromJson(req.body(), JsonObject.class);
				JsonArray reviews = jObject.getAsJsonArray("reviews");
				JsonArray properties = jObject.getAsJsonArray("properties");
				
				HashMap<String, Integer> map = new HashMap<>();
				for (JsonElement j : properties) {
					Property p = gson.fromJson(j, Property.class);
					map.put(p.topic, p.id);
				}
				
				ArrayList<Relationship> ret = new ArrayList<>();
				for (JsonElement j : reviews) {
					Review r = gson.fromJson(j, Review.class);
					_Doc doc = mc.inference(r.content, r.prod, r.id);
					if (doc == null) continue;
					
					
					for (_Stn s : doc.getSentences()) {
						String topicName = MatcherCore.topics[s.getTopic() % 15];
						if (topicName.equals("") || !map.containsKey(topicName)) continue;
						
						Relationship rel = md.new Relationship();
						rel.best_sentence = s.getRawSentence();
						rel.related_review_id = r.id;
						rel.related_property_id = map.get(topicName);
						rel.sentiment = 0.0;
						ret.add(rel);
					}
				}
				
				String rels = gson.toJson(ret.toArray(new Relationship[0]));
				return String.format("{\"relationships\": %s}", rels);
				
			} catch (Exception e) {
				e.printStackTrace();
				res.type("");
				throw e;
			}
		});
	}
}
