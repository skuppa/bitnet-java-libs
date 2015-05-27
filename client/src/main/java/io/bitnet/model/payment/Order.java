
package io.bitnet.model.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
 * order
 * <p>
 * A single Order
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "accountId",
    "totalAmount",
    "payerId",
    "state",
    "currency",
    "reference",
    "integrationId",
    "desc",
    "amount",
    "taxAmount",
    "shippingAmount",
    "channelIdentifier",
    "items",
    "shipping",
    "createdAt",
    "modifiedAt"
})
public class Order {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")
    @NotNull
    private String id;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("accountId")
    @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")
    @NotNull
    private String accountId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("totalAmount")
    @Pattern(regexp = "^\\s*(?=.*[1-9])\\d{1,8}(?:\\.\\d{1,3})?\\s*$")
    @NotNull
    private String totalAmount;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("payerId")
    @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")
    @NotNull
    private String payerId;
    /**
     * Current condition of the Order
     * (Required)
     * 
     */
    @JsonProperty("state")
    @NotNull
    private Order.State state = Order.State.fromValue("OPEN");
    /**
     * Currency requested by the Merchant (refer to ISO-4217 (http://www.iso.org/iso/currency_codes) for Fiat currencies)
     * (Required)
     * 
     */
    @JsonProperty("currency")
    @NotNull
    private Order.Currency currency;
    /**
     * Payee Reference
     * 
     */
    @JsonProperty("reference")
    @Size(max = 50)
    private String reference;
    /**
     * Unique identifier for tracking partners
     * 
     */
    @JsonProperty("integrationId")
    @Size(max = 40)
    private String integrationId;
    /**
     * A summary of the Order
     * 
     */
    @JsonProperty("desc")
    @Size(max = 255)
    private String desc;
    @JsonProperty("amount")
    @Pattern(regexp = "^(\\d{1,8}(\\.\\d{1,3})?)?$")
    private String amount;
    @JsonProperty("taxAmount")
    @Pattern(regexp = "^(\\d{1,8}(\\.\\d{1,3})?)?$")
    private String taxAmount;
    @JsonProperty("shippingAmount")
    @Pattern(regexp = "^(\\d{1,8}(\\.\\d{1,3})?)?$")
    private String shippingAmount;
    /**
     * Channel identification for the Order
     * 
     */
    @JsonProperty("channelIdentifier")
    private Order.ChannelIdentifier channelIdentifier;
    /**
     * Collection of Order line items
     * 
     */
    @JsonProperty("items")
    @Valid
    private List<Item> items = new ArrayList<Item>();
    /**
     * Shipping destination information
     * 
     */
    @JsonProperty("shipping")
    @Valid
    private Shipping shipping;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("createdAt")
    @Pattern(regexp = "^\\d{4}-[0-1][0-9]-[0-3]\\d{1}T[0-2]\\d{1}:[0-5]\\d{1}:[0-5]\\d{1}[.]\\d{1,5}Z$")
    @NotNull
    private String createdAt;
    @JsonProperty("modifiedAt")
    @Pattern(regexp = "^\\d{4}-[0-1][0-9]-[0-3]\\d{1}T[0-2]\\d{1}:[0-5]\\d{1}:[0-5]\\d{1}[.]\\d{1,5}Z$")
    private String modifiedAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * (Required)
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Order withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The accountId
     */
    @JsonProperty("accountId")
    public String getAccountId() {
        return accountId;
    }

    /**
     * 
     * (Required)
     * 
     * @param accountId
     *     The accountId
     */
    @JsonProperty("accountId")
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Order withAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The totalAmount
     */
    @JsonProperty("totalAmount")
    public String getTotalAmount() {
        return totalAmount;
    }

    /**
     * 
     * (Required)
     * 
     * @param totalAmount
     *     The totalAmount
     */
    @JsonProperty("totalAmount")
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Order withTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The payerId
     */
    @JsonProperty("payerId")
    public String getPayerId() {
        return payerId;
    }

    /**
     * 
     * (Required)
     * 
     * @param payerId
     *     The payerId
     */
    @JsonProperty("payerId")
    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public Order withPayerId(String payerId) {
        this.payerId = payerId;
        return this;
    }

