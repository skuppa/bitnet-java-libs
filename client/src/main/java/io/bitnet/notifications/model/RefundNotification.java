
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
import io.bitnet.model.refund.refund.Refund;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "type",
    "subscriptionId",
    "refund"
})
public class RefundNotification {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    @NotNull
    private RefundNotification.Type type;
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
    @JsonProperty("refund")
    @NotNull
    private Refund refund;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public RefundNotification.Type getType() {
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
    public void setType(RefundNotification.Type type) {
        this.type = type;
    }

    public RefundNotification withType(RefundNotification.Type type) {
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

    public RefundNotification withSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The refund
     */
    @JsonProperty("refund")
    public Refund getRefund() {
        return refund;
    }

    /**
     * 
     * (Required)
     * 
     * @param refund
     *     The refund
     */
    @JsonProperty("refund")
    public void setRefund(Refund refund) {
        this.refund = refund;
    }

    public RefundNotification withRefund(Refund refund) {
        this.refund = refund;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(type).append(subscriptionId).append(refund).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RefundNotification) == false) {
            return false;
        }
        RefundNotification rhs = ((RefundNotification) other);
        return new EqualsBuilder().append(type, rhs.type).append(subscriptionId, rhs.subscriptionId).append(refund, rhs.refund).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum Type {

        REFUND_NOTIFICATION("refund.notification");
        private final String value;
        private static Map<String, RefundNotification.Type> constants = new HashMap<String, RefundNotification.Type>();

        static {
            for (RefundNotification.Type c: values()) {
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
        public static RefundNotification.Type fromValue(String value) {
            RefundNotification.Type constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("type", type).append("subscriptionId", subscriptionId).append("refund", refund).toString();
    }
}
