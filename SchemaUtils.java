package com.example;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemaUtils {

    // Parse the spec.yaml file to extract fields and types
    public static Map<String, String> parseYamlSpec(String yamlFilePath) throws Exception {
        Map<String, String> fields = new HashMap<>();
        Yaml yaml = new Yaml();
        try (InputStream inputStream = new FileInputStream(yamlFilePath)) {
            Map<String, Object> yamlContent = yaml.load(inputStream);
            Map<String, Object> components = (Map<String, Object>) yamlContent.get("components");
            if (components != null) {
                Map<String, Object> schemas = (Map<String, Object>) components.get("schemas");
                if (schemas != null) {
                    for (Map.Entry<String, Object> entry : schemas.entrySet()) {
                        Map<String, Object> schema = (Map<String, Object>) entry.getValue();
                        Map<String, Object> properties = (Map<String, Object>) schema.get("properties");
                        if (properties != null) {
                            for (Map.Entry<String, Object> property : properties.entrySet()) {
                                String fieldName = property.getKey();
                                Map<String, Object> fieldDetails = (Map<String, Object>) property.getValue();
                                String fieldType = (String) fieldDetails.get("type");
                                fields.put(fieldName, fieldType);
                            }
                        }
                    }
                }
            }
        }
        return fields;
    }

    // Parse the Avro schema file to extract fields and types
    public static Map<String, String> parseAvroSchema(String avroFilePath) throws Exception {
        Map<String, String> fields = new HashMap<>();
        Schema schema = new Parser().parse(new FileInputStream(avroFilePath));
        List<Schema.Field> avroFields = schema.getFields();
        for (Schema.Field field : avroFields) {
            fields.put(field.name(), field.schema().getType().getName());
        }
        return fields;
    }
}
