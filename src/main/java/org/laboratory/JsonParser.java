package org.laboratory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonParser {

    public static List<Order> readOrdersFromFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath), new TypeReference<List<Order>>() {});
        } catch (IOException e) {
            System.err.println("Erreur de lecture JSON : " + e.getMessage());
            return null;
        }
    }
}
