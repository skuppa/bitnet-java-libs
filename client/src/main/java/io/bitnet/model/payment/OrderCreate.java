
package io.bitnet.model.payment;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * order
 * <p>
 * Create single Order
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "accountId",
    "reference",
    "integrationId",
    "desc",
    "currency",
    "amount",
    "taxAmount",
    "shippingAmount",
    "totalAmount",
    "payerId",
    "channelIdentifier",
    "items",
    "shipping"
})
public class OrderCreate {

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
    /**
     * Currency requested by the Merchant (refer to ISO-4217 (http://www.iso.org/iso/currency_codes) for Fiat currencies)
     * (Required)
     * 
     */
    @JsonProperty("currency")
    @NotNull
    private io.bitnet.model.payment.Order.Currency currency;
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
     * Channel identification for the Order
     * 
     */
    @JsonProperty("channelIdentifier")
    private io.bitnet.model.payment.Order.ChannelIdentifier channelIdentifier;
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

    public OrderCreate withAccountId(String accountId) {
        this.accountId = accountId;
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

    public OrderCreate withReference(String reference) {
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

    public OrderCreate withIntegrationId(String integrationId) {
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

    public OrderCreate withDesc(String desc) {
        this.desc = desc;
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
    public io.bitnet.model.payment.Order.Currency getCurrency() {
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
    public void setCurrency(io.bitnet.model.payment.Order.Currency currency) {
        this.currency = currency;
    }

    public OrderCreate withCurrency(io.bitnet.model.payment.Order.Currency currency) {
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

    public OrderCreate withAmount(String amount) {
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

    public OrderCreate withTaxAmount(String taxAmount) {
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

    public OrderCreate withShippingAmount(String shippingAmount) {
        this.shippingAmount = shippingAmount;
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

    public OrderCreate withTotalAmount(String totalAmount) {
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

    public OrderCreate withPayerId(String payerId) {
        this.payerId = payerId;
        return this;
    }

    /**
     * Channel identification for the Order
     * 
     * @return
     *     The channelIdentifier
     */
    @JsonProperty("channelIdentifier")
    public io.bitnet.model.payment.Order.ChannelIdentifier getChannelIdentifier() {
        return channelIdentifier;
    }

    /**
     * Channel identification for the Order
     * 
     * @param channelIdentifier
     *     The channelIdentifier
     */
    @JsonProperty("channelIdentifier")
    public void setChannelIdentifier(io.bitnet.model.payment.Order.ChannelIdentifier channelIdentifier) {
        this.channelIdentifier = channelIdentifier;
    }

    public OrderCreate withChannelIdentifier(io.bitnet.model.payment.Order.ChannelIdentifier channelIdentifier) {
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

    public OrderCreate withItems(List<Item> items) {
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

    public OrderCreate withShipping(Shipping shipping) {
        this.shipping = shipping;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(accountId).append(reference).append(integrationId).append(desc).append(currency).append(amount).append(taxAmount).append(shippingAmount).append(totalAmount).append(payerId).append(channelIdentifier).append(items).append(shipping).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OrderCreate) == false) {
            return false;
        }
        OrderCreate rhs = ((OrderCreate) other);
        return new EqualsBuilder().append(accountId, rhs.accountId).append(reference, rhs.reference).append(integrationId, rhs.integrationId).append(desc, rhs.desc).append(currency, rhs.currency).append(amount, rhs.amount).append(taxAmount, rhs.taxAmount).append(shippingAmount, rhs.shippingAmount).append(totalAmount, rhs.totalAmount).append(payerId, rhs.payerId).append(channelIdentifier, rhs.channelIdentifier).append(items, rhs.items).append(shipping, rhs.shipping).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("accountId", accountId).append("reference", reference).append("integrationId", integrationId).append("desc", desc).append("currency", currency).append("amount", amount).append("taxAmount", taxAmount).append("shippingAmount", shippingAmount).append("totalAmount", totalAmount).append("payerId", payerId).append("channelIdentifier", channelIdentifier).append("items", items).append("shipping", shipping).toString();
    }
}
