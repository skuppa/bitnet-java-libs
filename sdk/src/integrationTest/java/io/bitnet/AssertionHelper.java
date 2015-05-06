/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet;

import io.bitnet.model.payer.payer.ContactNumber;
import io.bitnet.model.payment.order.Item;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.fail;

/**
 * This provides some common test methods.
 */
public class AssertionHelper {
    private static final String BASIC_UUID_REGEX = "^[A-Fa-f0-9]{8}[-]([A-Fa-f0-9]{4}[-]){3}[A-Fa-f0-9]{12}$";

    public static void assertIsUUID(String valueToCheck) {
        if (valueToCheck != null && valueToCheck.matches(BASIC_UUID_REGEX)) {
            return;
        }
        fail(String.format("The value%s is not a correlation id", valueToCheck));
    }

    public static void assertThatContactNumbersMatch(Set<ContactNumber> expectedContactNumbers, Set<ContactNumber> actualContactNumbers) {
        if (expectedContactNumbers.size() != actualContactNumbers.size()) {
            fail(String.format("The contact numbers expected %s do not match the actual contact numbers %s", expectedContactNumbers, actualContactNumbers));
        }

        for (ContactNumber contactNumber : actualContactNumbers) {
            if (!expectedContactNumbers.contains(contactNumber)) {
                fail(String.format("The contact numbers expected %s do not match the actual contact numbers %s", expectedContactNumbers, actualContactNumbers));
            }
        }
    }

    public static void assertThatShippingContactNumbersMatch(
            Set<io.bitnet.model.payment.order.ContactNumber> expectedContactNumbers,
            Set<io.bitnet.model.payment.order.ContactNumber> actualContactNumbers) {
        if (expectedContactNumbers.size() != actualContactNumbers.size()) {
            fail(String.format("The contact numbers expected %s do not match the actual contact numbers %s", expectedContactNumbers, actualContactNumbers));
        }

        for (io.bitnet.model.payment.order.ContactNumber contactNumber : actualContactNumbers) {
            if (!expectedContactNumbers.contains(contactNumber)) {
                fail(String.format("The contact numbers expected %s do not match the actual contact numbers %s", expectedContactNumbers, actualContactNumbers));
            }
        }
    }

    public static void assertThatOrderItemsMatch(List<Item> expectedItems, List<Item> actualItems) {
        if (expectedItems.size() != actualItems.size()) {
            fail(String.format("The order items expected %s do not match the actual order items %s", expectedItems, actualItems));
        }

        for (Item item : actualItems) {
            if (!expectedItems.contains(item)) {
                fail(String.format("The order items expected %s do not match the actual order items %s", expectedItems, actualItems));
            }
        }
    }
}
