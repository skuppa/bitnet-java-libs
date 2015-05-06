/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;

/**
 * This differs to the generated model as it has been hand rolled to allow us to use a string
 * for state, rather than the enum, as the public facing enum does not have all the states used
 * by internal clients.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "state"
})
public class TestInvoiceUpdate {

    /**
     * Current condition of the Invoice.
     * (Required)
     *
     */
    @JsonProperty("state")
    @NotNull
    private String state;

    /**
     * Current condition of the Invoice.
     * (Required)
     *
     * @return
     *     The state
     */
    @JsonProperty("state")
    public String getState() {
        return state;
    }

    /**
     * Current condition of the Invoice.
     * (Required)
     *
     * @param state
     *     The state
     */
    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    public TestInvoiceUpdate withState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(state).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof TestInvoiceUpdate == false) {
            return false;
        }
        TestInvoiceUpdate rhs = ((TestInvoiceUpdate) other);
        return new EqualsBuilder().append(state, rhs.state).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("state", state).toString();
    }
}
