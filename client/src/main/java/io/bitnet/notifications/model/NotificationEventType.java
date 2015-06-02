package io.bitnet.notifications.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing notification event types.
 */
public enum NotificationEventType {
    INVOICE_STATE_CHANGED("invoice.stateChanged"),
    INVOICE_PAYMENT_RECEIVED("invoice.paymentReceived"),
    INVOICE_EXPIRED("invoice.expired"),
    ORDER_STATE_CHANGED("order.stateChanged"),
    REFUND_STATE_CHANGED("refund.stateChanged");

    private final String value;
    private static Map<String, NotificationEventType> constants = new HashMap<String, NotificationEventType>();

    static {
        for (NotificationEventType c : values()) {
            constants.put(c.value, c);
        }
    }

    private NotificationEventType(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }

    @JsonCreator
    public static NotificationEventType fromValue(String value) {
        NotificationEventType constant = constants.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
