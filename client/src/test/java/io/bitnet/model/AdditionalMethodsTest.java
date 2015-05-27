/**
 * Copyright 2015 Bitnet.
 */
package io.bitnet.model;

import io.bitnet.model.payer.Payer;
import io.bitnet.model.payer.PayerUpdate;
import io.bitnet.model.payment.Invoice;
import io.bitnet.model.payment.Invoices;
import io.bitnet.model.payment.Order;
import io.bitnet.model.payment.Orders;
import io.bitnet.model.refund.Refund;
import io.bitnet.model.refund.Refunds;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Tests for generated additional methods.
 */
public class AdditionalMethodsTest {

    private Order[] orders = new Order[]{new Order().withAccountId("account1"), new Order().withAccountId("account2")};
    private Invoice[] invoices = new Invoice[]{new Invoice().withAccountId("account1"), new Invoice().withAccountId("account2")};
    private Refund[] refunds = new Refund[]{new Refund().withAccountId("account1"), new Refund().withAccountId("account2")};

    @Test
    public void shouldRetrieveOrders() {
        assertThat(requestOrders(orders).getOrders(), is(equalTo(asList(orders))));
    }

    @Test
    public void shouldRetrieveEmptyOrders() {
        assertThat(requestOrders().getOrders(), is(empty()));
        assertThat(requestOrdersWithSize(0, orders).getOrders(), is(empty()));
    }

    @Test
    public void shouldRetrieveInvoices() {
        assertThat(requestInvoices(invoices).getInvoices(), is(equalTo(asList(invoices))));
    }

    @Test
    public void shouldRetrieveEmptyInvoices() {
        assertThat(requestInvoices().getInvoices(), is(empty()));
        assertThat(requestInvoicesWithSize(0, invoices).getInvoices(), is(empty()));
    }

    @Test
    public void shouldRetrieveRefunds() {
        assertThat(requestRefunds(refunds).getRefunds(), is(equalTo(asList(refunds))));
    }

    @Test
    public void shouldRetrieveEmptyRefunds() {
        assertThat(requestRefunds().getRefunds(), is(empty()));
        assertThat(requestRefundsWithSize(0, refunds).getRefunds(), is(empty()));
    }

    @Test
    public void shouldCopyPayerPropertiesToPayerUpdateObject() {
        Payer payer = new Payer().withFirstName("Bob").withLastName("Jenkins").withEmail("bob@bill.com");
        PayerUpdate payerUpdate = new PayerUpdate(payer);

        assertThat(payerUpdate.getFirstName(), is(equalTo(payer.getFirstName())));
        assertThat(payerUpdate.getLastName(), is(equalTo(payer.getLastName())));
        assertThat(payerUpdate.getEmail(), is(equalTo(payer.getEmail())));
    }

    private Orders requestOrders(Order... orders) {
        return requestOrdersWithSize(orders.length, orders);
    }

    private Orders requestOrdersWithSize(int size, Order... orders) {
        return new Orders().withEmbedded(new io.bitnet.model.payment.Embedded().withOrders(asList(orders))).withSize(size);
    }

    private Invoices requestInvoices(Invoice... invoices) {
        return requestInvoicesWithSize(invoices.length, invoices);
    }

    private Invoices requestInvoicesWithSize(int size, Invoice... invoices) {
        return new Invoices().withEmbedded(new io.bitnet.model.payment.Embedded_().withInvoices(asList(invoices))).withSize(size);
    }

    private Refunds requestRefunds(Refund... refunds) {
        return requestRefundsWithSize(refunds.length, refunds);
    }

    private Refunds requestRefundsWithSize(int size, Refund... refunds) {
        return new Refunds().withEmbedded(new io.bitnet.model.refund.Embedded().withRefunds(asList(refunds))).withSize(size);
    }
}
