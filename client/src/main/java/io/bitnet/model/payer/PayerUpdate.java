
package io.bitnet.model.payer;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
 * Update a single Payer
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "reference",
    "firstName",
    "lastName",
    "address",
    "email",
    "contactNumbers",
    "refundPaymentAddress"
})
public class PayerUpdate {

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
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("email")
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    @Size(max = 255)
    @NotNull
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

    public PayerUpdate withReference(String reference) {
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

    public PayerUpdate withFirstName(String firstName) {
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

    public PayerUpdate withLastName(String lastName) {
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

    public PayerUpdate withAddress(Address address) {
        this.address = address;
        return this;
    }

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param email
     *     The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public PayerUpdate withEmail(String email) {
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

    public PayerUpdate withContactNumbers(Set<ContactNumber> contactNumbers) {
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

    public PayerUpdate withRefundPaymentAddress(String refundPaymentAddress) {
        this.refundPaymentAddress = refundPaymentAddress;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(reference).append(firstName).append(lastName).append(address).append(email).append(contactNumbers).append(refundPaymentAddress).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PayerUpdate) == false) {
            return false;
        }
        PayerUpdate rhs = ((PayerUpdate) other);
        return new EqualsBuilder().append(reference, rhs.reference).append(firstName, rhs.firstName).append(lastName, rhs.lastName).append(address, rhs.address).append(email, rhs.email).append(contactNumbers, rhs.contactNumbers).append(refundPaymentAddress, rhs.refundPaymentAddress).isEquals();
    }


    public PayerUpdate(Payer payer) {
        org.modelmapper.ModelMapper modelMapper = new org.modelmapper.ModelMapper();
        modelMapper.map(payer, this);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("reference", reference).append("firstName", firstName).append("lastName", lastName).append("address", address).append("email", email).append("contactNumbers", contactNumbers).append("refundPaymentAddress", refundPaymentAddress).toString();
    }
}
