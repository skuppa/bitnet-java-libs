
package io.bitnet.model.payment;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
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
    "addressLine1",
    "addressLine2",
    "city",
    "postalCode",
    "country",
    "region"
})
public class Address {

    /**
     *  1st line of address
     * (Required)
     * 
     */
    @JsonProperty("addressLine1")
    @Size(min = 1, max = 60)
    @NotNull
    private String addressLine1;
    /**
     *  2nd line of address
     * 
     */
    @JsonProperty("addressLine2")
    @Size(max = 60)
    private String addressLine2;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("city")
    @Size(min = 1, max = 60)
    @NotNull
    private String city;
    /**
     * Zip / Postal Code
     * (Required)
     * 
     */
    @JsonProperty("postalCode")
    @Size(min = 1, max = 10)
    @NotNull
    private String postalCode;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("country")
    @NotNull
    private Address.Country country = Address.Country.fromValue("US");
    @JsonProperty("region")
    @Size(min = 1, max = 50)
    private String region;

    /**
     *  1st line of address
     * (Required)
     * 
     * @return
     *     The addressLine1
     */
    @JsonProperty("addressLine1")
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     *  1st line of address
     * (Required)
     * 
     * @param addressLine1
     *     The addressLine1
     */
    @JsonProperty("addressLine1")
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public Address withAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    /**
     *  2nd line of address
     * 
     * @return
     *     The addressLine2
     */
    @JsonProperty("addressLine2")
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     *  2nd line of address
     * 
     * @param addressLine2
     *     The addressLine2
     */
    @JsonProperty("addressLine2")
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public Address withAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The city
     */
    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    /**
     * 
     * (Required)
     * 
     * @param city
     *     The city
     */
    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    public Address withCity(String city) {
        this.city = city;
        return this;
    }

