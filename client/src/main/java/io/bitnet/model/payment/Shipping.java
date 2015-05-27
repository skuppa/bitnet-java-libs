
package io.bitnet.model.payment;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * Shipping destination information
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "firstName",
    "lastName",
    "address",
    "contactNumbers",
    "method"
})
public class Shipping {

    /**
     * First name
     * 
     */
    @JsonProperty("firstName")
    @Size(max = 60)
    private String firstName;
    /**
     * Last name
     * 
     */
    @JsonProperty("lastName")
    @Size(max = 60)
    private String lastName;
    /**
     * 
     */
    @JsonProperty("address")
    @Valid
    private Address address;
    @JsonProperty("contactNumbers")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    @Valid
    private Set<ContactNumber> contactNumbers = new LinkedHashSet<ContactNumber>();
    /**
     * Carrier and service used to ship product / service
     * 
     */
    @JsonProperty("method")
    @Size(max = 60)
    private String method;

    /**
     * First name
     * 
     * @return
     *     The firstName
     */
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    /**
     * First name
     * 
     * @param firstName
     *     The firstName
     */
    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Shipping withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Last name
     * 
     * @return
     *     The lastName
     */
    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    /**
     * Last name
     * 
     * @param lastName
     *     The lastName
     */
    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Shipping withLastName(String lastName) {
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

    public Shipping withAddress(Address address) {
        this.address = address;
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

    public Shipping withContactNumbers(Set<ContactNumber> contactNumbers) {
        this.contactNumbers = contactNumbers;
        return this;
    }

    /**
     * Carrier and service used to ship product / service
     * 
     * @return
     *     The method
     */
    @JsonProperty("method")
    public String getMethod() {
        return method;
    }

    /**
     * Carrier and service used to ship product / service
     * 
     * @param method
     *     The method
     */
    @JsonProperty("method")
    public void setMethod(String method) {
        this.method = method;
    }

    public Shipping withMethod(String method) {
        this.method = method;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(firstName).append(lastName).append(address).append(contactNumbers).append(method).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Shipping) == false) {
            return false;
        }
        Shipping rhs = ((Shipping) other);
        return new EqualsBuilder().append(firstName, rhs.firstName).append(lastName, rhs.lastName).append(address, rhs.address).append(contactNumbers, rhs.contactNumbers).append(method, rhs.method).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("firstName", firstName).append("lastName", lastName).append("address", address).append("contactNumbers", contactNumbers).append("method", method).toString();
    }
}
