
package io.bitnet.model.payment;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * orderUpdate
 * <p>
 * Update a single Order
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "state"
})
public class OrderUpdate {

    /**
     * Current condition of the Order
     * (Required)
     * 
     */
    @JsonProperty("state")
    @NotNull
    private io.bitnet.model.payment.Order.State state = io.bitnet.model.payment.Order.State.fromValue("OPEN");

    /**
     * Current condition of the Order
     * (Required)
     * 
     * @return
     *     The state
     */
    @JsonProperty("state")
    public io.bitnet.model.payment.Order.State getState() {
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
    public void setState(io.bitnet.model.payment.Order.State state) {
        this.state = state;
    }

    public OrderUpdate withState(io.bitnet.model.payment.Order.State state) {
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
        if ((other instanceof OrderUpdate) == false) {
            return false;
        }
        OrderUpdate rhs = ((OrderUpdate) other);
        return new EqualsBuilder().append(state, rhs.state).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("state", state).toString();
    }
}
