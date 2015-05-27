
package io.bitnet.model.refund;

import javax.annotation.Generated;
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
 * refund
 * <p>
 * Create a single Refund
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "accountId",
    "invoiceId",
    "reference",
    "instruction",
    "currency",
    "amount"
})
public class RefundCreate {

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
    @JsonProperty("invoiceId")
    @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")
    @NotNull
    private String invoiceId;
    /**
     * Merchant supplied identifier for the Refund entity
     * 
     */
    @JsonProperty("reference")
    @Size(max = 50)
    private String reference;
    /**
     * The type of Refund
     * (Required)
     * 
     */
    @JsonProperty("instruction")
    @NotNull
    private io.bitnet.model.refund.Refund.Instruction instruction;
    /**
     * Currency requested by the Merchant (refer to ISO-4217 (http://www.iso.org/iso/currency_codes) for Fiat currencies)
     * 
     */
    @JsonProperty("currency")
    private io.bitnet.model.refund.Requested.Currency currency;
    @JsonProperty("amount")
    @Pattern(regexp = "^(\\d{1,8}(\\.\\d{1,3})?)?$")
    private String amount;

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

    public RefundCreate withAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The invoiceId
     */
    @JsonProperty("invoiceId")
    public String getInvoiceId() {
        return invoiceId;
    }

    /**
     * 
     * (Required)
     * 
     * @param invoiceId
     *     The invoiceId
     */
    @JsonProperty("invoiceId")
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public RefundCreate withInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
        return this;
    }

    /**
     * Merchant supplied identifier for the Refund entity
     * 
     * @return
     *     The reference
     */
    @JsonProperty("reference")
    public String getReference() {
        return reference;
    }

    /**
     * Merchant supplied identifier for the Refund entity
     * 
     * @param reference
     *     The reference
     */
    @JsonProperty("reference")
    public void setReference(String reference) {
        this.reference = reference;
    }

    public RefundCreate withReference(String reference) {
        this.reference = reference;
        return this;
    }

    /**
     * The type of Refund
     * (Required)
     * 
     * @return
     *     The instruction
     */
    @JsonProperty("instruction")
    public io.bitnet.model.refund.Refund.Instruction getInstruction() {
        return instruction;
    }

    /**
     * The type of Refund
     * (Required)
     * 
     * @param instruction
     *     The instruction
     */
    @JsonProperty("instruction")
    public void setInstruction(io.bitnet.model.refund.Refund.Instruction instruction) {
        this.instruction = instruction;
    }

    public RefundCreate withInstruction(io.bitnet.model.refund.Refund.Instruction instruction) {
        this.instruction = instruction;
        return this;
    }

    /**
     * Currency requested by the Merchant (refer to ISO-4217 (http://www.iso.org/iso/currency_codes) for Fiat currencies)
     * 
     * @return
     *     The currency
     */
    @JsonProperty("currency")
    public io.bitnet.model.refund.Requested.Currency getCurrency() {
        return currency;
    }

    /**
     * Currency requested by the Merchant (refer to ISO-4217 (http://www.iso.org/iso/currency_codes) for Fiat currencies)
     * 
     * @param currency
     *     The currency
     */
    @JsonProperty("currency")
    public void setCurrency(io.bitnet.model.refund.Requested.Currency currency) {
        this.currency = currency;
    }

    public RefundCreate withCurrency(io.bitnet.model.refund.Requested.Currency currency) {
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

    public RefundCreate withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(accountId).append(invoiceId).append(reference).append(instruction).append(currency).append(amount).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RefundCreate) == false) {
            return false;
        }
        RefundCreate rhs = ((RefundCreate) other);
        return new EqualsBuilder().append(accountId, rhs.accountId).append(invoiceId, rhs.invoiceId).append(reference, rhs.reference).append(instruction, rhs.instruction).append(currency, rhs.currency).append(amount, rhs.amount).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("accountId", accountId).append("invoiceId", invoiceId).append("reference", reference).append("instruction", instruction).append("currency", currency).append("amount", amount).toString();
    }
}