    /**
     * Current condition of the Order
     * (Required)
     * 
     * @return
     *     The state
     */
    @JsonProperty("state")
    public Order.State getState() {
        return state;
    }

    /**
     * Current condition of the Order
     * (Required)
     * 
     * @param state
     *     The state
     */
    @JsonProperty("state")
    public void setState(Order.State state) {
        this.state = state;
    }

    public Order withState(Order.State state) {
        this.state = state;
        return this;
    }

    /**
     * Currency requested by the Merchant (refer to ISO-4217 (http://www.iso.org/iso/currency_codes) for Fiat currencies)
     * (Required)
     * 
     * @return
     *     The currency
     */
    @JsonProperty("currency")
    public Order.Currency getCurrency() {
        return currency;
    }

    /**
     * Currency requested by the Merchant (refer to ISO-4217 (http://www.iso.org/iso/currency_codes) for Fiat currencies)
     * (Required)
     * 
     * @param currency
     *     The currency
     */
    @JsonProperty("currency")
    public void setCurrency(Order.Currency currency) {
        this.currency = currency;
    }

    public Order withCurrency(Order.Currency currency) {
        this.currency = currency;
        return this;
    }

    /**
     * Payee Reference
     * 
     * @return
     *     The reference
     */
    @JsonProperty("reference")
    public String getReference() {
        return reference;
    }

    /**
     * Payee Reference
     * 
     * @param reference
     *     The reference
     */
    @JsonProperty("reference")
    public void setReference(String reference) {
        this.reference = reference;
    }

    public Order withReference(String reference) {
        this.reference = reference;
        return this;
    }

    /**
     * Unique identifier for tracking partners
     * 
     * @return
     *     The integrationId
     */
    @JsonProperty("integrationId")
    public String getIntegrationId() {
        return integrationId;
    }

    /**
     * Unique identifier for tracking partners
     * 
     * @param integrationId
     *     The integrationId
     */
    @JsonProperty("integrationId")
    public void setIntegrationId(String integrationId) {
        this.integrationId = integrationId;
    }

    public Order withIntegrationId(String integrationId) {
        this.integrationId = integrationId;
        return this;
    }

    /**
     * A summary of the Order
     * 
     * @return
     *     The desc
     */
    @JsonProperty("desc")
    public String getDesc() {
        return desc;
    }

