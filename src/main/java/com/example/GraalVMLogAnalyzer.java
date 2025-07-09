package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GraalVMLogAnalyzer implements RequestHandler<S3EventInput, String> {

    @Override
    public String handleRequest(S3EventInput input, Context context) {
        LambdaLogger logger = context.getLogger();

        String bucketName = input.getBucket();
        String key = input.getKey();

        logger.log("Reading file from bucket: " + bucketName + ", key: " + key);

        try (S3Client s3 = S3Client.create()) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            InputStream inputStream = s3.getObject(getObjectRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            int errorCount = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                // Simple log analysis: count number of ERROR lines
                if (line.contains("ERROR")) {
                    errorCount++;
                }
            }

            logger.log("Total ERROR lines: " + errorCount);
            return "Log analysis completed. ERROR count: " + errorCount;

        } catch (Exception e) {
            logger.log("Exception while processing: " + e.getMessage());
            return "Error processing the log file.";
        }
    }
}
