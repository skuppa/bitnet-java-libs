
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
public class Requested {

    /**
     * Currency requested by the Merchant (refer to ISO-4217 (http://www.iso.org/iso/currency_codes) for Fiat currencies)
     * 
     */
    @JsonProperty("currency")
    private Requested.Currency currency;
    @JsonProperty("amount")
    @Pattern(regexp = "^(\\d{1,8}(\\.\\d{1,3})?)?$")
    private String amount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Currency requested by the Merchant (refer to ISO-4217 (http://www.iso.org/iso/currency_codes) for Fiat currencies)
     * 
     * @return
     *     The currency
     */
    @JsonProperty("currency")
    public Requested.Currency getCurrency() {
        return currency;
    }

    /**
     * Currency requested by the Merchant (refer to ISO-4217 (http://www.iso.org/iso/currency_codes) for Fiat currencies)
     * 
     * @param currency
     *     The currency
     */
    @JsonProperty("currency")
    public void setCurrency(Requested.Currency currency) {
        this.currency = currency;
    }

    public Requested withCurrency(Requested.Currency currency) {
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

    public Requested withAmount(String amount) {
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

    public Requested withAdditionalProperty(String name, Object value) {
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
        if ((other instanceof Requested) == false) {
            return false;
        }
        Requested rhs = ((Requested) other);
        return new EqualsBuilder().append(currency, rhs.currency).append(amount, rhs.amount).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum Currency {

        AED("AED"),
        ARS("ARS"),
        AUD("AUD"),
        BBD("BBD"),
        BDT("BDT"),
        BGN("BGN"),
        BHD("BHD"),
        BOB("BOB"),
        BRL("BRL"),
        BZD("BZD"),
        CAD("CAD"),
        CHF("CHF"),
        CLP("CLP"),
        CNY("CNY"),
        CRC("CRC"),
        CZK("CZK"),
        DKK("DKK"),
        DOP("DOP"),
        EGP("EGP"),
        EUR("EUR"),
        GBP("GBP"),
        GTQ("GTQ"),
        HKD("HKD"),
        HNL("HNL"),
        HRK("HRK"),
        HUF("HUF"),
        IDR("IDR"),
        ILS("ILS"),
        INR("INR"),
        JMD("JMD"),
        JOD("JOD"),
        JPY("JPY"),
        KRW("KRW"),
        KWD("KWD"),
        KYD("KYD"),
        LKR("LKR"),
        LTL("LTL"),
        MVR("MVR"),
        MXN("MXN"),
        MYR("MYR"),
        NIO("NIO"),
        NOK("NOK"),
        NZD("NZD"),
        OMR("OMR"),
        PAB("PAB"),
        PEN("PEN"),
        PHP("PHP"),
        PKR("PKR"),
        PLN("PLN"),
        PYG("PYG"),
        QAR("QAR"),
        RON("RON"),
        RUB("RUB"),
        SAR("SAR"),
        SEK("SEK"),
        SGD("SGD"),
        THB("THB"),
        TRY("TRY"),
        TWD("TWD"),
        USD("USD"),
        VND("VND"),
        ZAR("ZAR");
        private final String value;
        private static Map<String, Requested.Currency> constants = new HashMap<String, Requested.Currency>();

        static {
            for (Requested.Currency c: values()) {
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
        public static Requested.Currency fromValue(String value) {
            Requested.Currency constant = constants.get(value);
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
