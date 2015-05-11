import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.Uninterruptibles;
import feign.Logger;
import feign.codec.EncodeException;
import io.bitnet.Bitnet;
import io.bitnet.Blockchain;
import io.bitnet.core.exceptions.*;
import io.bitnet.core.notifications.BitnetNotificationHelper;
import io.bitnet.feign.OkHttpClientProvider;
import io.bitnet.model.payer.payer.Payer;
import io.bitnet.model.payer.payer.PayerCreate;
import io.bitnet.model.payment.invoice.Invoice;
import io.bitnet.model.payment.invoice.InvoiceCreate;
import io.bitnet.model.payment.order.Order;
import io.bitnet.model.payment.order.OrderCreate;
import io.bitnet.model.refund.refund.Refund;
import io.bitnet.model.refund.refund.RefundCreate;
import io.bitnet.model.refund.refund.Requested;
import io.bitnet.notifications.model.InvoiceNotification;
import io.bitnet.notifications.model.Notification;
import io.bitnet.notifications.model.OrderNotification;
import io.bitnet.notifications.model.RefundNotification;
import org.apache.commons.lang3.StringUtils;
import spark.Request;
import spark.Response;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static io.bitnet.core.notifications.NotificationSubscription.*;
import static java.util.concurrent.TimeUnit.MINUTES;
import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.stop;

/**
 * Main class for demonstrating usage.
 */
public class Main {
    // Client id for your bitnet account in selected environment
    private static final String CLIENT_ID = "";
    // Account id for your bitnet account in selected environment
    private static final String ACCOUNT_ID = "";
    // Secret for your bitnet account in selected environment
    private static final String SECRET = "";
    // Environment you wish to run against (assuming you are using the advanced start method)
    private static final String ENVIRONMENT = "";

    // Your order notification subscription key id and secret for your account
    private static final String ORDER_NOTIFICATION_SUBSCRIPTION_KEY_ID = "";
    private static final String ORDER_NOTIFICATION_SUBSCRIPTION_SECRET = "";

    // Your invoice notification subscription key id and secret for your account
    private static final String INVOICE_NOTIFICATION_SUBSCRIPTION_KEY_ID = "";
    private static final String INVOICE_NOTIFICATION_SUBSCRIPTION_SECRET = "";

    // Your refund notification subscription key id and secret for your account
    private static final String REFUND_NOTIFICATION_SUBSCRIPTION_KEY_ID = "";
    private static final String REFUND_NOTIFICATION_SUBSCRIPTION_SECRET = "";

    private static Bitnet bitnet;
    private static BitnetNotificationHelper notificationHelper;


    public static void main(String... args) {
        checkPreconditions();

        // To connect to production you would use:
        bitnet = Bitnet.start(CLIENT_ID, SECRET);

        // To connect to test you would use:
        // bitnet = Bitnet.startTest(CLIENT_ID, SECRET);

        // Advanced usage to connect to a specific environment use:
        // bitnet = Bitnet.start(CLIENT_ID, SECRET, ENVIRONMENT, Blockchain.TESTNET, Logger.Level.FULL, OkHttpClientProvider.unsafeOkHttpClient());

        // Create notification helper configured with all your subscription key ids.
        // If you are running this behind a firewall consider using an http://ngrok.com tunnel as the notification in your subscriptions.
        notificationHelper = Bitnet.notificationHelper(
                orderSubscriptionCredentials(ORDER_NOTIFICATION_SUBSCRIPTION_KEY_ID, ORDER_NOTIFICATION_SUBSCRIPTION_SECRET),
                invoiceSubscriptionCredentials(INVOICE_NOTIFICATION_SUBSCRIPTION_KEY_ID, INVOICE_NOTIFICATION_SUBSCRIPTION_SECRET),
                refundSubscriptionCredentials(REFUND_NOTIFICATION_SUBSCRIPTION_KEY_ID, REFUND_NOTIFICATION_SUBSCRIPTION_SECRET));

        // Opens port 8888 to start listening to requests.
        startListeningOnPort(8888);

        // Registered to receive webhooks on 127.0.0.1:8888/webhook
        startNotificationsWebhook();

        // Create a payer, order, invoice and refund against the bitnet api
        callBitnetAPI(bitnet);

        // Wait for 5 minutes so all notification are received before ending program.
        System.out.println("Waiting for notification events for 5 minutes");
        waitFor(5, MINUTES);
        closeNotificationsWebhook();
    }


