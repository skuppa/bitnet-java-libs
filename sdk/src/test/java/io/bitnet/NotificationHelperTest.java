/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet;

import com.google.common.collect.ImmutableMap;
import io.bitnet.core.notifications.BitnetNotificationHelper;
import io.bitnet.notifications.model.InvoiceNotification;
import io.bitnet.notifications.model.Notification;

import io.bitnet.notifications.model.OrderNotification;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static io.bitnet.core.notifications.NotificationSubscription.invoiceSubscriptionCredentials;
import static io.bitnet.core.notifications.NotificationSubscription.orderSubscriptionCredentials;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Tests for notification helper.
 */
public class NotificationHelperTest {

    BitnetNotificationHelper notificationHelper;

    @Before
    public void setup() {
        notificationHelper = Bitnet.notificationHelper(
                orderSubscriptionCredentials("dd8a326a-d28e-4e26-99cc-4758e858d647", "111111111122222222221111111111222222222211111111112222222222"),
                invoiceSubscriptionCredentials("1d7fd0e9-4727-4b12-a2a3-c9d7360f0918", "111111111122222222221111111111222222222211111111112222222222"));
    }


    @Test
    public void shouldVerifyOrderNotification() {
        String body = "{\"id\":\"fe1f0137-d1b7-40b3-9b54-b537f1af19fd\",\"date\":\"2015-05-07T10:02:16.705Z\",\"event\":\"order.stateChanged\"," +
                "\"notification\":{\"type\":\"order.notification\",\"subscriptionId\":\"5a27a84d-ce78-45e4-be31-24c24b8f6205\",\"order\":{\"id\":\"64410e0c-df10-4c60-b11a-51a29626dece\"," +
                "\"accountId\":\"f436aa6e-5d2f-43d6-9a99-0029a66a02f8\",\"state\":\"PAID\",\"reference\":\"null\",\"totalAmount\":\"55.12\",\"currency\":\"BBD\"}}}";

        Map headers = new ImmutableMap.Builder<String, String>()
                .put("Digest", "SHA-256=dfe6bca684d912b7f6d78cbefee9a2e39652ed32dea277da7fb65a51ddc08bf1")
                .put("Date", "Thu May 7 10:02:16 2015 GMT")
                .put("Authorization", "Signature keyId=\"dd8a326a-d28e-4e26-99cc-4758e858d647\"," +
                        "signature=\"NocQm6GZc4bcOQBFfBApMMZA0r2N0zOrnpm88SAJONw=\",headers=\"date digest\",algorithm=\"hmac-sha256\"")
                .build();

        notificationHelper.isVerifiedNotification(headers, body);

        assertThat(notificationHelper.isVerifiedNotification(headers, body), is(true));
    }

    @Test
    public void shouldVerifyInvoiceNotification() {
        String body = "{\"id\":\"b863dc42-b26e-4167-8cf7-39b87301efc9\",\"date\":\"2015-05-07T12:52:41.631Z\",\"event\":\"invoice.expired\",\"notification\":{\"type\":\"invoice.notification\",\"" +
                "subscriptionId\":\"48513b30-595a-47b1-b5a2-298d9e556b92\",\"invoice\":{\"id\":\"fa31c897-e66c-431e-a465-03daea7fab58\",\"accountId\":\"f436aa6e-5d2f-43d6-9a99-0029a66a02f8\",\"" +
                "orderId\":\"3917d2f4-ae5f-4dd5-8e9c-b936f0b94eee\",\"reference\":null,\"state\":\"OPEN\",\"amount\":\"0.12137101\",\"amountReceived\":\"0.00000000\",\"currency\":\"BTC\"," +
                "\"expired\":true}}}";

        Map headers = new ImmutableMap.Builder<String, String>()
                .put("Digest", "SHA-256=1d90568da9f2f7439118131d4bbe515e7d3efc9b45b0c988da8c8a25f51280b0")
                .put("Date", "Thu May 7 12:52:41 2015 GMT")
                .put("Authorization", "Signature keyId=\"1d7fd0e9-4727-4b12-a2a3-c9d7360f0918\",signature=\"IOgpR8eOm4twjDM3eDNjVaXJfZhsz0LQLOv4avXGDPQ=\",headers=\"date digest\"," +
                        "algorithm=\"hmac-sha256\"")
                .build();

        notificationHelper.isVerifiedNotification(headers, body);

        assertThat(notificationHelper.isVerifiedNotification(headers, body), is(true));
    }

    @Test
    public void shouldParseInvoiceNotification() throws IOException {
        String body = "{\"id\":\"b863dc42-b26e-4167-8cf7-39b87301efc9\",\"date\":\"2015-05-07T12:52:41.631Z\",\"event\":\"invoice.expired\",\"notification\":{\"type\":\"invoice.notification\",\"" +
                "subscriptionId\":\"48513b30-595a-47b1-b5a2-298d9e556b92\",\"invoice\":{\"id\":\"fa31c897-e66c-431e-a465-03daea7fab58\",\"accountId\":\"f436aa6e-5d2f-43d6-9a99-0029a66a02f8\",\"" +
                "orderId\":\"3917d2f4-ae5f-4dd5-8e9c-b936f0b94eee\",\"reference\":null,\"state\":\"OPEN\",\"amount\":\"0.12137101\",\"amountReceived\":\"0.00000000\",\"currency\":\"BTC\"," +
                "\"expired\":true}}}";

        assertThat(notificationHelper.getNotificationEventType(body), is(Notification.EventType.INVOICE_EXPIRED));

        Notification<InvoiceNotification> notification = notificationHelper.getInvoiceNotification(body);
        assertThat(notification.getNotification().getInvoice().getId(), is(equalTo("fa31c897-e66c-431e-a465-03daea7fab58")));
    }

    @Test
    public void shouldParseOrderNotification() throws IOException {
        String body = "{\"id\":\"919757d1-b890-4002-8fc1-d32c3f38276f\",\"date\":\"2015-05-07T12:37:52.082Z\",\"event\":\"order.stateChanged\",\"notification\":{\"type\":\"order.notification\"," +
                "\"subscriptionId\":\"5a27a84d-ce78-45e4-be31-24c24b8f6205\",\"order\":{\"id\":\"fe96e4bf-9d6e-4cc6-9039-c708f1722391\",\"accountId\":\"f436aa6e-5d2f-43d6-9a99-0029a66a02f8\"," +
                "\"state\":\"PAID\",\"reference\":\"null\",\"totalAmount\":\"55.12\",\"currency\":\"BBD\"}}}";

        assertThat(notificationHelper.getNotificationEventType(body), is(Notification.EventType.ORDER_STATE_CHANGED));

        Notification<OrderNotification> notification = notificationHelper.getOrderNotification(body);
        assertThat(notification.getNotification().getOrder().getId(), is(equalTo("fe96e4bf-9d6e-4cc6-9039-c708f1722391")));
    }


}