    /**
     * Zip / Postal Code
     * (Required)
     * 
     * @return
     *     The postalCode
     */
    @JsonProperty("postalCode")
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Zip / Postal Code
     * (Required)
     * 
     * @param postalCode
     *     The postalCode
     */
    @JsonProperty("postalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Address withPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The country
     */
    @JsonProperty("country")
    public Address.Country getCountry() {
        return country;
    }

    /**
     * 
     * (Required)
     * 
     * @param country
     *     The country
     */
    @JsonProperty("country")
    public void setCountry(Address.Country country) {
        this.country = country;
    }

    public Address withCountry(Address.Country country) {
        this.country = country;
        return this;
    }

    /**
     * 
     * @return
     *     The region
     */
    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    /**
     * 
     * @param region
     *     The region
     */
    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    public Address withRegion(String region) {
        this.region = region;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(addressLine1).append(addressLine2).append(city).append(postalCode).append(country).append(region).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Address) == false) {
            return false;
        }
        Address rhs = ((Address) other);
        return new EqualsBuilder().append(addressLine1, rhs.addressLine1).append(addressLine2, rhs.addressLine2).append(city, rhs.city).append(postalCode, rhs.postalCode).append(country, rhs.country).append(region, rhs.region).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum Country {

        AD("AD"),
        AE("AE"),
        AF("AF"),
        AG("AG"),
        AI("AI"),
        AL("AL"),
        AM("AM"),
        AN("AN"),
        AO("AO"),
        AQ("AQ"),
        AR("AR"),
        AS("AS"),
        AT("AT"),
        AU("AU"),
        AW("AW"),
        AX("AX"),
        AZ("AZ"),
        BA("BA"),
        BB("BB"),
        BD("BD"),
        BE("BE"),
        BF("BF"),
        BG("BG"),
        BH("BH"),
        BI("BI"),
        BJ("BJ"),
        BL("BL"),
        BM("BM"),
        BN("BN"),
        BO("BO"),
        BQ("BQ"),
        BR("BR"),
        BS("BS"),
        BT("BT"),
        BV("BV"),
        BW("BW"),
        BY("BY"),
        BZ("BZ"),
        CA("CA"),
        CC("CC"),
        CD("CD"),
        CF("CF"),
        CG("CG"),
        CH("CH"),
        CI("CI"),
        CK("CK"),
        CL("CL"),
        CM("CM"),
        CN("CN"),
        CO("CO"),
        CR("CR"),
        CU("CU"),
        CV("CV"),
        CW("CW"),
        CX("CX"),
        CY("CY"),
        CZ("CZ"),
        DE("DE"),
        DJ("DJ"),
        DK("DK"),
        DM("DM"),
        DO("DO"),
        DZ("DZ"),
        EC("EC"),
        EE("EE"),
        EG("EG"),
        EH("EH"),
        ER("ER"),
        ES("ES"),
        ET("ET"),
        FI("FI"),
        FJ("FJ"),
        FK("FK"),
        FM("FM"),
        FO("FO"),
        FR("FR"),
        GA("GA"),
        GB("GB"),
        GD("GD"),
        GE("GE"),
        GF("GF"),
        GG("GG"),
        GH("GH"),
        GI("GI"),
        GL("GL"),
        GM("GM"),
        GN("GN"),
        GP("GP"),
        GQ("GQ"),
        GR("GR"),
        GS("GS"),
        GT("GT"),
        GU("GU"),
        GW("GW"),
        GY("GY"),
        HK("HK"),
        HM("HM"),
        HN("HN"),
        HR("HR"),
        HT("HT"),
        HU("HU"),
        ID("ID"),
        IE("IE"),
        IL("IL"),
        IM("IM"),
        IN("IN"),
        IO("IO"),
        IQ("IQ"),
        IR("IR"),
        IS("IS"),
        IT("IT"),
        JE("JE"),
        JM("JM"),
        JO("JO"),
        JP("JP"),
        KE("KE"),
        KG("KG"),
        KH("KH"),
        KI("KI"),
        KM("KM"),
        KN("KN"),
        KP("KP"),
        KR("KR"),
        KW("KW"),
        KY("KY"),
        KZ("KZ"),
        LA("LA"),
        LB("LB"),
        LC("LC"),
        LI("LI"),
        LK("LK"),
        LR("LR"),
        LS("LS"),
        LT("LT"),
        LU("LU"),
        LV("LV"),
        LY("LY"),
        MA("MA"),
        MC("MC"),
        MD("MD"),
        ME("ME"),
        MF("MF"),
        MG("MG"),
        MH("MH"),
        MK("MK"),
        ML("ML"),
        MM("MM"),
        MN("MN"),
        MO("MO"),
        MP("MP"),
        MQ("MQ"),
        MR("MR"),
        MS("MS"),
        MT("MT"),
        MU("MU"),
        MV("MV"),
        MW("MW"),
        MX("MX"),
        MY("MY"),
        MZ("MZ"),
        NA("NA"),
        NC("NC"),
        NE("NE"),
        NF("NF"),
        NG("NG"),
        NI("NI"),
        NL("NL"),
        NO("NO"),
        NP("NP"),
        NR("NR"),
        NU("NU"),
        NZ("NZ"),
        OM("OM"),
        PA("PA"),
        PE("PE"),
        PF("PF"),
        PG("PG"),
        PH("PH"),
        PK("PK"),
        PL("PL"),
        PM("PM"),
        PN("PN"),
        PR("PR"),
        PS("PS"),
        PT("PT"),
        PW("PW"),
        PY("PY"),
        QA("QA"),
        RE("RE"),
        RO("RO"),
        RS("RS"),
        RU("RU"),
        RW("RW"),
        SA("SA"),
        SB("SB"),
        SC("SC"),
        SD("SD"),
        SE("SE"),
        SG("SG"),
        SH("SH"),
        SI("SI"),
        SJ("SJ"),
        SK("SK"),
        SL("SL"),
        SM("SM"),
        SN("SN"),
        SO("SO"),
        SR("SR"),
        ST("ST"),
        SV("SV"),
        SX("SX"),
        SY("SY"),
        SZ("SZ"),
        TC("TC"),
        TD("TD"),
        TF("TF"),
        TG("TG"),
        TH("TH"),
        TJ("TJ"),
        TK("TK"),
        TL("TL"),
        TM("TM"),
        TN("TN"),
        TO("TO"),
        TR("TR"),
        TT("TT"),
        TV("TV"),
        TW("TW"),
        TZ("TZ"),
        UA("UA"),
        UG("UG"),
        UM("UM"),
        US("US"),
        UY("UY"),
        UZ("UZ"),
        VA("VA"),
        VC("VC"),
        VE("VE"),
        VG("VG"),
        VI("VI"),
        VN("VN"),
        VU("VU"),
        WF("WF"),
        WS("WS"),
        YE("YE"),
        YT("YT"),
        ZA("ZA"),
        ZM("ZM"),
        ZW("ZW");
        private final String value;
        private static Map<String, Address.Country> constants = new HashMap<String, Address.Country>();

        static {
            for (Address.Country c: values()) {
                constants.put(c.value, c);
            }
        }

        private Country(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static Address.Country fromValue(String value) {
            Address.Country constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("addressLine1", "XX MASKED XX").append("addressLine2", "XX MASKED XX").append("city", "XX MASKED XX").append("postalCode", "XX MASKED XX").append("country", "XX MASKED XX").append("region", "XX MASKED XX").toString();
    }
}
