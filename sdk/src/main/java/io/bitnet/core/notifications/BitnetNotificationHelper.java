/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.core.notifications;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import io.bitnet.core.notifications.httpsig.BitnetRequestContent;
import io.bitnet.core.notifications.httpsig.BitnetVerifier;
import io.bitnet.notifications.model.*;
import net.adamcin.httpsig.api.*;
import net.adamcin.httpsig.hmac.HmacKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Provides assistance for verifying and parsing Bitnet notifications.
 */
public class BitnetNotificationHelper {
    private static final Logger LOG = LoggerFactory.getLogger(BitnetNotificationHelper.class);
    public static final String ALGORITHM = "SHA-256";
    private final Challenge challenge = new Challenge("", Arrays.asList("date", "digest"), Arrays.asList(Algorithm.HMAC_SHA256));
    private final List<HmacKey> keys = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    public BitnetNotificationHelper(NotificationSubscription... subscriptions) {
        for (NotificationSubscription subscription : subscriptions) {
            this.keys.add(new HmacKey(subscription.getKeyId(), subscription.getSecret()));
        }
    }

    /**
     * Check if notification is valid and has a verified signature.
     *
     * @param headers all headers from notification request
     * @param body    body of notification request
     * @return true is valid and has a verified signature, otherwise false.
     */
    public boolean isVerifiedNotification(Map<String, String> headers, String body) {
        headers = caseInsensitiveMap(headers);
        return isDigestValid(body, headers.get("digest"))
                && verifier().verify(challenge, requestContent(headers), Authorization.parse(headers.get("Authorization")));
    }

    /**
     * Check if notification is valid and has a verified signature.
     *
     * @param headers all headers from notification request
     * @param body    body of notification request
     * @return reason why notification wasn't verifiable.
     */
    public String rejectNotificationReason(Map<String, String> headers, String body) {
        headers = caseInsensitiveMap(headers);

        if (!isDigestValid(body, headers.get("digest"))) {
            return "Invalid digest for request body.";
        }

        VerifyResult result = verifier().verifyWithResult(challenge, requestContent(headers), Authorization.parse(headers.get("Authorization")));

        if (result != VerifyResult.SUCCESS) {
            return "Signature verification failed due to: " + result.toString();
        }

        return "";
    }


    /**
     * Get invoice notification from request body.
     *
     * @param body request body
     * @return invoice notification
     */
    public Notification<InvoiceNotification> getInvoiceNotification(String body) {
        return parseNotification(body, InvoiceNotification.class);
    }

    /**
     * Get order notification from request body.
     *
     * @param body request body
     * @return order notification
     */

    public Notification<OrderNotification> getOrderNotification(String body) {
        return parseNotification(body, OrderNotification.class);
    }

    /**
     * Get refund notification from request body.
     *
     * @param body request body
     * @return refund notification
     */

    public Notification<RefundNotification> getRefundNotification(String body) {
        return parseNotification(body, RefundNotification.class);
    }

    /**
     * Check that notification is an invoice notification.
     *
     * @param body request body
     * @return true if body if invoice notification.
     */
    public boolean isInvoiceNotification(String body) {
        return isNotificationOfType(body, InvoiceNotification.Type.INVOICE_NOTIFICATION.toString());
    }

    /**
     * Check that notification is an order notification.
     *
     * @param body request body
     * @return true if body if refund notification.
     */
    public boolean isRefundNotification(String body) {
        return isNotificationOfType(body, RefundNotification.Type.REFUND_NOTIFICATION.toString());
    }

    /**
     * Check that notification is an invoice notification.
     *
     * @param body request body
     * @return true if body if order notification.
     */
    public boolean isOrderNotification(String body) {
        return isNotificationOfType(body, OrderNotification.Type.ORDER_NOTIFICATION.toString());
    }


    /**
     * Get notification from request body.
     *
     * @param body request body
     * @param type notification class
     * @param <T>  notification type
     * @return true is body matches notification type.
     */
    public <T> Notification<T> parseNotification(String body, Class<T> type) {
        try {
            return mapper.readValue(body, mapper.getTypeFactory().constructParametrizedType(Notification.class, Notification.class, type));
        } catch (IOException e) {
            LOG.warn("Issue parsing notification event {} - {}", body, e.getMessage());
            return null;
        } catch (Exception e) {
            LOG.warn("Issue parsing notification event {} - {}", body, e.getMessage());
            return null;
        }
    }

    /**
     * Check if request body contains notification of type.
     *
     * @param body request body
     * @param type type of notification
     * @return true if body contains notification type.
     */
    public boolean isNotificationOfType(String body, String type) {
        try {
            final JsonNode jsonNode = mapper.readTree(body);
            return jsonNode.path("notification").get("type").asText().equals(type);
        } catch (IOException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Get event type of notification from request body.
     *
     * @param body request body
     * @return notification event type
     */
    public NotificationEventType getNotificationEventType(String body) {
        try {
            final JsonNode jsonNode = mapper.readTree(body);
            return NotificationEventType.fromValue(jsonNode.get("event").asText());
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> caseInsensitiveMap(Map<String, String> requestHeaders) {
        Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        headers.putAll(requestHeaders);
        return headers;
    }

    private BitnetRequestContent requestContent(Map<String, String> headers) {
        BitnetRequestContent.Builder requestContentBuilder = new BitnetRequestContent.Builder();
        requestContentBuilder.addHeader("date", headers.get("date"));
        requestContentBuilder.addHeader("digest", headers.get("digest"));
        return requestContentBuilder.build();
    }

    private BitnetVerifier verifier() {
        return new BitnetVerifier(
                new DefaultKeychain(keys), Constants.DEFAULT_KEY_IDENTIFIER, -1L);
    }


    private boolean isDigestValid(String payload, String digestHeader) {
        return digestHeader.equals(ALGORITHM + "=" + Hashing.sha256().hashString(payload, Charsets.UTF_8).toString());
    }
}
