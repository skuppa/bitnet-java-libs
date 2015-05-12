# Bitnet Java SDK


[Bitnet](https://bitnet.io/) is an enterprise grade digital commerce platform delivering the security, reliability and scalability demanded by global businesses. There are a number of ways of integrating with the platform; The Java SDK is one of the integrations. You can find full details on the platform and all integrations in the [implementation guide](https://developer.bitnet.io/implementation_guide).

The following document should provide all the information you need to get up and running with the SDK.

## Prerequisites

### Bitnet Credentials

Bitnet provide production and test environments. Before using any of their services you must have a registered account for that environment. Once registered you will be provided with the following key pieces of information required to use the SDK:

|        | Notes |
| -----------| ----------------------------------------------------- |
| Account Id | Your Bitnet Account Id for the environment.
| Client Id  | Your Bitnet Client Id for the environment.
| Secret     | Your Bitnet Secret for the environment.


### IP Whitelisting

All Bitnet environments use a whitelist of permitted IP addresses. Your IP must be added to this list before you can use the service. This should be handled at registration, but if you receive access denied messages this is a key detail to check with Bitnet.

## Getting Started

You can either take a look at the quick start example [project](https://github.com/bitnet/bitnet-java-libs/blob/master/README.md#quick-start) or you can [add](https://github.com/bitnet/bitnet-java-libs/blob/master/README.md#adding-dependencies) the dependencies to your own project and start working with the Bitnet object.

### Quick Start

There is an example project in [Java 7](https://github.com/bitnet/bitnet-java-libs/tree/master/quickstart-java7) or [Java 8](https://github.com/bitnet/bitnet-java-libs/tree/master/quickstart-Java8) which you can use to get up and running with the SDK immediately. This demonstrates how to set up the SDK and get started making calls to the Bitnet platform.

### Adding Dependencies

The Bitnet Java SDK is available from jCenter under the identifier [io.bitnet:bitnet-java-sdk](https://dl.bintray.com/bitnet/maven/io/bitnet/bitnet-java-sdk/).

#### Gradle

To add the Bitnet SDK to a Gradle project make sure jCenter is listed within the projects repositories.

```javascript
repositories {
    mavenCentral()
    maven {
        url "https://dl.bintray.com/bitnet/maven/"
    }
}
```

And add the bitnet-java-sdk with the latest version as follows.

```javascript
dependencies {
    compile 'io.bitnet:bitnet-java-sdk:<!--current-version-->'
}
```

where the current version can be found in [jCenter](https://dl.bintray.com/bitnet/maven/io/bitnet/bitnet-java-sdk/).

#### Maven

To add the Bitnet SDK to a Maven project make sure jCenter is listed within the projects repositories.

```xml
<repositories>
    <repository>
        <id>jCenter</id>
        <url>https://dl.bintray.com/bitnet/maven/</url>
    </repository>
</repositories>
```
And add the bitnet-java-sdk with the latest version as follows.

```xml
<dependencies>
    <dependency>
        <groupId>io.bitnet</groupId>
        <artifactId>bitnet-java-sdk</artifactId>
        <version><!--current-version--></version>
    </dependency>
</dependencies>
```

where the current version can be found in [jCenter](https://dl.bintray.com/bitnet/maven/io/bitnet/bitnet-java-sdk/).

#### The Bitnet Object

All Bitnet services can be accessed through the Bitnet object. You can obtain an instance of the object as follows:

```java
/*
 * Get an instance of the Bitnet object to provide access to Bitnet production services.
 * @param YOUR_BITNET_CLIENT_ID The client id given to you by Bitnet for the current environment.
 * @param YOUR_BITNET_SECRET The secret given to you by Bitnet for the current environment
 */
Bitnet bitnet = Bitnet.start(YOUR_BITNET_CLIENT_ID, YOUR_BITNET_SECRET);
```

or for the test environment:

```java
/*
 * Get an instance of the Bitnet object to provide access to Bitnet test services.
 * @param YOUR_BITNET_CLIENT_ID The client id given to you by Bitnet for the current environment.
 * @param YOUR_BITNET_SECRET The secret given to you by Bitnet for the current environment.
 */
Bitnet binet = Bitnet.startTest(YOUR_BITNET_CLIENT_ID, YOUR_BITNET_SECRET)
```

## Using Bitnet Services

There are a number of available services which correspond one-to-one with the Bitnet API:
* Payer
* Order
* Invoice
* Refund

## Authentication

The Bitnet API uses OAuth 2.0 for authentication. The SDK will manage authentication for you using the Client Id and Secret Bitnet provided.

## Payer

### Creating a Payer

Start by building a PayerCreate object with the Payer's details which can passed to the Bitnet Service.

For example:

```java
/*
 * Build a payer object with minimal required info.
 * @param YOUR_BITNET_ACCOUNT_ID The account id given to you by Bitnet for the current environment.
 * @param EMAIL The email address for the new payer.
 */
PayerCreate newPayer = new PayerCreate()
                .withAccountId(YOUR_BITNET_ACCOUNT_ID)
                .withEmail("thePayersEmailAddress@email.com")
```

or an example with address, reference and refund payment address:

```java
/*
 * Build a payer address.
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
 * @param REFUND_PAYMENT_ADDRESS This can be populated at time of creation, or updated at a later
 *        date. A refund payment address must be set in order to initiate a refund for a Payer.
 */
PayerCreate newPayer = new PayerCreate()
                .withAccountId(YOUR_BITNET_ACCOUNT_ID)
                .withEmail("thePayersEmailAddress@email.com")
                .withReference(YOUR_UNIQUE_REFERNCE)
                .withRefundPaymentAddress(REFUND_PAYMENT_ADDRESS)
                .withAddress(payerAddress);

```

Now call the Bitnet service to create the Payer:

```java

/*
 * Calling the BITNET create payer service with a payer object.
 */
Payer payer = bitnet.payerService().createPayer(newPayer);
```

The returned Payer object will contain an Id which you will need for all proceeding calls.

### Updating a Payer

Start by building a PayerUpdate object.

```java
/* Build a PayerUpdate object.
 * All payer information, apart from the Payer Id, can be updated.
 * @param YOUR_PAYER You should use a Payer you obtained from the Bitnet API
 *        create or get methods to ensure it is populated with all existing payer data.
 * IMPORTANT: All previous payer information must be supplied, otherwise this
 *            payer information will be overwritten.
 */
PayerUpdate payerToUpdate = new PayerUpdate(YOUR_PAYER)
                        .withEmail("updated@email.com");
```

Now call the Bitnet service.

```java
/*
 * Calling the BITNET update payer service.
 */
Payer updatedPayer = bitnet.payerService().updatePayer(payerToUpdate, PAYER_ID);
```

### Getting a Payer

```java
/*
 * Calling the BITNET service to get the payer.
 * @param PAYER_ID The id of the payer to be retrieved.
 */
Payer payer = bitnet.payerService().getPayer(PAYER_ID);
```

## Order

Once you have a Payer you can create an order.

```java
/*
 * Build an order object with minimal required info.
 * @param EXISTING_PAYER_ID The existing payer id which should be tied to this order.
 *        A payer may be linked to multiple orders.
 * @param THE_CURRENCY_SPECIFIED_IN_YOUR_ORDER This is the currency that has been used in
 *        the shopping basket, etc, for this order.
 * @param THE_PRICE_SPECIFIED_IN_YOUR_ORDER This is the price that has been used in the
 *        shopping basket, etc for this order.
 * Note: Digital currency is the responsibility of the Invoice, not the Order.
 */
OrderCreate newOrder = new OrderCreate()
                    .withAccountId(YOUR_BITNET_ACCOUNT_ID)
                    .withPayerId(EXISTING_PAYER_ID)
                    .withCurrency(THE_CURRENCY_SPECIFIED_IN_YOUR_ORDER)
                    .withTotalAmount(THE_PRICE_SPECIFIED_IN_YOUR_ORDER);
```

It is also possible to include Items along with the order.

```java

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
 * Build an order object with some items and a description.
 */
OrderCreate newOrder = new OrderCreate()
                    .withAccountId(YOUR_BITNET_ACCOUNT_ID)
                    .withPayerId(EXISTING_PAYER_ID)
                    .withCurrency(THE_CURRENCY_SPECIFIED_IN_YOUR_ORDER)
                    .withTotalAmount(THE_PRICE_SPECIFIED_IN_YOUR_ORDER)
                    .withDesc("A test order")
                    .withItems(items);
```

Call the Bitnet service to create the order

```java
/*
 * Calling the BITNET service to create the order.
 */  
Order order = bitnet.orderService().createOrder(newOrder);
```

The returned Order contains an Order Id which you will need for proceeding calls.

### Updating an Order

The state of an order can be updated to CANCELED if the order has not yet been invoiced.

```java
/*
 * Build an OrderUpdate with Canceled State.
 * Note: This is the only update to Orders allowed at present
 */
OrderUpdate orderToCancel = new OrderUpdate()
                        .withState(Order.State.CANCELED);
```

```java
/*
 * Call the BITNET service to cancel the order.
 * @param ORDER_ID The id of the order to be canceled.
 */
Order canceledOrder = bitnet.orderService().updateOrder(orderToCancel, ORDER_ID);
```

### Retrieving Orders

Orders can be retrieved individually:

```java
/*
 * Call the BITNET service to get an individual order
 * @param ORDER_ID The id of the order to fetch.
 */
Order order = bitnet.orderService().getOrder(ORDER_ID);
```
or as a list:

```java
/*
 * Start by building a list with the order states you are interested in.
 */
List<Order.State> states = new ArrayList<Order.State>();
states.add(Order.State.OPEN);

/*
 * Call the BITNET service to get a list of orders.
 * @param states The list of states you are interested in.
 * @param OFFSET_FROM_ZERO The list of orders starts with an index of 0.
 *        This number indicates where the list or subset of orders should start.
 * @param NUMBER_OF_ORDERS The number of orders to include in this list of orders.
 */
Orders orders = bitnet.orderService()
            .getOrders(YOUR_BITNET_ACCOUNT_ID, states, OFFSET_FROM_ZERO, NUMBER_OF_ORDERS);
```

## Invoices

### Creating an Invoice

```java
/*
 * Build an InvoiceCreate object.
 * @param EXISTING_ORDER_ID The id of an existing and open ORDER.
 * Note: An order can only be linked to one invoice.
 */
InvoiceCreate newInvoice = new InvoiceCreate()
                .withAccountId(YOUR_BITNET_ACCOUNT_ID)
                .withOrderId(EXISTING_ORDER_ID);
```

```java
/*
 * Call the BITNET service to create the invoice.
 */
Invoice invoice = bitnet.invoiceService().createInvoice(newInvoice);
```

On successful creation of an invoice, the linked order will be updated to a state of INVOICED. The created Invoice will contain the Bitcoin quote and payment address details.

### Updating an Invoice

```java
/*
 * Build an InvoiceUpdate object with Canceled state.
 * IMPORTANT: The state of an invoice can be updated to CANCELED if the invoice is
 *            OPEN and no payments have been detected.
 * Note: This is the only type of update that can currently be made to an invoice.
 */
InvoiceUpdate invoiceToUpdate = new InvoiceUpdate()
                            .withState(Invoice.State.CANCELED);
```

```java
/*
 * Call BITNET services to cancel the invoice.
 */
Invoice canceledInvoice = bitnet.invoiceService().updateInvoice(INVOICE_ID, invoiceToUpdate);
```

### Retrieving Invoices

Invoices can be retrieved individually:

```java
/*
 * Get Invoice by Id.
 */
Invoice invoice = bitnet.invoiceService().getInvoice(INVOICE_ID);
```

or as a list:

```java
/*
 * Start by building a list with the invoice states you are interested in.
 */
List<Invoice.State> states = new ArrayList<Invoice.State>();
states.add(Invoice.State.PAID);

/*
 * Call the BITNET service to get a list of invoices.
 * @param states The list of states you are interested in.
 * @param OFFSET_FROM_ZERO The list of invoices starts with an index of 0.
 *        This number indicates where the list or subset of invoices should start.
 * @param NUMBER_OF_INVOICES The number of invoices to include in this list.
 * NOTE: This method is overloaded. Choose the appropriate one for your needs.
 */
Invoices invoices = bitnet.invoiceService().getInvoices(
    YOUR_BITNET_ACCOUNT_ID,
    INVOICE_PAYMENT_ADDRESS,
    states,
    OFFSET_FROM_ZERO,
    NUMBER_OF_INVOICES)
```

## Refunds

There are three types of refund: Full, Partial and Mispayment Correction.

### Full refund

```java
/*
 * Build a RefundCreate object for the full refund.
 * @param Instruction To initiate a full refund you must set the Refund Instruction to FULL.
 * @param INVOICE_ID The invoice which should be refunded against.
 * IMPORTANT: The invoice must be in a state of PAID or OVERPAID and
 *            the payer associated with the order must have a refund payment address
 *            present before a refund can be created.
 */
RefundCreate newRefund = new RefundCreate()
                .withAccountId(YOUR_BITNET_ACCOUNT_ID)
                .withInstruction(Refund.Instruction.FULL)
                .withInvoiceId(INVOICE_ID);
```
```java
/*
 * Call the BITNET service to create the refund.
 */
Refund refund = bitnet.refundService().createRefund(newRefund);
```

### Partial Refund

```java
/*
 * Build a RefundCreate object for the partial correction refund.
 * @param Instruction To initiate a partial refund you must set the Refund
 *        Instruction to PARTIAL.
 * @param REFUND_AMOUNT The refund amount also must be less than the pricing amount
 *        of the order.
 * @param REFUND_CURRENCY The currency supplied in the refund request must match
 *        the order pricing currency.
 * @param INVOICE_ID The invoice which should be refunded against.
 * IMPORTANT: The invoice must be in a state of PAID or OVERPAID and the payer associated
 *            with the order must have a refund payment address present before a refund can be
 *            created. You must supply the refund amount in these cases.
 */
RefundCreate newRefund = new RefundCreate()
                .withAccountId(YOUR_BITNET_ACCOUNT_ID)
                .withAmount(REFUND_AMOUNT)
                .withCurrency(REFUND_CURRENCY)
                .withInstruction(Refund.Instruction.PARTIAL)
                .withInvoiceId(INVOICE_ID);
```
```java
/*
 * Now call the BITNET service to create the refund.
 */
Refund refund = bitnet.refundService().createRefund(newRefund);
```

### Mispayment Correction refund

For OVERPAID invoices a mispayment correction can be initiated to return the overpaid amount.

```java
/*
 * Build a RefundCreate object for the mispayment correction refund..
 * @param Instruction To initiate a mispayment correction refund you must set the
 *        Refund Instruction to MISPAYMENT_CORRECTION
 * @param INVOICE_ID The invoice which should be refunded against.
 * IMPORTANT: The invoice must be in a state of OVERPAID and the payer associated
 *            with the order must have a refund payment address
 *            present before a refund can be created.
 */
RefundCreate newRefund = new RefundCreate()
                .withAccountId(YOUR_BITNET_ACCOUNT_ID)
                .withInstruction(Refund.Instruction.MISPAYMENT_CORRECTION)
                .withInvoiceId(INVOICE_ID);
```
```java
/*
 * Now call the BITNET service to create the refund.
 */
Refund refund = bitnet.refundService().createRefund(newRefund);
```

### Retrieving Refunds

Refunds can be retrieved individually:

```java
/*
 * Get Refund by Id
 */
Refund refund = bitnet.refundService().getRefund(REFUND_ID);
```

or as a list:

```java
/*
 * Start by building a list with the refund states you are interested in.
 */
List<Refund.State> states = new ArrayList<Refund.State>();
states.add(Refund.State.OPEN);

/*
 * Call the BITNET service to get a list of refunds.
 * @param states The list of states you are interested in.
 * @param OFFSET_FROM_ZERO The list of refunds starts with an index of 0.
 *        This number indicates where the list or subset of refunds should start.
 * @param NUMBER_OF_REFUNDS The number of refunds to include in this list.
 */
Refunds refunds = bitnet.refundService().getRefunds(
    YOUR_BITNET_ACCOUNT_ID,
    states,
    OFFSET_FROM_ZERO,
    NUMBER_OF_REFUNDS)
```

## Errors

The SDK will raise exceptions for a wide range of errors. All exceptions raised derive from RuntimeException. We would encourage you to catch all the listed errors and handle them appropriately.

All errors except EncodeException will contain:
* CorrelationId: The Bitnet transaction identifier.
* Error Messages: A list of error messages describing the error.
* Response Body: The entire response body for reference.

```java
public void handleBitnetException(RuntimeException exception) {
    try {
        throw exception;
    } catch (BitnetAccessDeniedException e) {
        // Authentication with the Bitnet API failed
        // Check that your Client Id, Secret and Account Id are correct.
    } catch (BitnetInvalidRequestException e) {
        // The request was invalid
        // This can be due to invalid request formatting and so on.
    } catch (BitnetRequestCouldNotBeProcessedException e) {
        // The request could not be processed.
        // An example would be missing required fields.
    } catch (EncodeException e) {
        // These exceptions may occur as result of the passed object not obeying it's
        // constraints.
    } catch (BitnetConflictException e) {
        // An example of a conflict would be an attempt to create an entity which already
        // exists. Are you creating a duplicate payer, etc?
    } catch (BitnetRequestForbiddenException e) {
        // You do not have permission for the requested action.
        // Check that you are using the correct accountId and have the appropriate permissions.
    } catch (BitnetResourceNotFoundException e) {
        // Check that the resource you are actioning exists, e.g. is the Payer Id valid?
    } catch (BitnetException e) {
        // This will be thrown for errors when a more specific error cannot be identified.
    }
}
```

## Bitnet Notifications

Bitnet informs merchants of significant events, such as a payment received or the state change of an Invoice, by invoking a webhook. The full guide to Bitnet Notifications and webhooks can be found in the [Bitnet Developer Guide](https://developer.bitnet.io/api_guide#webhooks).

The SDK does not provide a mechanism for registered or receiving notifications, but it does give you some assistance in processing received notification.

### Setting up the SDK for Notification Processing

To be completed........

## Testing with a custom endpoint

If you are testing with a custom Bitnet endpoint you can get the Bitnet object as follows:

```java
/*
 * Get the bitnet object for a specified endpoint.
 * @param YOUR_BITNET_CLIENT_ID The Bitnet Client ID provided to you for the
 *        specified environment.
 * @param YOUR_BITNET_SECRET The Bitnet Secret provided to you for the
 *        specified environment.
 * @param ENVIRONMENT The custom endpoint.
 * @param BLOCKCHAIN The block chain used by the endpoint; most likely TESTNET.
 * @param LOGGING_LEVEL The desired logging level.
 * @param unsafeOkHttpClient This allows you to ignore unsafe SSL connections;
 *        useful in test.
 */
Bitnet bitnet = Bitnet.start(YOUR_BITNET_CLIENT_ID,
                             YOUR_BITNET_SECRET,
                             ENVIRONMENT,
                             BLOCKCHAIN,
                             LOGGING_LEVEL,
                             io.bitnet.feign.OkHttpClientProvider.unsafeOkHttpClient());

```

## Advanced Usage

We recommend using the SDK for integration but if you want to implement the integration yourself you can still take advantage of the model classes and service interfaces available within the Client jar.

In this case you should implement:
* OAuthService
* PayerService
* OrderService
* InvoiceService
* RefundService

## About this project

### Client overview

The client provides a java representation of the model and interfaces supported by the Bitnet API.

### SDK overview

The SDK provides a default implementation of the Bitnet API client using [Feign](https://github.com/Netflix/feign).

It is possible to provide an alternative implementation using the BitnetServiceProvider interface.

The FeignServiceProvider configures an appropriate Feign client and provides access to the Bitnet services.

```java
public Feign.Builder feignBuilder() {
  return Feign.builder()
        .client(client)
        .logger(new Slf4jLogger())
        .logLevel(loggingLevel)
        .encoder(new RequestLogger(new RequestValidator(new JacksonEncoder(), blockchain)))
        .decoder(new ResponseLogger(new JacksonDecoder()))
        .errorDecoder(new BitnetErrorDecoder(new BitnetErrorFactory(new BitnetErrorResponseInterrogator()), authenticationManager()))
        .retryer(new ConfigurableRetryer())
        .requestInterceptor(new RequestVersionInterceptor());
}
```

Using decorators for the Feign encoder and decoder it supports request/response logging, validation, retries and exception handling.

### Building from source

To build the client and SDK you will need:
* Java 7+ installed

The project is built using Gradle. You do not need Gradle installed as we take advantage of the [Gradle Wrapper](http://gradle.org/docs/current/userguide/gradle_wrapper.html).

Unless you have a valid client account for integration testing you should run the build using the following command:

```java
# On OSX or Linux
# Run the build without the integrationTest step.
./gradlew build -x integrationTest

# On Windows
# Run the build without the integrationTest step.
gradlew.bat build -x integrationTest

```

If you have a client account then update either sdk/integrationTest.properties or ~/.bitnet-java-libs/integrationTest.properties with your clientId, secret, environment, accountId, internalClientId and internalSecret and run the full build:

```java
# On OSX or Linux.
# Run the full build.
./gradlew build

# On Windows
# Run the full build.
gradlew.bat build

```
