package org.originsreborn.fragaliciousorigins.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class JsonUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String serializeHashMapToJsonString(HashMap<String, String> hashMap) {
        return gson.toJson(hashMap);
    }

    public static HashMap<String, String> unserializeJsonStringToHashMap(String jsonString) {
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        return gson.fromJson(jsonString, type);
    }

    // Example usage
    public static void main(String[] args) {
        // Create a sample HashMap
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");
        hashMap.put("key3", "value3");

        // Serialize the HashMap to JSON string
        String jsonString = serializeHashMapToJsonString(hashMap);

        // Print the JSON string
        System.out.println("Serialized JSON string:");
        System.out.println(jsonString);

        // Unserialize JSON string back to HashMap
        HashMap<String, String> unserializedHashMap = unserializeJsonStringToHashMap(jsonString);

        // Print the unserialized HashMap
        System.out.println("\nUnserialized HashMap:");
        for (String key : unserializedHashMap.keySet()) {
            System.out.println(key + ": " + unserializedHashMap.get(key));
        }
    }
}