    private static void callBitnetAPI(Bitnet bitnet) {
        Payer createdPayer = call(() -> bitnet.payerService().createPayer(
                new PayerCreate()
                        .withAccountId(ACCOUNT_ID)
                        .withEmail("payer.1@bitnet.io")
                        .withReference(UUID.randomUUID().toString()))).orElse(null);

        if (createdPayer == null) {
            System.out.println("Unable to create payer");
            return;
        }


        Order createdOrder = call(() -> bitnet.orderService().createOrder(
                new OrderCreate()
                        .withAccountId(ACCOUNT_ID)
                        .withPayerId(createdPayer.getId())
                        .withCurrency(Order.Currency.BBD)
                        .withTotalAmount("55.12"))).orElse(null);

        if (createdOrder == null) {
            System.out.println("Unable to create order");
            return;
        }

        Invoice createdInvoice = call(() -> bitnet.invoiceService().createInvoice(
                new InvoiceCreate()
                        .withAccountId(ACCOUNT_ID)
                        .withOrderId(createdOrder.getId()))).orElse(null);

        if (createdInvoice == null) {
            System.out.println("Unable to create invoice");
            return;
        }

        // The next call will fail as the invoice will still be open
        // User of the SDK need to catch and handle exceptions, see below.

        Optional<Refund> refund = call(() -> bitnet.refundService().createRefund(new RefundCreate()
                .withAccountId(ACCOUNT_ID)
                .withAmount("10.00")
                .withCurrency(Requested.Currency.BBD)
                .withInstruction(Refund.Instruction.PARTIAL)
                .withInvoiceId(createdInvoice.getId())));

        System.out.println(refund);
    }


    /**
     * Handles bitnet exceptions logging out any exceptions which have occurred.
     *
     * @param exception exception to handle and log
     */
    private static void handleBitnetException(RuntimeException exception) {
        try {
            throw exception;
        } catch (BitnetAccessDeniedException e) {
            // Authentication with the Bitnet API failed
            // Check that your Client Id, Secret and Account Id are correct.
            System.out.println("Access denied");
        } catch (BitnetInvalidRequestException e) {
            // The request was invalid
            // This can be due to invalid request formatting and so on.
            System.out.println("Invalid Request");
        } catch (BitnetRequestCouldNotBeProcessedException e) {
            // The request could not be processed.
            // An example would be missing required fields.
            System.out.println("Request could not be processed");
        } catch (EncodeException e) {
            // These exceptions may occur as result of the passed object not obeying it's
            // constraints.
            System.out.println("Unable to encode object");
        } catch (BitnetConflictException e) {
            // An example of a conflict would be an attempt to create an entity which already
            // exists. Are you creating a duplicate payer, etc?
            System.out.println("Conflicting entity");
        } catch (BitnetRequestForbiddenException e) {
            // You do not have permission for the requested action.
            // Check that you are using the correct accountId and have the appropriate permissions.
            System.out.println("Forbidden request");
        } catch (BitnetResourceNotFoundException e) {
            // Check that the resource you are actioning exists, e.g. is the Payer Id valid?
            System.out.println("Resource not found");
        } catch (BitnetException e) {
            // This will be thrown for errors when a more specific error cannot be identified.
            System.out.println("Unknown bitnet exception");
        }
    }

