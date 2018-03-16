package MatcherAPI;

import static spark.Spark.*;
import static MatcherAPI.JsonUtil.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

import mains.TopicModelMain;
import java.io.IOException;
import java.text.ParseException;

public class Main
{
    public static void main( String[] args )
            throws IOException, ParseException
    {
        TopicModelMain.main(args);

        post("test", (req, res) -> {
            res.type("application/json");

            JsonParser parser = new JsonParser();
            JsonElement j = parser.parse(req.body());
            JsonObject jO = j.getAsJsonObject();
            return jO.get("a");
        });

        get("hello", (req,res) -> {
            res.type("application/json");
            return "hello";
        }, json());
    }
}
