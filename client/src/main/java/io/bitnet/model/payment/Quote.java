
package io.bitnet.model.payment;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
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


/**
 * The digital currency Quote
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "quoteId",
    "amount",
    "currency",
    "exchangeRate",
    "expiresAt"
})
public class Quote {

    @JsonProperty("quoteId")
    @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")
    private String quoteId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("amount")
    @Pattern(regexp = "^(\\d{1,8}(\\.\\d{1,8})?)?$")
    @NotNull
    private String amount;
    /**
     * Currency to be used by the Payer
     * (Required)
     * 
     */
    @JsonProperty("currency")
    @NotNull
    private Quote.Currency currency;
    @JsonProperty("exchangeRate")
    @Pattern(regexp = "^(\\d{1,8}(\\.\\d{1,8})?)?$")
    private String exchangeRate;
    /**
     * Time that the Quote expires at (UTC)
     * 
     */
    @JsonProperty("expiresAt")
    @Pattern(regexp = "^\\d{4}-\\d{1,2}-\\d{1,2}T{1}\\d{2}:{1}\\d{2}:{1}\\d{2}.{1}\\d{1,3}Z{1}$")
    private String expiresAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The quoteId
     */
    @JsonProperty("quoteId")
    public String getQuoteId() {
        return quoteId;
    }

    /**
     * 
     * @param quoteId
     *     The quoteId
     */
    @JsonProperty("quoteId")
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public Quote withQuoteId(String quoteId) {
        this.quoteId = quoteId;
        return this;
    }

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param amount
     *     The amount
     */
    @JsonProperty("amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Quote withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Currency to be used by the Payer
     * (Required)
     * 
     * @return
     *     The currency
     */
    @JsonProperty("currency")
    public Quote.Currency getCurrency() {
        return currency;
    }

    /**
     * Currency to be used by the Payer
     * (Required)
     * 
     * @param currency
     *     The currency
     */
    @JsonProperty("currency")
    public void setCurrency(Quote.Currency currency) {
        this.currency = currency;
    }

    public Quote withCurrency(Quote.Currency currency) {
        this.currency = currency;
        return this;
    }

    /**
     * 
     * @return
     *     The exchangeRate
     */
    @JsonProperty("exchangeRate")
    public String getExchangeRate() {
        return exchangeRate;
    }

    /**
     * 
     * @param exchangeRate
     *     The exchangeRate
     */
    @JsonProperty("exchangeRate")
    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Quote withExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    /**
     * Time that the Quote expires at (UTC)
     * 
     * @return
     *     The expiresAt
     */
    @JsonProperty("expiresAt")
    public String getExpiresAt() {
        return expiresAt;
    }

    /**
     * Time that the Quote expires at (UTC)
     * 
     * @param expiresAt
     *     The expiresAt
     */
    @JsonProperty("expiresAt")
    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Quote withExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
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

    public Quote withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(quoteId).append(amount).append(currency).append(exchangeRate).append(expiresAt).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Quote) == false) {
            return false;
        }
        Quote rhs = ((Quote) other);
        return new EqualsBuilder().append(quoteId, rhs.quoteId).append(amount, rhs.amount).append(currency, rhs.currency).append(exchangeRate, rhs.exchangeRate).append(expiresAt, rhs.expiresAt).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum Currency {

        BTC("BTC");
        private final String value;
        private static Map<String, Quote.Currency> constants = new HashMap<String, Quote.Currency>();

        static {
            for (Quote.Currency c: values()) {
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
        public static Quote.Currency fromValue(String value) {
            Quote.Currency constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("quoteId", quoteId).append("amount", amount).append("currency", currency).append("exchangeRate", exchangeRate).append("expiresAt", expiresAt).append("additionalProperties", additionalProperties).toString();
    }
}
