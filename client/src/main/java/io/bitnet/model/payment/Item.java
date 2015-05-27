
package io.bitnet.model.payment;

import javax.annotation.Generated;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "name",
    "desc",
    "price",
    "quantity",
    "sku"
})
public class Item {

    /**
     * name of product / service
     * 
     */
    @JsonProperty("name")
    @Size(max = 100)
    private String name;
    /**
     * Description of product / service
     * 
     */
    @JsonProperty("desc")
    @Size(max = 255)
    private String desc;
    @JsonProperty("price")
    @Pattern(regexp = "^(\\d{1,8}(\\.\\d{1,3})?)?$")
    private String price;
    /**
     * quantity of given product / service
     * 
     */
    @JsonProperty("quantity")
    @DecimalMin("1")
    @DecimalMax("99999")
    private Integer quantity;
    /**
     * Stock keeping unit of given product / service
     * 
     */
    @JsonProperty("sku")
    @Size(max = 50)
    private String sku;

    /**
     * name of product / service
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * name of product / service
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Item withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Description of product / service
     * 
     * @return
     *     The desc
     */
    @JsonProperty("desc")
    public String getDesc() {
        return desc;
    }

    /**
     * Description of product / service
     * 
     * @param desc
     *     The desc
     */
    @JsonProperty("desc")
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Item withDesc(String desc) {
        this.desc = desc;
        return this;
    }

    /**
     * 
     * @return
     *     The price
     */
    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    public Item withPrice(String price) {
        this.price = price;
        return this;
    }

    /**
     * quantity of given product / service
     * 
     * @return
     *     The quantity
     */
    @JsonProperty("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * quantity of given product / service
     * 
     * @param quantity
     *     The quantity
     */
    @JsonProperty("quantity")
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Item withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Stock keeping unit of given product / service
     * 
     * @return
     *     The sku
     */
    @JsonProperty("sku")
    public String getSku() {
        return sku;
    }

    /**
     * Stock keeping unit of given product / service
     * 
     * @param sku
     *     The sku
     */
    @JsonProperty("sku")
    public void setSku(String sku) {
        this.sku = sku;
    }

    public Item withSku(String sku) {
        this.sku = sku;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(desc).append(price).append(quantity).append(sku).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Item) == false) {
            return false;
        }
        Item rhs = ((Item) other);
        return new EqualsBuilder().append(name, rhs.name).append(desc, rhs.desc).append(price, rhs.price).append(quantity, rhs.quantity).append(sku, rhs.sku).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("desc", desc).append("price", price).append("quantity", quantity).append("sku", sku).toString();
    }
}
