
package io.bitnet.model.token;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "self"
})
public class Links {

    /**
     * XXX
     * (Required)
     * 
     */
    @JsonProperty("self")
    @Valid
    @NotNull
    private Self self;

    /**
     * XXX
     * (Required)
     * 
     * @return
     *     The self
     */
    @JsonProperty("self")
    public Self getSelf() {
        return self;
    }

    /**
     * XXX
     * (Required)
     * 
     * @param self
     *     The self
     */
    @JsonProperty("self")
    public void setSelf(Self self) {
        this.self = self;
    }

    public Links withSelf(Self self) {
        this.self = self;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(self).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Links) == false) {
            return false;
        }
        Links rhs = ((Links) other);
        return new EqualsBuilder().append(self, rhs.self).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("self", self).toString();
    }
}
