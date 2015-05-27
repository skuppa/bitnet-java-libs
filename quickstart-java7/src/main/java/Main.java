import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.Uninterruptibles;
import feign.codec.EncodeException;
import io.bitnet.Bitnet;
import io.bitnet.core.exceptions.*;
import io.bitnet.core.notifications.BitnetNotificationHelper;
import io.bitnet.model.payer.Payer;
import io.bitnet.model.payer.PayerCreate;
import io.bitnet.model.payment.Invoice;
import io.bitnet.model.payment.InvoiceCreate;
import io.bitnet.model.payment.Order;
import io.bitnet.model.payment.OrderCreate;
import io.bitnet.model.payment.Orders;
import io.bitnet.model.refund.Refund;
import io.bitnet.model.refund.RefundCreate;
import io.bitnet.notifications.model.InvoiceNotification;
import io.bitnet.notifications.model.Notification;
import io.bitnet.notifications.model.OrderNotification;
import io.bitnet.notifications.model.RefundNotification;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.bitnet.core.notifications.NotificationSubscription.*;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MINUTES;
import static spark.Spark.post;
import static spark.Spark.setPort;

/**
 * Quick start example of using the Bitnet Java SDK.
 * <p/>
 * The Bitnet object wraps all the calls to the Bitnet API providing authentication management,
 * error handling, parsing of responses, retries, PPI safe logging.
 * <p/>
 * The bitnet notification helper is used to verify that notifications are valid and verified to be generated using your subscriptions key id and secret.
 * <p/>
 * Sparkframework http://www.sparkjava.com is used to create a endpoint at http://127.0.0.1:8888/webhook for receiving notifications.
 * N.B. If you are running this behind a firewall consider using http://ngrok.com to tunnel notifications.
 * The application will wait for 5  minutes before shutting down so that some notifications are relieved.
 * <p/>
 * After the example has setup the above it goes on to create a payer, order, invoice before finally attempting to create a refund.
 * <p/>
 * See https://github.com/bitnet/bitnet-java-libs for full documentation.
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
        // To connect to production you would use:
        //bitnet = Bitnet.start(CLIENT_ID, SECRET);

        // To connect to test you would use:
        // bitnet = Bitnet.startTest(CLIENT_ID, SECRET);

        // Advanced usage to connect to a specific environment use:
        bitnet = Bitnet.start(CLIENT_ID, SECRET, ENVIRONMENT, io.bitnet.Blockchain.TESTNET, feign.Logger.Level.FULL, io.bitnet.feign.OkHttpClientProvider.unsafeOkHttpClient());

        // Create notification helper configured with all your subscription key ids and secrets.
        notificationHelper = Bitnet.notificationHelper(
                orderSubscriptionCredentials(ORDER_NOTIFICATION_SUBSCRIPTION_KEY_ID, ORDER_NOTIFICATION_SUBSCRIPTION_SECRET),
                invoiceSubscriptionCredentials(INVOICE_NOTIFICATION_SUBSCRIPTION_KEY_ID, INVOICE_NOTIFICATION_SUBSCRIPTION_SECRET),
                refundSubscriptionCredentials(REFUND_NOTIFICATION_SUBSCRIPTION_KEY_ID, REFUND_NOTIFICATION_SUBSCRIPTION_SECRET));

        startListeningOnPort(8888);
        // Registered to receive webhooks on 127.0.0.1:8888/webhook
        startNotificationsWebhook();

        try {
            Payer payer = createPayer();
            System.out.println("Created Payer " + payer);

            Order order = createOrder(payer);
            System.out.println("Created Order " + order);

            Orders openOrders = retrieveOpenOrders();
            System.out.println("Open Orders" + openOrders);

            Invoice invoice = createInvoice(order);
            System.out.println("Created Invoice " + invoice);

            Refund refund = createRefund(invoice, Refund.Instruction.FULL);
            System.out.println("Created Refund " + refund);

        } catch (BitnetException | BitnetRetryableException | EncodeException e) {
            handleBitnetException(e);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // Wait for 5 minutes so all notification are received before ending program.
        System.out.println("Waiting for notification events for 5 minutes");
        waitFor(5, MINUTES);
        closeNotificationsWebhook();
    }

    /**
     * Create a new payer.
     * See https://github.com/bitnet/bitnet-java-libs#payer for more details.
     */
    private static Payer createPayer() {
        PayerCreate newPayer = new PayerCreate()
                .withAccountId(ACCOUNT_ID)
                .withEmail("thePayersEmailAddress@email.com");

        return bitnet.payerService().createPayer(newPayer);
    }

    /**
     * Create a new order.
     * See https://github.com/bitnet/bitnet-java-libs#order for more details.
     */
    private static Order createOrder(Payer createdPayer) {
        OrderCreate newOrder = new OrderCreate()
                .withAccountId(ACCOUNT_ID)
                .withPayerId(createdPayer.getId())
                .withCurrency(Order.Currency.USD)
                .withTotalAmount("10.00")
                .withDesc("A test order");

        return bitnet.orderService().createOrder(newOrder);
    }

    /**
     * Retrieve list of open orders.
     * See https://github.com/bitnet/bitnet-java-libs#order for more details.
     */
    private static Orders retrieveOpenOrders() {
        return bitnet.orderService().getOrders(ACCOUNT_ID, asList(Order.State.OPEN), 0, 100);
    }

    /**
     * Create a new invoice.
     * See https://github.com/bitnet/bitnet-java-libs#invoices for more details.
     */
    private static Invoice createInvoice(Order createdOrder) {
        InvoiceCreate newInvoice = new InvoiceCreate()
                .withAccountId(ACCOUNT_ID)
                .withOrderId(createdOrder.getId());

        return bitnet.invoiceService().createInvoice(newInvoice);
    }

    /**
     * Create a new invoice.
     * See https://github.com/bitnet/bitnet-java-libs#refunds for more details.
     */
    private static Refund createRefund(Invoice createdInvoice, Refund.Instruction instruction) {
        RefundCreate newRefund = new RefundCreate()
                .withAccountId(ACCOUNT_ID)
                .withInstruction(instruction)
                .withInvoiceId(createdInvoice.getId());

        return bitnet.refundService().createRefund(newRefund);
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
            System.out.println("Access denied: " + e.getMessage());
        } catch (BitnetInvalidRequestException e) {
            // The request was invalid
            // This can be due to invalid request formatting and so on.
            System.out.println("Invalid Request: " + e.getMessage());
        } catch (BitnetRequestCouldNotBeProcessedException e) {
            // The request could not be processed.
            // An example would be missing required fields.
            System.out.println("Request could not be processed: " + e.getMessage());
        } catch (EncodeException e) {
            // These exceptions may occur as result of the passed object not obeying it's
            // constraints.
            System.out.println("Unable to encode object: " + e.getMessage());
        } catch (BitnetConflictException e) {
            // An example of a conflict would be an attempt to create an entity which already
            // exists. Are you creating a duplicate payer, etc?
            System.out.println("Conflicting entity: " + e.getMessage());
        } catch (BitnetRequestForbiddenException e) {
            // You do not have permission for the requested action.
            // Check that you are using the correct accountId and have the appropriate permissions.
            System.out.println("Forbidden request: " + e.getMessage());
        } catch (BitnetResourceNotFoundException e) {
            // Check that the resource you are actioning exists, e.g. is the Payer Id valid?
            System.out.println("Resource not found: " + e.getMessage());
        } catch (BitnetException e) {
            // This will be thrown for errors when a more specific error cannot be identified.
            System.out.println("Unknown bitnet exception: " + e.getMessage());
        }
    }

    private static void waitFor(int interval, TimeUnit units) {
        Uninterruptibles.sleepUninterruptibly(interval, units);
    }

    private static void startListeningOnPort(int port) {
        setPort(port);
    }

    private static void closeNotificationsWebhook() {
        System.exit(0);
    }

    /**
     * Start listening for and handling notifications using spark framework.
     */
    private static void startNotificationsWebhook() {
        post(new Route("/webhook") {
            @Override
            public Object handle(Request request, Response response) {
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
            }
        });

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

    private static void handleInvoiceExpired(Notification<InvoiceNotification> notification) {
        Invoice invoice = notification.getNotification().getInvoice();
        System.out.format("Invoice %s for order %s has expired with %s received\n", invoice.getId(), invoice.getOrderId(), invoice.getAmountReceived());
    }

    private static void handleInvoicePaymentReceived(Notification<InvoiceNotification> notification) {
        Invoice invoice = notification.getNotification().getInvoice();
        System.out.format("Invoice %s for order %s has received a payment, so far %s has been received\n", invoice.getId(), invoice.getOrderId(), invoice.getAmountReceived());
    }

    private static void handleInvoiceStateChange(Notification<InvoiceNotification> notification) {
        Invoice invoice = notification.getNotification().getInvoice();
        System.out.format("Invoice %s for order %s has changed state to %s\n", invoice.getId(), invoice.getOrderId(), invoice.getState());
    }

    private static void handleOrderStateChange(Notification<OrderNotification> notification) {
        Order order = notification.getNotification().getOrder();
        System.out.format("Order %s has changed state to %s\n", order.getId(), order.getState());
    }

    private static void handleRefundStateChange(Notification<RefundNotification> notification) {
        Refund refund = notification.getNotification().getRefund();
        System.out.format("Refund %s for invoice %s has changed state to %s\n", refund.getId(), refund.getInvoiceId(), refund.getState());
    }
}