package utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class JsonPathUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode readJsonFile(String filePath) throws IOException {
        return mapper.readTree(new File(filePath));
    }

    public static void updateFieldByPath(JsonNode jsonNode, String path, String newValue) {
        String[] parts = path.split("\\.");
        JsonNode currentNode = jsonNode;

        for (int i = 0; i < parts.length - 1; i++) {
            String part = parts[i];
            if (part.contains("[")) {
                String arrayName = part.substring(0, part.indexOf("["));
                int index = Integer.parseInt(part.substring(part.indexOf("[") + 1, part.indexOf("]")));
                if (currentNode.has(arrayName) && currentNode.get(arrayName).isArray()) {
                    ArrayNode arrayNode = (ArrayNode) currentNode.get(arrayName);
                    if (index < arrayNode.size()) {
                        currentNode = arrayNode.get(index);
                    } else {
                        throw new IllegalArgumentException("Array index out of bounds: " + part);
                    }
                } else {
                    throw new IllegalArgumentException("Invalid array path: " + part);
                }
            } else {
                if (currentNode.has(part)) {
                    currentNode = currentNode.get(part);
                } else {
                    throw new IllegalArgumentException("Invalid path: " + part);
                }
            }
        }

        String finalField = parts[parts.length - 1];
        if (currentNode instanceof ObjectNode) {
            ((ObjectNode) currentNode).put(finalField, newValue);
        } else {
            throw new IllegalArgumentException("Cannot update non-object node: " + finalField);
        }
    }

    public static String getValueByPath(JsonNode jsonNode, String path) {
        String[] parts = path.split("\\.");
        JsonNode currentNode = jsonNode;

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.contains("[")) {
                String arrayName = part.substring(0, part.indexOf("["));
                int index = Integer.parseInt(part.substring(part.indexOf("[") + 1, part.indexOf("]")));
                if (currentNode.has(arrayName) && currentNode.get(arrayName).isArray()) {
                    ArrayNode arrayNode = (ArrayNode) currentNode.get(arrayName);
                    if (index < arrayNode.size()) {
                        currentNode = arrayNode.get(index);
                    } else {
                        throw new IllegalArgumentException("Array index out of bounds: " + part);
                    }
                } else {
                    throw new IllegalArgumentException("Invalid array path: " + part);
                }
            } else {
                if (currentNode.has(part)) {
                    currentNode = currentNode.get(part);
                } else {
                    throw new IllegalArgumentException("Invalid path: " + part);
                }
            }
        }

        return currentNode.asText();
    }

    public static void writeJsonFile(String filePath, JsonNode jsonNode) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), jsonNode);
    }
}