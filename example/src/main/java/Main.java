import com.google.common.collect.ImmutableMap;
import feign.Logger;
import feign.codec.EncodeException;
import io.bitnet.Bitnet;
import io.bitnet.Blockchain;
import io.bitnet.core.exceptions.*;
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
import org.apache.commons.lang3.StringUtils;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;
import java.util.UUID;

import static spark.Spark.*;

/**
 * Main class for demonstrating usage.
 */
public class Main {
    // Update the following constants with your credentials.
    public static final String CLIENT_ID = "";
    public static final String ACCOUNT_ID = "";
    public static final String SECRET = "";
    public static final String ENVIRONMENT = "";

    private static final String ORDER_NOTIFICATION_SUBSCRIPTION_KEY_ID = "";
    private static final String ORDER_NOTIFICATION_SUBSCRIPTION_SECRET = "";

    private static final String INVOICE_NOTIFICATION_SUBSCRIPTION_KEY_ID = "";
    private static final String INVOICE_NOTIFICATION_SUBSCRIPTION_SECRET = "";

    private static final String REFUND_NOTIFICATION_SUBSCRIPTION_KEY_ID = "";
    private static final String REFUND_NOTIFICATION_SUBSCRIPTION_SECRET = "";

    public static void main(String... args) {
        if (StringUtils.isBlank(CLIENT_ID) || StringUtils.isBlank(ACCOUNT_ID) || StringUtils.isBlank(SECRET) || StringUtils.isBlank(ENVIRONMENT)) {
            throw new RuntimeException("Unable to start bitnet sdk due to missing credentials, please update constants in Main class");
        }

        Bitnet bitnet = Bitnet.start(CLIENT_ID, SECRET, ENVIRONMENT, Blockchain.TESTNET, Logger.Level.FULL, OkHttpClientProvider.unsafeOkHttpClient());

        // To connect to UAT you would use:
        // Bitnet.startTest(CLIENT_ID, SECRET);

        // To connect to production you would use:
        // Bitnet bitnet = Bitnet.start(CLIENT_ID, SECRET);

        Payer createdPayer = bitnet.payerService().createPayer(
                new PayerCreate()
                        .withAccountId(ACCOUNT_ID)
                        .withEmail("payer.1@bitnet.io")
                        .withReference(UUID.randomUUID().toString()));

        Order createdOrder = bitnet.orderService().createOrder(
                new OrderCreate()
                        .withAccountId(ACCOUNT_ID)
                        .withPayerId(createdPayer.getId())
                        .withCurrency(Order.Currency.BBD)
                        .withTotalAmount("55.12"));

        Invoice createdInvoice = bitnet.invoiceService().createInvoice(
                new InvoiceCreate()
                        .withAccountId(ACCOUNT_ID)
                        .withOrderId(createdOrder.getId()));

        // The next call will fail as the invoice will still be open
        // User of the SDK need to catch and handle exceptions, see below.

        Refund refund = null;
        try {
            bitnet.refundService().createRefund(new RefundCreate()
                    .withAccountId(ACCOUNT_ID)
                    .withAmount("10.00")
                    .withCurrency(Requested.Currency.BBD)
                    .withInstruction(Refund.Instruction.PARTIAL)
                    .withInvoiceId(createdInvoice.getId()));
        } catch (RuntimeException e) {
            handleBitnetException(e);
        }

        System.out.println(refund);
    }

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


    private static void registerWebhookForNotifications(Bitnet bitnet) {
        setPort(8888);
        get(new Route("/healthCheck") {
            @Override
            public Object handle(Request request, Response response) {
                return "{\"status\" : \"healthy\"}";
            }
        });

        post(new Route("/webhook") {
            @Override
            public Object handle(Request request, Response response) {
                Map headers = new ImmutableMap.Builder<String, String>()
                        .put("Digest", request.headers("Digest"))
                        .put("Date", request.headers("Date"))
                        .put("Authorization", request.headers("Authorization"))
                        .build();





                return "Notification received";
            }
        });
    }

}