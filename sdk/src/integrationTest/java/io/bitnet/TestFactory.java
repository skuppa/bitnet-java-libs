/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet;

import io.bitnet.model.payer.Address;
import io.bitnet.model.payer.ContactNumber;
import io.bitnet.model.payer.PayerCreate;
import io.bitnet.model.payment.Item;
import io.bitnet.model.payment.Shipping;

import java.util.*;

import static io.bitnet.TestCredentials.testAccountId;

/**
 * This will provide ways to get some objects common across tests.
 */
public class TestFactory {

    public static final String REFUND_PAYMENT_ADDRESS = "mm4FdvNDm173WiQosCvhqpQdThsE7vvSCp";

    public static PayerCreate getPayerCreateWithAllInformation(String email) {
        return new PayerCreate()
                .withAccountId(testAccountId())
                .withEmail(email)
                .withFirstName("robot")
                .withLastName("monkey")
                .withAddress(getAddress())
                .withReference(getRandomId())
                .withRefundPaymentAddress(REFUND_PAYMENT_ADDRESS)
                .withContactNumbers(getContactNumbers());
    }

    private static Set<ContactNumber> getContactNumbers() {
        return getContactNumbers("08003847373", "02892600300", "07411853737");
    }

    public static Set<ContactNumber> getContactNumbers(String homeNumber, String workNumber, String mobileNumber) {
        HashSet<ContactNumber> contactNumbers = new HashSet<>();
        contactNumbers.add(getContactNumber(homeNumber, ContactNumber.Label.HOME));
        contactNumbers.add(getContactNumber(workNumber, ContactNumber.Label.WORK));
        contactNumbers.add(getContactNumber(mobileNumber, ContactNumber.Label.MOBILE));
        return contactNumbers;
    }

    private static ContactNumber getContactNumber(String number, ContactNumber.Label label) {
        return new ContactNumber().withNumber(number).withLabel(label);
    }

    public static Set<io.bitnet.model.payment.ContactNumber> getOrderContactNumbers(String homeNumber, String workNumber, String mobileNumber) {
        HashSet<io.bitnet.model.payment.ContactNumber> contactNumbers = new HashSet<>();
        contactNumbers.add(getContactNumber(homeNumber, io.bitnet.model.payment.ContactNumber.Label.HOME));
        contactNumbers.add(getContactNumber(workNumber, io.bitnet.model.payment.ContactNumber.Label.WORK));
        contactNumbers.add(getContactNumber(mobileNumber, io.bitnet.model.payment.ContactNumber.Label.MOBILE));
        return contactNumbers;
    }

    private static io.bitnet.model.payment.ContactNumber getContactNumber(String number, io.bitnet.model.payment.ContactNumber.Label label) {
        return new io.bitnet.model.payment.ContactNumber().withNumber(number).withLabel(label);
    }


    public static String getRandomId() {
        return UUID.randomUUID().toString();
    }

    public static Shipping getShipping() {
        return new Shipping()
                .withFirstName("robot")
                .withLastName("monkey")
                .withAddress(getShippingAddress())
                .withContactNumbers(getOrderContactNumbers("02892878787", "028903467777", "07411832323"))
                .withMethod("courier");
    }

    public static List<Item> getOrderItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item().withDesc("item 1").withName("item 1 name").withPrice("2.99").withQuantity(1).withSku("sku 1"));
        items.add(new Item().withDesc("item 2").withName("item 2 name").withPrice("4.99").withQuantity(4).withSku("sku 2"));
        items.add(new Item().withDesc("item 3").withName("item 3 name").withPrice("8.99").withQuantity(2).withSku("sku 3"));
        return items;
    }

    private static io.bitnet.model.payment.Address getShippingAddress() {
        return new io.bitnet.model.payment.Address()
                .withAddressLine1("9 test street")
                .withAddressLine2("test avenue")
                .withCity("testville")
                .withRegion("somewhere")
                .withPostalCode("testcode")
                .withCountry(io.bitnet.model.payment.Address.Country.US);
    }

    private static Address getAddress() {
        return new Address()
                .withAddressLine1("9 test street")
                .withAddressLine2("test avenue")
                .withCity("testville")
                .withRegion("somewhere")
                .withPostalCode("testcode")
                .withCountry(Address.Country.US);
    }

}
