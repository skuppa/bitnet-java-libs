
package io.bitnet.model.payment;

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
 * invoice
 * <p>
 * Create a  single Invoice
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "accountId",
    "orderId",
    "reference"
})
public class InvoiceCreate {

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
    @JsonProperty("orderId")
    @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")
    @NotNull
    private String orderId;
    /**
     * Invoice Reference
     * 
     */
    @JsonProperty("reference")
    @Size(max = 50)
    private String reference;

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

    public InvoiceCreate withAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The orderId
     */
    @JsonProperty("orderId")
    public String getOrderId() {
        return orderId;
    }

    /**
     * 
     * (Required)
     * 
     * @param orderId
     *     The orderId
     */
    @JsonProperty("orderId")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public InvoiceCreate withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    /**
     * Invoice Reference
     * 
     * @return
     *     The reference
     */
    @JsonProperty("reference")
    public String getReference() {
        return reference;
    }

    /**
     * Invoice Reference
     * 
     * @param reference
     *     The reference
     */
    @JsonProperty("reference")
    public void setReference(String reference) {
        this.reference = reference;
    }

    public InvoiceCreate withReference(String reference) {
        this.reference = reference;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(accountId).append(orderId).append(reference).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InvoiceCreate) == false) {
            return false;
        }
        InvoiceCreate rhs = ((InvoiceCreate) other);
        return new EqualsBuilder().append(accountId, rhs.accountId).append(orderId, rhs.orderId).append(reference, rhs.reference).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("accountId", accountId).append("orderId", orderId).append("reference", reference).toString();
    }
}
