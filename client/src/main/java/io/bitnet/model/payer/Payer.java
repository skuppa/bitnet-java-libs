
package io.bitnet.model.payer;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * payer
 * <p>
 * A single Payer
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "accountId",
    "reference",
    "firstName",
    "lastName",
    "address",
    "email",
    "contactNumbers",
    "refundPaymentAddress",
    "createdAt",
    "modifiedAt"
})
public class Payer {

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
    @JsonProperty("reference")
    @Size(max = 50)
    private String reference;
    @JsonProperty("firstName")
    @Size(max = 60)
    private String firstName;
    @JsonProperty("lastName")
    @Size(max = 60)
    private String lastName;
    /**
     * 
     */
    @JsonProperty("address")
    private Address address;
    @JsonProperty("email")
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    @Size(max = 255)
    private String email;
    @JsonProperty("contactNumbers")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    private Set<ContactNumber> contactNumbers = new LinkedHashSet<ContactNumber>();
    /**
     * Coin address used to by consumers to receive refunds
     * 
     */
    @JsonProperty("refundPaymentAddress")
    @Pattern(regexp = "^[123nm][a-km-zA-HJ-NP-Z1-9]{26,34}$")
    private String refundPaymentAddress;
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

    public Payer withId(String id) {
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

    public Payer withAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    /**
     * 
     * @return
     *     The reference
     */
    @JsonProperty("reference")
    public String getReference() {
        return reference;
    }

    /**
     * 
     * @param reference
     *     The reference
     */
    @JsonProperty("reference")
    public void setReference(String reference) {
        this.reference = reference;
    }

    public Payer withReference(String reference) {
        this.reference = reference;
        return this;
    }

    /**
     * 
     * @return
     *     The firstName
     */
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The firstName
     */
    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Payer withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The lastName
     */
    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Payer withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * 
     * @return
     *     The address
     */
    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    public Payer withAddress(Address address) {
        this.address = address;
        return this;
    }

    /**
     * 
     * @return
     *     The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public Payer withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * 
     * @return
     *     The contactNumbers
     */
    @JsonProperty("contactNumbers")
    public Set<ContactNumber> getContactNumbers() {
        return contactNumbers;
    }

    /**
     * 
     * @param contactNumbers
     *     The contactNumbers
     */
    @JsonProperty("contactNumbers")
    public void setContactNumbers(Set<ContactNumber> contactNumbers) {
        this.contactNumbers = contactNumbers;
    }

    public Payer withContactNumbers(Set<ContactNumber> contactNumbers) {
        this.contactNumbers = contactNumbers;
        return this;
    }

    /**
     * Coin address used to by consumers to receive refunds
     * 
     * @return
     *     The refundPaymentAddress
     */
    @JsonProperty("refundPaymentAddress")
    public String getRefundPaymentAddress() {
        return refundPaymentAddress;
    }

    /**
     * Coin address used to by consumers to receive refunds
     * 
     * @param refundPaymentAddress
     *     The refundPaymentAddress
     */
    @JsonProperty("refundPaymentAddress")
    public void setRefundPaymentAddress(String refundPaymentAddress) {
        this.refundPaymentAddress = refundPaymentAddress;
    }

    public Payer withRefundPaymentAddress(String refundPaymentAddress) {
        this.refundPaymentAddress = refundPaymentAddress;
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

    public Payer withCreatedAt(String createdAt) {
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

    public Payer withModifiedAt(String modifiedAt) {
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

    public Payer withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(accountId).append(reference).append(firstName).append(lastName).append(address).append(email).append(contactNumbers).append(refundPaymentAddress).append(createdAt).append(modifiedAt).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Payer) == false) {
            return false;
        }
        Payer rhs = ((Payer) other);
        return new EqualsBuilder().append(id, rhs.id).append(accountId, rhs.accountId).append(reference, rhs.reference).append(firstName, rhs.firstName).append(lastName, rhs.lastName).append(address, rhs.address).append(email, rhs.email).append(contactNumbers, rhs.contactNumbers).append(refundPaymentAddress, rhs.refundPaymentAddress).append(createdAt, rhs.createdAt).append(modifiedAt, rhs.modifiedAt).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("accountId", accountId).append("reference", reference).append("firstName", firstName).append("lastName", lastName).append("address", address).append("email", email).append("contactNumbers", contactNumbers).append("refundPaymentAddress", refundPaymentAddress).append("createdAt", createdAt).append("modifiedAt", modifiedAt).append("additionalProperties", additionalProperties).toString();
    }
}
