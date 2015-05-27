
package io.bitnet.model.token;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * Token
 * <p>
 * Oauth Token
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "_links",
    "access_token",
    "expires_in",
    "jti",
    "scope",
    "token_type"
})
public class Token {

    /**
     * 
     */
    @JsonProperty("_links")
    @Valid
    private io.bitnet.model.token.Links Links;
    /**
     * Access token that grants access to the API
     * 
     */
    @JsonProperty("access_token")
    private String accessToken;
    /**
     * XXX
     * 
     */
    @JsonProperty("expires_in")
    private Double expiresIn;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("jti")
    @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")
    @NotNull
    private String jti;
    /**
     * XXX
     * (Required)
     * 
     */
    @JsonProperty("scope")
    @Pattern(regexp = "([a-z]+\\.[a-z]+)")
    @NotNull
    private String scope;
    /**
     * XXX
     * 
     */
    @JsonProperty("token_type")
    private Token.TokenType tokenType;

    /**
     * 
     * @return
     *     The Links
     */
    @JsonProperty("_links")
    public io.bitnet.model.token.Links getLinks() {
        return Links;
    }

    /**
     * 
     * @param Links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(io.bitnet.model.token.Links Links) {
        this.Links = Links;
    }

    public Token withLinks(io.bitnet.model.token.Links Links) {
        this.Links = Links;
        return this;
    }

    /**
     * Access token that grants access to the API
     * 
     * @return
     *     The accessToken
     */
    @JsonProperty("access_token")
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Access token that grants access to the API
     * 
     * @param accessToken
     *     The access_token
     */
    @JsonProperty("access_token")
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Token withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * XXX
     * 
     * @return
     *     The expiresIn
     */
    @JsonProperty("expires_in")
    public Double getExpiresIn() {
        return expiresIn;
    }

    /**
     * XXX
     * 
     * @param expiresIn
     *     The expires_in
     */
    @JsonProperty("expires_in")
    public void setExpiresIn(Double expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Token withExpiresIn(Double expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The jti
     */
    @JsonProperty("jti")
    public String getJti() {
        return jti;
    }

    /**
     * 
     * (Required)
     * 
     * @param jti
     *     The jti
     */
    @JsonProperty("jti")
    public void setJti(String jti) {
        this.jti = jti;
    }

    public Token withJti(String jti) {
        this.jti = jti;
        return this;
    }

    /**
     * XXX
     * (Required)
     * 
     * @return
     *     The scope
     */
    @JsonProperty("scope")
    public String getScope() {
        return scope;
    }

    /**
     * XXX
     * (Required)
     * 
     * @param scope
     *     The scope
     */
    @JsonProperty("scope")
    public void setScope(String scope) {
        this.scope = scope;
    }

    public Token withScope(String scope) {
        this.scope = scope;
        return this;
    }

    /**
     * XXX
     * 
     * @return
     *     The tokenType
     */
    @JsonProperty("token_type")
    public Token.TokenType getTokenType() {
        return tokenType;
    }

    /**
     * XXX
     * 
     * @param tokenType
     *     The token_type
     */
    @JsonProperty("token_type")
    public void setTokenType(Token.TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public Token withTokenType(Token.TokenType tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(Links).append(accessToken).append(expiresIn).append(jti).append(scope).append(tokenType).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Token) == false) {
            return false;
        }
        Token rhs = ((Token) other);
        return new EqualsBuilder().append(Links, rhs.Links).append(accessToken, rhs.accessToken).append(expiresIn, rhs.expiresIn).append(jti, rhs.jti).append(scope, rhs.scope).append(tokenType, rhs.tokenType).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum TokenType {

        BEARER("bearer");
        private final String value;
        private static Map<String, Token.TokenType> constants = new HashMap<String, Token.TokenType>();

        static {
            for (Token.TokenType c: values()) {
                constants.put(c.value, c);
            }
        }

        private TokenType(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static Token.TokenType fromValue(String value) {
            Token.TokenType constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Links", Links).append("accessToken", accessToken).append("expiresIn", expiresIn).append("jti", jti).append("scope", scope).append("tokenType", tokenType).toString();
    }
}
