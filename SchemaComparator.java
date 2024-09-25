package com.example;

import java.util.Map;
import java.util.Set;

public class SchemaComparator {

    // Compare fields and types between the YAML spec and Avro schema
    public static void compareSchemas(Map<String, String> yamlFields, Map<String, String> avroFields) {
        Set<String> yamlFieldNames = yamlFields.keySet();
        Set<String> avroFieldNames = avroFields.keySet();

        // Fields present in YAML but not in Avro
        Set<String> missingInAvro = Set.copyOf(yamlFieldNames);
        missingInAvro.removeAll(avroFieldNames);
        System.out.println("Fields missing in Avro schema:");
        missingInAvro.forEach(field -> System.out.println("- " + field + " (YAML type: " + yamlFields.get(field) + ")"));

        // Fields present in Avro but not in YAML
        Set<String> missingInYaml = Set.copyOf(avroFieldNames);
        missingInYaml.removeAll(yamlFieldNames);
        System.out.println("\nFields missing in YAML spec:");
        missingInYaml.forEach(field -> System.out.println("- " + field + " (Avro type: " + avroFields.get(field) + ")"));

        // Check for type mismatches
        System.out.println("\nField type mismatches:");
        Set<String> commonFields = Set.copyOf(yamlFieldNames);
        commonFields.retainAll(avroFieldNames);
        commonFields.forEach(field -> {
            if (!yamlFields.get(field).equals(avroFields.get(field))) {
                System.out.println("- " + field + ": YAML type is " + yamlFields.get(field) + ", Avro type is " + avroFields.get(field));
            }
        });
    }
}