    /**
     * A summary of the Order
     * 
     * @param desc
     *     The desc
     */
    @JsonProperty("desc")
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Order withDesc(String desc) {
        this.desc = desc;
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

    public Order withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    /**
     * 
     * @return
     *     The taxAmount
     */
    @JsonProperty("taxAmount")
    public String getTaxAmount() {
        return taxAmount;
    }

    /**
     * 
     * @param taxAmount
     *     The taxAmount
     */
    @JsonProperty("taxAmount")
    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Order withTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    /**
     * 
     * @return
     *     The shippingAmount
     */
    @JsonProperty("shippingAmount")
    public String getShippingAmount() {
        return shippingAmount;
    }

    /**
     * 
     * @param shippingAmount
     *     The shippingAmount
     */
    @JsonProperty("shippingAmount")
    public void setShippingAmount(String shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public Order withShippingAmount(String shippingAmount) {
        this.shippingAmount = shippingAmount;
        return this;
    }

    /**
     * Channel identification for the Order
     * 
     * @return
     *     The channelIdentifier
     */
    @JsonProperty("channelIdentifier")
    public Order.ChannelIdentifier getChannelIdentifier() {
        return channelIdentifier;
    }

    /**
     * Channel identification for the Order
     * 
     * @param channelIdentifier
     *     The channelIdentifier
     */
    @JsonProperty("channelIdentifier")
    public void setChannelIdentifier(Order.ChannelIdentifier channelIdentifier) {
        this.channelIdentifier = channelIdentifier;
    }

    public Order withChannelIdentifier(Order.ChannelIdentifier channelIdentifier) {
        this.channelIdentifier = channelIdentifier;
        return this;
    }

    /**
     * Collection of Order line items
     * 
     * @return
     *     The items
     */
    @JsonProperty("items")
    public List<Item> getItems() {
        return items;
    }

    /**
     * Collection of Order line items
     * 
     * @param items
     *     The items
     */
    @JsonProperty("items")
    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Order withItems(List<Item> items) {
        this.items = items;
        return this;
    }

    /**
     * Shipping destination information
     * 
     * @return
     *     The shipping
     */
    @JsonProperty("shipping")
    public Shipping getShipping() {
        return shipping;
    }

    /**
     * Shipping destination information
     * 
     * @param shipping
     *     The shipping
     */
    @JsonProperty("shipping")
    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Order withShipping(Shipping shipping) {
        this.shipping = shipping;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The createdAt
     */
    @JsonProperty("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * (Required)
     * 
     * @param createdAt
     *     The createdAt
     */
    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Order withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * 
     * @return
     *     The modifiedAt
     */
    @JsonProperty("modifiedAt")
    public String getModifiedAt() {
        return modifiedAt;
    }

    /**
     * 
     * @param modifiedAt
     *     The modifiedAt
     */
    @JsonProperty("modifiedAt")
    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Order withModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
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

    public Order withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(accountId).append(totalAmount).append(payerId).append(state).append(currency).append(reference).append(integrationId).append(desc).append(amount).append(taxAmount).append(shippingAmount).append(channelIdentifier).append(items).append(shipping).append(createdAt).append(modifiedAt).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Order) == false) {
            return false;
        }
        Order rhs = ((Order) other);
        return new EqualsBuilder().append(id, rhs.id).append(accountId, rhs.accountId).append(totalAmount, rhs.totalAmount).append(payerId, rhs.payerId).append(state, rhs.state).append(currency, rhs.currency).append(reference, rhs.reference).append(integrationId, rhs.integrationId).append(desc, rhs.desc).append(amount, rhs.amount).append(taxAmount, rhs.taxAmount).append(shippingAmount, rhs.shippingAmount).append(channelIdentifier, rhs.channelIdentifier).append(items, rhs.items).append(shipping, rhs.shipping).append(createdAt, rhs.createdAt).append(modifiedAt, rhs.modifiedAt).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum ChannelIdentifier {

        WEBSITE("WEBSITE"),
        MOTO("MOTO"),
        RETAIL("RETAIL"),
        MOBILE_APP("MOBILE_APP");
        private final String value;
        private static Map<String, Order.ChannelIdentifier> constants = new HashMap<String, Order.ChannelIdentifier>();

        static {
            for (Order.ChannelIdentifier c: values()) {
                constants.put(c.value, c);
            }
        }

        private ChannelIdentifier(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static Order.ChannelIdentifier fromValue(String value) {
            Order.ChannelIdentifier constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Generated("org.jsonschema2pojo")
    public static enum Currency {

        AED("AED"),
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
        USD("USD"),
        ZAR("ZAR");
        private final String value;
        private static Map<String, Order.Currency> constants = new HashMap<String, Order.Currency>();

        static {
            for (Order.Currency c: values()) {
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
        public static Order.Currency fromValue(String value) {
            Order.Currency constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Generated("org.jsonschema2pojo")
    public static enum State {

        OPEN("OPEN"),
        CANCELED("CANCELED"),
        INVOICED("INVOICED"),
        PAID("PAID");
        private final String value;
        private static Map<String, Order.State> constants = new HashMap<String, Order.State>();

        static {
            for (Order.State c: values()) {
                constants.put(c.value, c);
            }
        }

        private State(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static Order.State fromValue(String value) {
            Order.State constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("accountId", accountId).append("totalAmount", totalAmount).append("payerId", payerId).append("state", state).append("currency", currency).append("reference", reference).append("integrationId", integrationId).append("desc", desc).append("amount", amount).append("taxAmount", taxAmount).append("shippingAmount", shippingAmount).append("channelIdentifier", channelIdentifier).append("items", items).append("shipping", shipping).append("createdAt", createdAt).append("modifiedAt", modifiedAt).append("additionalProperties", additionalProperties).toString();
    }
}
