/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.core.notifications;

/**
 * Subscription credentials for notifications.
 */
public class NotificationSubscription {
    private String keyId;
    private String secret;

    private NotificationSubscription(String keyId, String secret) {
        this.keyId = keyId;
        this.secret = secret;
    }

    /**
     * Create new invoice notification subscription.
     *
     * @param keyId subscription key id
     * @param secret subscription secret
     * @return new invoice notification subscription
     */
    public static NotificationSubscription invoiceSubscriptionCredentials(String keyId, String secret){
        return new NotificationSubscription(keyId, secret);
    }

    /**
     * Create new order notification subscription.
     *
     * @param keyId subscription key id
     * @param secret subscription secret
     * @return new order notification subscription
     */

    public static NotificationSubscription orderSubscriptionCredentials(String keyId, String secret){
        return new NotificationSubscription(keyId, secret);
    }

    /**
     * Create new refund notification subscription.
     *
     * @param keyId subscription key id
     * @param secret subscription secret
     * @return new refund notification subscription
     */

    public static NotificationSubscription refundSubscriptionCredentials(String keyId, String secret){
        return new NotificationSubscription(keyId, secret);
    }

    public String getKeyId() {
        return keyId;
    }

    public String getSecret() {
        return secret;
    }
}
