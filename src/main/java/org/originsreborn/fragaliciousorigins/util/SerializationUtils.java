package org.originsreborn.fragaliciousorigins.util;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;

public class SerializationUtils {

    public static String serializeHashMapToString(HashMap<String, Serializable> hashMap) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(hashMap);
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        }
    }

    public static HashMap<String, Serializable> unserializeStringToHashMap(String input) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(input);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (HashMap<String, Serializable>) objectInputStream.readObject();
        }
    }
}

