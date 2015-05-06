/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Test credentials.
 */
public class TestCredentials {
    private static Properties localIntegrationProperties;
    private static Properties globalIntegrationProperties;

    public static String testClientId() {
        return getProperty("clientId");
    }

    public static String testSecret() {
        return getProperty("secret");
    }

    public static String internalClientId() {
        return getProperty("internalClientId");
    }

    public static String internalSecret() {
        return getProperty("internalSecret");
    }

    public static String testAccountId() {
        return getProperty("accountId");
    }

    public static String testEnvironment() {
        return getProperty("environment");
    }


    private static Properties getIntegrationProperties(String directory) {
        String integrationPropertiesFile = "/integrationTest.properties";

        try (InputStream input = new FileInputStream(directory + integrationPropertiesFile)) {
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException e) {
            System.out.println("Issue loading: " + directory + integrationPropertiesFile);
        }
        return null;
    }

    private static String getProperty(String propertyKey) {
        loadProperties();
        if (localIntegrationProperties != null && StringUtils.isNotBlank(localIntegrationProperties.getProperty(propertyKey))) {
            return localIntegrationProperties.getProperty(propertyKey);
        } else if (globalIntegrationProperties != null && StringUtils.isNotBlank(globalIntegrationProperties.getProperty(propertyKey))) {
            return globalIntegrationProperties.getProperty(propertyKey);
        }
        throw new RuntimeException("Unable to access integration test configuration - please look at project readme and add a integration test properties file.");
    }

    private static void loadProperties() {
        if (localIntegrationProperties == null || globalIntegrationProperties == null) {
            localIntegrationProperties = getIntegrationProperties(System.getProperty("user.dir"));
            globalIntegrationProperties = getIntegrationProperties(System.getProperty("user.home") + "/.bitnet-java-libs");
        }
    }
}
