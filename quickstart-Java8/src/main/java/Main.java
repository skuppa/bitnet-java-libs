import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.Uninterruptibles;
import feign.codec.EncodeException;
import io.bitnet.Bitnet;
import io.bitnet.core.exceptions.*;
import io.bitnet.core.notifications.BitnetNotificationHelper;
import io.bitnet.model.payer.payer.Address;
import io.bitnet.model.payer.payer.Payer;
import io.bitnet.model.payer.payer.PayerCreate;
import io.bitnet.model.payer.payer.PayerUpdate;
import io.bitnet.model.payment.invoice.Invoice;
import io.bitnet.model.payment.invoice.InvoiceCreate;
import io.bitnet.model.payment.order.Item;
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

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static io.bitnet.core.notifications.NotificationSubscription.*;
import static java.util.concurrent.TimeUnit.MINUTES;
import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.stop;

/**
 * Quick start example of using the Bitnet Java SDK.
 * <p/>
 * The Bitnet object wraps all the calls to the Bitnet API providing authentication management,
 * error handling, parsing of responses, retries, PPI safe logging.
 * <p/>
 * The bitnet notification helper is used to verify that notifications are valid and verified to be generated using your subscriptions key id and secret.
 * <p/>
 * Sparkframework http://www.sparkjava.com is used to create a endpoint at http://127.0.0.1:8888/webhook for receiving notifications.
 * N.B. If you are running this behind a firewall consider using http://ngrok.com to tunnel.
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
        checkPreconditions();

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

        // Opens port 8888 to start listening to requests.
        startListeningOnPort(8888);

        // Registered to receive webhooks on 127.0.0.1:8888/webhook
        startNotificationsWebhook();

        Payer payer = createPayer();
        System.out.println("Created Payer " + payer);

        Payer updatedPayer = updatePayer(payer);
        System.out.println("Updated Payer " + updatedPayer);

        Payer retrievedPayer = retrievePayer(payer.getId());


        Order order = createOrder(payer);
        System.out.println("Created Order " + order);

        Invoice invoice = createInvoice(order);
        System.out.println("Created Invoice " + invoice);

        Refund refund = createRefund(invoice);
        System.out.println("Created refund " + refund);


        // Wait for 5 minutes so all notification are received before ending program.
        System.out.println("Waiting for notification events for 5 minutes");
        waitFor(5, MINUTES);
        closeNotificationsWebhook();
    }

    private static Payer createPayer() {
       /*
        * Build a payer address
        */
        Address payerAddress = new Address()
                .withAddressLine1("9 test street")
                .withAddressLine2("test avenue")
                .withCity("testville")
                .withRegion("Illinois")
                .withPostalCode("60606")
                .withCountry(Address.Country.US);

        /*
         * Build a payer with an address, reference and refund payment address.
         * @param YOUR_UNIQUE_REFERENCE This is an unique identifier of your choosing.
         *        If you submit two new payers with the same reference you will get a BitnetConflictException.
         * @param REFUND_PAYMENT_ADDRESS This can be populated at time of creation, or
         *        updated at a later date. A refund payment address must be set in order to initiate a refund for a Payer.
         */
        PayerCreate newPayer = new PayerCreate()
                .withAccountId(ACCOUNT_ID)
                .withEmail("thePayersEmailAddress@email.com")
                .withReference(UUID.randomUUID().toString()) // A random reference Id
                        //.withRefundPaymentAddress(REFUND_PAYMENT_ADDRESS)
                .withAddress(payerAddress);

        /*
         * Call the BITNET service to create the prayer.
         */
        return call(() -> bitnet.payerService().createPayer(newPayer)).orElse(null);
    }

    private static Payer updatePayer(Payer payer) {
       /* Build a PayerUpdate object.
        * All payer information, apart from the Payer Id, can be updated.
        * IMPORTANT: All previous payer information must be supplied, otherwise this
        *            payer information will be overwritten.
        */
        PayerUpdate payerToUpdate = new PayerUpdate(payer).withEmail("updated@email.com");

        /*
         * Calling the BITNET update payer service.
         */
        return call(() -> bitnet.payerService().updatePayer(payerToUpdate, payer.getId())).orElse(null);
    }

    /**
     * Calling the BITNET service to get the payer
     *
     * @param payerId The id of the payer to be retrieved.
     * @return the retrieved payer
     */
    private static Payer retrievePayer(String payerId) {

        return call(() -> bitnet.payerService().getPayer(payerId)).orElse(null);
    }

    private static Order createOrder(Payer createdPayer) {
        if (createdPayer == null) {
            System.out.println("Unable to create order for null payer.");
            return null;
        }

        /*
         * Build a list of items.
         */
        List<Item> items = new ArrayList<>();
        items.add(new Item()
                .withDesc("item 1")
                .withName("item 1 name")
                .withPrice("2.99")
                .withQuantity(1)
                .withSku("sku 1"));

        /*
         * Build an order object with some items and a description
         */
        OrderCreate newOrder = new OrderCreate()
                .withAccountId(ACCOUNT_ID)
                .withPayerId(createdPayer.getId())
                .withCurrency(Order.Currency.USD)
                .withTotalAmount("10.00")
                .withDesc("A test order")
                .withItems(items);

        /*
         * Call the BITNET service to create the order.
         */
        return call(() -> bitnet.orderService().createOrder(newOrder)).orElse(null);
    }

    private static Invoice createInvoice(Order createdOrder) {
        if (createdOrder == null) {
            System.out.println("Unable to create invoice for null order.");
            return null;
        }

        /*
         * Build an InvoiceCreate object.
         * @EXISTING_ORDER_ID The id of an existing and open ORDER.
         * Note: An order can only be linked to one invoice.
         */
        InvoiceCreate newInvoice = new InvoiceCreate()
                .withAccountId(ACCOUNT_ID)
                .withOrderId(createdOrder.getId());


        /*
         * Call the BITNET service to create the invoice.
         */
        return call(() -> bitnet.invoiceService().createInvoice(newInvoice)).orElse(null);
    }

    private static Refund createRefund(Invoice createdInvoice) {
        if (createdInvoice == null) {
            System.out.println("Unable to create refund for null invoice.");
            return null;
        }

        return call(() -> bitnet.refundService().createRefund(new RefundCreate()
                .withAccountId(ACCOUNT_ID)
                .withAmount("10.00")
                .withCurrency(Requested.Currency.BBD)
                .withInstruction(Refund.Instruction.PARTIAL)
                .withInvoiceId(createdInvoice.getId()))).orElse(null);
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
            e.printStackTrace();
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