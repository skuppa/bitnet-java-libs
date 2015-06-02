package io.bitnet.notifications.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing notification event types.
 */
public enum NotificationEventTypes {
    INVOICE_STATE_CHANGED("invoice.stateChanged"),
    INVOICE_PAYMENT_RECEIVED("invoice.paymentReceived"),
    INVOICE_EXPIRED("invoice.expired"),
    ORDER_STATE_CHANGED("order.stateChanged"),
    REFUND_STATE_CHANGED("refund.stateChanged");

    private final String value;
    private static Map<String, NotificationEventTypes> constants = new HashMap<String, NotificationEventTypes>();

    static {
        for (NotificationEventTypes c : values()) {
            constants.put(c.value, c);
        }
    }

    private NotificationEventTypes(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }

    @JsonCreator
    public static NotificationEventTypes fromValue(String value) {
        NotificationEventTypes constant = constants.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
