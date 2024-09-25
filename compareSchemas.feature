Feature: Compare YAML and Avro schemas

  Scenario: Compare schemas for matching fields and types
    Given the YAML spec file "src/main/resources/spec.yaml" and Avro schema file "src/main/resources/schema.avsc"
    Then the schemas should be compared successfully
