
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
import io.bitnet.model.payment.invoice.Invoice;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "type",
    "subscriptionId",
    "invoice"
})
public class InvoiceNotification {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    @NotNull
    private InvoiceNotification.Type type;
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
    @JsonProperty("invoice")
    @NotNull
    private Invoice invoice;

    /**
     *
     * (Required)
     *
     * @return
     *     The type
     */
    @JsonProperty("type")
    public InvoiceNotification.Type getType() {
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
    public void setType(InvoiceNotification.Type type) {
        this.type = type;
    }

    public InvoiceNotification withType(InvoiceNotification.Type type) {
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

    public InvoiceNotification withSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The invoice
     */
    @JsonProperty("invoice")
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * 
     * (Required)
     * 
     * @param invoice
     *     The invoice
     */
    @JsonProperty("invoice")
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public InvoiceNotification withInvoice(Invoice invoice) {
        this.invoice = invoice;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(type).append(subscriptionId).append(invoice).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InvoiceNotification) == false) {
            return false;
        }
        InvoiceNotification rhs = ((InvoiceNotification) other);
        return new EqualsBuilder().append(type, rhs.type).append(subscriptionId, rhs.subscriptionId).append(invoice, rhs.invoice).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum Type {

        INVOICE_NOTIFICATION("invoice.notification");
        private final String value;
        private static Map<String, InvoiceNotification.Type> constants = new HashMap<String, InvoiceNotification.Type>();

        static {
            for (InvoiceNotification.Type c: values()) {
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
        public static InvoiceNotification.Type fromValue(String value) {
            InvoiceNotification.Type constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("type", type).append("subscriptionId", subscriptionId).append("invoice", invoice).toString();
    }
}
