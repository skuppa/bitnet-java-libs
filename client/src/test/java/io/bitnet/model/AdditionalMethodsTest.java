/**
 * Copyright 2015 Bitnet.
 */
package io.bitnet.model;

import io.bitnet.model.payment.invoice.Invoice;
import io.bitnet.model.payment.invoice.Invoices;
import io.bitnet.model.payment.order.Order;
import io.bitnet.model.payment.order.Orders;
import io.bitnet.model.refund.refund.Refund;
import io.bitnet.model.refund.refund.Refunds;
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

    private Orders requestOrders(Order... orders) {
        return requestOrdersWithSize(orders.length, orders);
    }

    private Orders requestOrdersWithSize(int size, Order... orders) {
        return new Orders().with_embedded(new io.bitnet.model.payment.order._embedded().withOrders(asList(orders))).withSize(size);
    }

    private Invoices requestInvoices(Invoice... invoices) {
        return requestInvoicesWithSize(invoices.length, invoices);
    }

    private Invoices requestInvoicesWithSize(int size, Invoice... invoices) {
        return new Invoices().with_embedded(new io.bitnet.model.payment.invoice._embedded().withInvoices(asList(invoices))).withSize(size);
    }

    private Refunds requestRefunds(Refund... refunds) {
        return requestRefundsWithSize(refunds.length, refunds);
    }

    private Refunds requestRefundsWithSize(int size, Refund... refunds) {
        return new Refunds().with_embedded(new io.bitnet.model.refund.refund._embedded().withRefunds(asList(refunds))).withSize(size);
    }
}