    /**
     * Safely make request, logging any exceptions and returning an optional object.
     *
     * @param call request
     * @param <T>  return type
     * @return object wrapped as optional
     */
    private static <T> Optional<T> call(Supplier<T> call) {
        try {
            return Optional.of(call.get());
        } catch (BitnetException | BitnetRetryableException | EncodeException e) {
            handleBitnetException(e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return Optional.ofNullable(null);
    }

    /**
     * Start listening for and handling notifications.
     */
    private static void startNotificationsWebhook() {
        post("/webhook", (request, response) -> {
            Map headers = new ImmutableMap.Builder<String, String>()
                    .put("Digest", request.headers("Digest"))
                    .put("Date", request.headers("Date"))
                    .put("Authorization", request.headers("Authorization"))
                    .build();
            if (notificationHelper.isVerifiedNotification(headers, request.body())) {
                return handleNotification(request);
            } else {
                return badRequestResponse(request, response, headers);
            }
        });
    }

    /**
     * Process notifications and handle as appropriate.
     *
     * @param request request object
     * @return body of response
     */
    private static Object handleNotification(Request request) {
        switch (notificationHelper.getNotificationEventType(request.body())) {
            case INVOICE_EXPIRED:
                handleInvoiceExpired(notificationHelper.getInvoiceNotification(request.body()));
                break;
            case INVOICE_PAYMENT_RECEIVED:
                handleInvoicePaymentReceived(notificationHelper.getInvoiceNotification(request.body()));
                break;
            case INVOICE_STATE_CHANGED:
                handleInvoiceStateChange(notificationHelper.getInvoiceNotification(request.body()));
                break;
            case ORDER_STATE_CHANGED:
                handleOrderStateChange(notificationHelper.getOrderNotification(request.body()));
                break;
            case REFUND_STATE_CHANGED:
                handleRefundStateChange(notificationHelper.getRefundNotification(request.body()));
                break;
        }
        return "Notification received";
    }

    /**
     * Respond to request indicating that the response is bad.
     *
     * @param request  request object
     * @param response response object
     * @param headers  map of headers
     * @return body of response
     */
    private static Object badRequestResponse(Request request, Response response, Map headers) {
        response.status(400);
        return notificationHelper.rejectNotificationReason(headers, request.body());
    }

    /**
     * Wait for a period of time before continuing.
     *
     * @param interval interval to wait for
     * @param units    unit of interval
     */
    private static void waitFor(int interval, TimeUnit units) {
        Uninterruptibles.sleepUninterruptibly(interval, units);
    }

    /**
     * Start listening for requests on port.
     *
     * @param port to listen on for requests
     */
    private static void startListeningOnPort(int port) {
        port(port);
    }

    /**
     * Stop listening for web hook notifications.
     */
    public static void closeNotificationsWebhook() {
        stop();
    }


    /**
     * Handles invoice expired event.
     *
     * @param notification invoice notification
     */
    private static void handleInvoiceExpired(Notification<InvoiceNotification> notification) {
        Invoice invoice = notification.getNotification().getInvoice();
        System.out.format("Invoice %s for order %s has expired with %s received\n", invoice.getId(), invoice.getOrderId(), invoice.getAmountReceived());
    }


    /**
     * Handles invoice payment received event.
     *
     * @param notification invoice notification
     */
    private static void handleInvoicePaymentReceived(Notification<InvoiceNotification> notification) {
        Invoice invoice = notification.getNotification().getInvoice();
        System.out.format("Invoice %s for order %s has received a payment, so far %s has been received\n", invoice.getId(), invoice.getOrderId(), invoice.getAmountReceived());
    }


    /**
     * Handles invoice state change event.
     *
     * @param notification invoice notification
     */
    private static void handleInvoiceStateChange(Notification<InvoiceNotification> notification) {
        Invoice invoice = notification.getNotification().getInvoice();
        System.out.format("Invoice %s for order %s has changed state to %s\n", invoice.getId(), invoice.getOrderId(), invoice.getState());
    }


    /**
     * Handles order state change event.
     *
     * @param notification order notification
     */
    private static void handleOrderStateChange(Notification<OrderNotification> notification) {
        Order order = notification.getNotification().getOrder();
        System.out.format("Order %s has changed state to %s\n", order.getId(), order.getState());
    }

    /**
     * Handles refund state change event.
     *
     * @param notification refund notification
     */
    private static void handleRefundStateChange(Notification<RefundNotification> notification) {
        Refund refund = notification.getNotification().getRefund();
        System.out.format("Refund %s for invoice %s has changed state to %s\n", refund.getId(), refund.getInvoiceId(), refund.getState());
    }

    /**
     * Check that mandatory constants are populated.
     */
    private static void checkPreconditions() {
        if (StringUtils.isBlank(CLIENT_ID) || StringUtils.isBlank(ACCOUNT_ID) || StringUtils.isBlank(SECRET)) {
            throw new RuntimeException("Unable to start bitnet sdk due to missing credentials, please update constants in Main class");
        }
    }

}