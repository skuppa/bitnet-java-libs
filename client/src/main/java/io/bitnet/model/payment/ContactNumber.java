
package io.bitnet.model.payment;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "label",
    "number"
})
public class ContactNumber {

    /**
     * Type of contact
     * (Required)
     * 
     */
    @JsonProperty("label")
    @NotNull
    private ContactNumber.Label label = ContactNumber.Label.fromValue("HOME");
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("number")
    @Pattern(regexp = "^[+]?[\\ 0-9-()]{1,}$")
    @Size(max = 25)
    @NotNull
    private String number;

    /**
     * Type of contact
     * (Required)
     * 
     * @return
     *     The label
     */
    @JsonProperty("label")
    public ContactNumber.Label getLabel() {
        return label;
    }

    /**
     * Type of contact
     * (Required)
     * 
     * @param label
     *     The label
     */
    @JsonProperty("label")
    public void setLabel(ContactNumber.Label label) {
        this.label = label;
    }

    public ContactNumber withLabel(ContactNumber.Label label) {
        this.label = label;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The number
     */
    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    /**
     * 
     * (Required)
     * 
     * @param number
     *     The number
     */
    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    public ContactNumber withNumber(String number) {
        this.number = number;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(label).append(number).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ContactNumber) == false) {
            return false;
        }
        ContactNumber rhs = ((ContactNumber) other);
        return new EqualsBuilder().append(label, rhs.label).append(number, rhs.number).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum Label {

        MOBILE("MOBILE"),
        HOME("HOME"),
        WORK("WORK");
        private final String value;
        private static Map<String, ContactNumber.Label> constants = new HashMap<String, ContactNumber.Label>();

        static {
            for (ContactNumber.Label c: values()) {
                constants.put(c.value, c);
            }
        }

        private Label(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static ContactNumber.Label fromValue(String value) {
            ContactNumber.Label constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("label", label).append("number", number).toString();
    }
}
