
package io.bitnet.notifications.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;


/**
 * eventNotificationSchema
 * <p>
 * A notification published by the Bitnet payments platform
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "id",
        "date",
        "event",
        "notification"
})
public class Notification<T> {

    /**
     * Unique identifier used for all key entities within the platform
     */
    @JsonProperty("id")
    @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")
    private String id;
    @JsonProperty("date")
    @Pattern(regexp = "^\\d{4}-[0-1][0-9]-[0-3]\\d{1}T[0-2]\\d{1}:[0-5]\\d{1}:[0-5]\\d{1}[.]\\d{1,5}Z$")
    private String date;
    /**
     * The name of the event that triggered the notification
     */
    @JsonProperty("event")
    @Pattern(regexp = "^[a-zA-Z_0-9\\.]{3,50}\\.[a-zA-Z_0-9\\.]{3,50}$")
    private String event;
    /**
     *
     */
    @JsonProperty("notification")
    private T notification;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Unique identifier used for all key entities within the platform
     *
     * @return The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Unique identifier used for all key entities within the platform
     *
     * @param id The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Notification withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The date
     */
    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    public Notification withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * The name of the event that triggered the notification
     *
     * @return The event
     */
    @JsonProperty("event")
    public String getEvent() {
        return event;
    }

    /**
     * The name of the event that triggered the notification
     *
     * @param event The event
     */
    @JsonProperty("event")
    public void setEvent(String event) {
        this.event = event;
    }

    public Notification withEvent(String event) {
        this.event = event;
        return this;
    }

    /**
     * @return The notification
     */
    @JsonProperty("notification")
    public T getNotification() {
        return notification;
    }

    /**
     * @param notification The notification
     */
    @JsonProperty("notification")
    public void setNotification(T notification) {
        this.notification = notification;
    }

    public Notification withNotification(T notification) {
        this.notification = notification;
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

    public Notification withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }



    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(date).append(event).append(notification).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Notification) == false) {
            return false;
        }
        Notification rhs = ((Notification) other);
        return new EqualsBuilder().append(id, rhs.id).append(date, rhs.date).append(event, rhs.event).append(notification, rhs.notification).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("date", date).append("event", event).append("notification", notification).append("additionalProperties", additionalProperties).toString();
    }
}
