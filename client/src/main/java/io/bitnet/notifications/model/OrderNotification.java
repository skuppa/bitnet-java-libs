
package io.bitnet.notifications.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import io.bitnet.model.payment.order.Order;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "type",
    "subscriptionId",
    "order"
})
public class OrderNotification {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    @NotNull
    private OrderNotification.Type type;
    /**
     * Unique identifier used for all key entities within the platform
     * (Required)
     * 
     */
    @JsonProperty("subscriptionId")
    @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")
    @NotNull
    private String subscriptionId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("order")
    @NotNull
    private Order order;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public OrderNotification.Type getType() {
        return type;
    }

    /**
     * 
     * (Required)
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(OrderNotification.Type type) {
        this.type = type;
    }

    public OrderNotification withType(OrderNotification.Type type) {
        this.type = type;
        return this;
    }

    /**
     * Unique identifier used for all key entities within the platform
     * (Required)
     * 
     * @return
     *     The subscriptionId
     */
    @JsonProperty("subscriptionId")
    public String getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * Unique identifier used for all key entities within the platform
     * (Required)
     * 
     * @param subscriptionId
     *     The subscriptionId
     */
    @JsonProperty("subscriptionId")
    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public OrderNotification withSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The order
     */
    @JsonProperty("order")
    public Order getOrder() {
        return order;
    }

    /**
     * 
     * (Required)
     * 
     * @param order
     *     The order
     */
    @JsonProperty("order")
    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderNotification withOrder(Order order) {
        this.order = order;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(type).append(subscriptionId).append(order).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OrderNotification) == false) {
            return false;
        }
        OrderNotification rhs = ((OrderNotification) other);
        return new EqualsBuilder().append(type, rhs.type).append(subscriptionId, rhs.subscriptionId).append(order, rhs.order).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum Type {

        ORDER_NOTIFICATION("order.notification");
        private final String value;
        private static Map<String, OrderNotification.Type> constants = new HashMap<String, OrderNotification.Type>();

        static {
            for (OrderNotification.Type c: values()) {
                constants.put(c.value, c);
            }
        }

        private Type(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static OrderNotification.Type fromValue(String value) {
            OrderNotification.Type constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("type", type).append("subscriptionId", subscriptionId).append("order", order).toString();
    }
}
