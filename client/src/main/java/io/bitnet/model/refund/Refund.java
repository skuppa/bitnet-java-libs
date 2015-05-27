
package io.bitnet.model.refund;

import java.util.HashMap;
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
 * refund
 * <p>
 * A single Refund
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "accountId",
    "invoiceId",
    "reference",
    "state",
    "instruction",
    "paymentAddress",
    "requested",
    "sent",
    "createdAt",
    "modifiedAt"
})
public class Refund {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")
    @NotNull
    private String id;
    @JsonProperty("accountId")
    @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")
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
     * The current state of the Refund
     * (Required)
     * 
     */
    @JsonProperty("state")
    @NotNull
    private Refund.State state = Refund.State.fromValue("OPEN");
    /**
     * The type of Refund
     * (Required)
     * 
     */
    @JsonProperty("instruction")
    @NotNull
    private Refund.Instruction instruction;
    /**
     * One-time coin address to transfer consumers refund (generated by Bitnet)
     * (Required)
     * 
     */
    @JsonProperty("paymentAddress")
    @Pattern(regexp = "^[213nm][a-km-zA-HJ-NP-Z1-9]{26,34}$")
    @NotNull
    private String paymentAddress;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("requested")
    @Valid
    @NotNull
    private Requested requested;
    @JsonProperty("sent")
    private Sent sent;
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

    public Refund withId(String id) {
        this.id = id;
        return this;
    }

    /**
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
     * @param accountId
     *     The accountId
     */
    @JsonProperty("accountId")
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Refund withAccountId(String accountId) {
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

    public Refund withInvoiceId(String invoiceId) {
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

    public Refund withReference(String reference) {
        this.reference = reference;
        return this;
    }

    /**
     * The current state of the Refund
     * (Required)
     * 
     * @return
     *     The state
     */
    @JsonProperty("state")
    public Refund.State getState() {
        return state;
    }

    /**
     * The current state of the Refund
     * (Required)
     * 
     * @param state
     *     The state
     */
    @JsonProperty("state")
    public void setState(Refund.State state) {
        this.state = state;
    }

    public Refund withState(Refund.State state) {
        this.state = state;
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
    public Refund.Instruction getInstruction() {
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
    public void setInstruction(Refund.Instruction instruction) {
        this.instruction = instruction;
    }

    public Refund withInstruction(Refund.Instruction instruction) {
        this.instruction = instruction;
        return this;
    }

    /**
     * One-time coin address to transfer consumers refund (generated by Bitnet)
     * (Required)
     * 
     * @return
     *     The paymentAddress
     */
    @JsonProperty("paymentAddress")
    public String getPaymentAddress() {
        return paymentAddress;
    }

    /**
     * One-time coin address to transfer consumers refund (generated by Bitnet)
     * (Required)
     * 
     * @param paymentAddress
     *     The paymentAddress
     */
    @JsonProperty("paymentAddress")
    public void setPaymentAddress(String paymentAddress) {
        this.paymentAddress = paymentAddress;
    }

    public Refund withPaymentAddress(String paymentAddress) {
        this.paymentAddress = paymentAddress;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The requested
     */
    @JsonProperty("requested")
    public Requested getRequested() {
        return requested;
    }

    /**
     * 
     * (Required)
     * 
     * @param requested
     *     The requested
     */
    @JsonProperty("requested")
    public void setRequested(Requested requested) {
        this.requested = requested;
    }

    public Refund withRequested(Requested requested) {
        this.requested = requested;
        return this;
    }

    /**
     * 
     * @return
     *     The sent
     */
    @JsonProperty("sent")
    public Sent getSent() {
        return sent;
    }

    /**
     * 
     * @param sent
     *     The sent
     */
    @JsonProperty("sent")
    public void setSent(Sent sent) {
        this.sent = sent;
    }

    public Refund withSent(Sent sent) {
        this.sent = sent;
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

    public Refund withCreatedAt(String createdAt) {
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

    public Refund withModifiedAt(String modifiedAt) {
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

    public Refund withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(accountId).append(invoiceId).append(reference).append(state).append(instruction).append(paymentAddress).append(requested).append(sent).append(createdAt).append(modifiedAt).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Refund) == false) {
            return false;
        }
        Refund rhs = ((Refund) other);
        return new EqualsBuilder().append(id, rhs.id).append(accountId, rhs.accountId).append(invoiceId, rhs.invoiceId).append(reference, rhs.reference).append(state, rhs.state).append(instruction, rhs.instruction).append(paymentAddress, rhs.paymentAddress).append(requested, rhs.requested).append(sent, rhs.sent).append(createdAt, rhs.createdAt).append(modifiedAt, rhs.modifiedAt).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum Instruction {

        FULL("FULL"),
        PARTIAL("PARTIAL"),
        MISPAYMENT_CORRECTION("MISPAYMENT_CORRECTION");
        private final String value;
        private static Map<String, Refund.Instruction> constants = new HashMap<String, Refund.Instruction>();

        static {
            for (Refund.Instruction c: values()) {
                constants.put(c.value, c);
            }
        }

        private Instruction(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static Refund.Instruction fromValue(String value) {
            Refund.Instruction constant = constants.get(value);
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
        PENDING("PENDING"),
        PAID("PAID");
        private final String value;
        private static Map<String, Refund.State> constants = new HashMap<String, Refund.State>();

        static {
            for (Refund.State c: values()) {
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
        public static Refund.State fromValue(String value) {
            Refund.State constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("accountId", accountId).append("invoiceId", invoiceId).append("reference", reference).append("state", state).append("instruction", instruction).append("paymentAddress", paymentAddress).append("requested", requested).append("sent", sent).append("createdAt", createdAt).append("modifiedAt", modifiedAt).append("additionalProperties", additionalProperties).toString();
    }
}
