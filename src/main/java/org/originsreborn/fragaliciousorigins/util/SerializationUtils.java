package org.originsreborn.fragaliciousorigins.util;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;

public class SerializationUtils {

    public static String serializeHashMapToString(HashMap<String, Serializable> hashMap) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(hashMap);
        objectOutputStream.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    public static HashMap<String, Serializable> unserializeStringToHashMap(String input) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(input);
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
        return (HashMap<String, Serializable>) objectInputStream.readObject();
    }

    public static void main(String[] args) {
        // Create a HashMap with some test data
        HashMap<String, Serializable> hashMap = new HashMap<>();
        hashMap.put("key1", "value1");
        hashMap.put("key2", 123);
        hashMap.put("key3", true);

        try {
            // Serialize the HashMap to a String
            String serialized = serializeHashMapToString(hashMap);
            System.out.println("Serialized HashMap:");
            System.out.println(serialized);

            // Deserialize the String back to a HashMap
            HashMap<String, Serializable> deserialized = unserializeStringToHashMap(serialized);
            System.out.println("\nDeserialized HashMap:");
            for (String key : deserialized.keySet()) {
                System.out.println(key + " : " + deserialized.get(key));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

