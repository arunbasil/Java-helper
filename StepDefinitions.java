package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {

    private Map<String, String> yamlFields;
    private Map<String, String> avroFields;

    @Given("the YAML spec file {string} and Avro schema file {string}")
    public void the_yaml_spec_file_and_avro_schema_file(String yamlFilePath, String avroFilePath) throws Exception {
        yamlFields = SchemaUtils.parseYamlSpec(yamlFilePath);
        avroFields = SchemaUtils.parseAvroSchema(avroFilePath);
    }

    @Then("the schemas should be compared successfully")
    public void the_schemas_should_be_compared_successfully() {
        SchemaComparator.compareSchemas(yamlFields, avroFields);
        assertTrue(!yamlFields.isEmpty() && !avroFields.isEmpty(), "Fields should not be empty");
    }
}
