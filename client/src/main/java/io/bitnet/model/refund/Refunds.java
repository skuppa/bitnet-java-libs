
package io.bitnet.model.refund;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.Collections;
import java.util.List;



/**
 * refunds collection
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "size",
    "_embedded"
})
public class Refunds {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("size")
    @NotNull
    private Integer size;
    /**
     * 
     */
    @JsonProperty("_embedded")
    private io.bitnet.model.refund.Embedded Embedded;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The size
     */
    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    /**
     * 
     * (Required)
     * 
     * @param size
     *     The size
     */
    @JsonProperty("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    public Refunds withSize(Integer size) {
        this.size = size;
        return this;
    }

    /**
     * 
     * @return
     *     The Embedded
     */
    @JsonProperty("_embedded")
    public io.bitnet.model.refund.Embedded getEmbedded() {
        return Embedded;
    }

    /**
     * 
     * @param Embedded
     *     The _embedded
     */
    @JsonProperty("_embedded")
    public void setEmbedded(io.bitnet.model.refund.Embedded Embedded) {
        this.Embedded = Embedded;
    }

    public Refunds withEmbedded(io.bitnet.model.refund.Embedded Embedded) {
        this.Embedded = Embedded;
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

    public Refunds withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(size).append(Embedded).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Refunds) == false) {
            return false;
        }
        Refunds rhs = ((Refunds) other);
        return new EqualsBuilder().append(size, rhs.size).append(Embedded, rhs.Embedded).append(additionalProperties, rhs.additionalProperties).isEquals();
    }


    public List<Refund> getRefunds() {
        return Embedded == null || size == 0 ? Collections.<Refund>emptyList() : Embedded.getRefunds();
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("size", size).append("Embedded", Embedded).append("additionalProperties", additionalProperties).toString();
    }
}
