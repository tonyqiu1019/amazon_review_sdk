package MatcherAPI;

import com.google.gson.*;
import spark.ResponseTransformer;

public class JsonUtil {
  public static String toJson(Object object) {
    return new Gson().toJson(object);
  }
 
  public static ResponseTransformer json() {
    return JsonUtil::toJson;
  }
}
