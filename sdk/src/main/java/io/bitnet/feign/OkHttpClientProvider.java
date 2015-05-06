/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.feign;

import com.squareup.okhttp.OkHttpClient;

import javax.net.ssl.*;
import java.security.cert.CertificateException;

/**
 * Provider for OkHttpClients.
 */
public class OkHttpClientProvider {

    /**
     * Get default okHttpClient.
     *
     * @return okHttpClient
     */
    public static feign.okhttp.OkHttpClient okHttpClient() {
        return new feign.okhttp.OkHttpClient();
    }

    /**
     * Get okHttpClient configured to trust self signed ssl certificates.
     *
     * @return trusting okHttpClient
     */
    public static feign.okhttp.OkHttpClient unsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return new feign.okhttp.OkHttpClient(okHttpClient);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
