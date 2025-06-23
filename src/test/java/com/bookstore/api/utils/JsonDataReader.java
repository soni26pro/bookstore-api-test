package com.bookstore.api.utils;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataReader {

    public static JsonNode readJsonFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream input = JsonDataReader.class.getClassLoader().getResourceAsStream(filePath)) {
            if (input == null) {
                System.err.println("Sorry, unable to find " + filePath);
                return null;
            }
            return objectMapper.readTree(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
