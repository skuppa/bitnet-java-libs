
package io.bitnet.model.refund;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "currency",
    "amount"
})
public class Sent {

    /**
     * Currency to refund the Payer with
     * 
     */
    @JsonProperty("currency")
    private Sent.Currency currency;
    @JsonProperty("amount")
    @Pattern(regexp = "^(\\d{1,8}(\\.\\d{1,8})?)?$")
    private String amount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Currency to refund the Payer with
     * 
     * @return
     *     The currency
     */
    @JsonProperty("currency")
    public Sent.Currency getCurrency() {
        return currency;
    }

    /**
     * Currency to refund the Payer with
     * 
     * @param currency
     *     The currency
     */
    @JsonProperty("currency")
    public void setCurrency(Sent.Currency currency) {
        this.currency = currency;
    }

    public Sent withCurrency(Sent.Currency currency) {
        this.currency = currency;
        return this;
    }

    /**
     * 
     * @return
     *     The amount
     */
    @JsonProperty("amount")
    public String getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     *     The amount
     */
    @JsonProperty("amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Sent withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Sent withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(currency).append(amount).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Sent) == false) {
            return false;
        }
        Sent rhs = ((Sent) other);
        return new EqualsBuilder().append(currency, rhs.currency).append(amount, rhs.amount).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum Currency {

        BTC("BTC");
        private final String value;
        private static Map<String, Sent.Currency> constants = new HashMap<String, Sent.Currency>();

        static {
            for (Sent.Currency c: values()) {
                constants.put(c.value, c);
            }
        }

        private Currency(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static Sent.Currency fromValue(String value) {
            Sent.Currency constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("currency", currency).append("amount", amount).append("additionalProperties", additionalProperties).toString();
    }
}
