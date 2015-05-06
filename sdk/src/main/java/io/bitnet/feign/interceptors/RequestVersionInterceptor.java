/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.feign.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Manifest;

/**
 * Request interceptor which adds a user agent header indicating the version of sdk being used.
 */
public class RequestVersionInterceptor implements RequestInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(RequestVersionInterceptor.class);
    private static final String sdkVersion = getSdkVersion();
    private static final String sdkTitle = "bitnet-java-sdk";

    @Override
    public void apply(RequestTemplate template) {
        template.header("User-Agent", sdkVersion);
    }

    private static String getSdkVersion() {
        String sdkVersion = "Unknown";
        try {
            Enumeration<URL> resources = RequestVersionInterceptor.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                Manifest manifest = new Manifest(resources.nextElement().openStream());
                String title = manifest.getMainAttributes().getValue("Implementation-Title");
                String version = manifest.getMainAttributes().getValue("Implementation-Version");
                if (title != null && title.equals(sdkTitle) && version != null) {
                    sdkVersion = title + "@" + version;
                }
            }
        } catch (IOException e) {
            LOG.error("Unable to determine SDK version.");
        }
        LOG.debug("Using {}.", sdkVersion);
        return sdkVersion;
    }
}
