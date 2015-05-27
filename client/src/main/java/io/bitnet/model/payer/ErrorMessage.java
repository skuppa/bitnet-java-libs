
package io.bitnet.model.payer;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * error response
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "errors"
})
public class ErrorMessage {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("errors")
    @Valid
    @NotNull
    private List<Error> errors = new ArrayList<Error>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The errors
     */
    @JsonProperty("errors")
    public List<Error> getErrors() {
        return errors;
    }

    /**
     * 
     * (Required)
     * 
     * @param errors
     *     The errors
     */
    @JsonProperty("errors")
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public ErrorMessage withErrors(List<Error> errors) {
        this.errors = errors;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(errors).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ErrorMessage) == false) {
            return false;
        }
        ErrorMessage rhs = ((ErrorMessage) other);
        return new EqualsBuilder().append(errors, rhs.errors).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("errors", errors).toString();
    }
}
