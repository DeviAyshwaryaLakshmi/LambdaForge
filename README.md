# GraalVMLogAnalyzer

GraalVMLogAnalyzer is a Java-based tool designed to analyze log files, optimized for use with GraalVM and AWS Lambda. It leverages the AWS SDK v2 for S3 integration and can be packaged as a native executable or a fat JAR for serverless deployments.

## Features

- Efficient log analysis using Java 21 features
- AWS Lambda handler support
- S3 integration for reading and writing logs
- Ready for GraalVM native image compilation

## Requirements

- Java 21 (or compatible with your setup)
- Maven 3.6+
- GraalVM for native image builds
- AWS credentials (for S3 access)

## Build

To build the fat JAR for Lambda deployment:

`mvn clean package`

The output JAR will be in the `target/` directory.

## Usage

### As a Fat Jar in Lambda Function

Deploy the generated JAR to AWS Lambda. Set the handler to:

`org.example.JavaLogAnalyzer::handleRequest`

### As a Native Image in Lambda Function

To build a native image with GraalVM:

`gu install native-image`(Optional)
`mvn package -Pnative`

Zip the bootstrap and the native image together for deployment.

`zip -r lambda.zip bootstrap native-lambda`

Deploy the resulting zip file (lambda.zip) to AWS Lambda, setting the handler to:
`org.example.JavaLogAnalyzer::handleRequest`

## Configuration

- S3 bucket and key names can be configured via environment variables or Lambda event input.
- Java version is set to 21 in the Maven build.

## Dependencies

- `aws-lambda-java-core`
- `software.amazon.awssdk:s3`

---

For more details, see the source code and comments.
