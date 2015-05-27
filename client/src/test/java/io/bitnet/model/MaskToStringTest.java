/**
 * Copyright 2015 Bitnet.
 */
package io.bitnet.model;

import io.bitnet.model.payer.Address;
import io.bitnet.model.payer.ContactNumber;
import io.bitnet.model.payer.Payer;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Test to string masking works
 */
public class MaskToStringTest {

    @Test
    public void shouldMaskAddressToString() {
        String addressToString = getDefaultAddress().toString();
        assertAddressMasked(addressToString);
    }

    @Test
    public void shouldOnlyMaskAddressFields() {
        Payer payer = new Payer();
        payer.setId("id");
        payer.setAccountId("accountId");
        payer.setReference("reference");
        payer.setFirstName("firstName");
        payer.setLastName("lastName");
        payer.setAddress(getDefaultAddress());
        payer.setEmail("email");
        payer.setContactNumbers(Collections.singleton(new ContactNumber().withNumber("contactNumber")));
        payer.setRefundPaymentAddress("refundPaymentAddress");
        payer.setCreatedAt("createdAt");
        payer.setModifiedAt("modifiedAt");

        String payerToString = payer.toString();

        assertThat(payerToString, containsString("id=id"));
        assertThat(payerToString, containsString("accountId=accountId"));
        assertThat(payerToString, containsString("reference=reference"));
        assertThat(payerToString, containsString("firstName=firstName"));
        assertThat(payerToString, containsString("lastName=lastName"));
        assertAddressMasked(payerToString);
        assertThat(payerToString, containsString("email=email"));
        assertThat(payerToString, containsString("contactNumbers=[io.bitnet.model.payer.ContactNumber"));
        assertThat(payerToString, containsString("[label=HOME,number=contactNumber]"));
        assertThat(payerToString, containsString("refundPaymentAddress=refundPaymentAddress"));
        assertThat(payerToString, containsString("createdAt=createdAt"));
        assertThat(payerToString, containsString("modifiedAt=modifiedAt"));
    }

    private void assertAddressMasked(String toString) {
        assertThat(toString, containsString("addressLine1=XX MASKED XX"));
        assertThat(toString, containsString("addressLine2=XX MASKED XX"));
        assertThat(toString, containsString("city=XX MASKED XX"));
        assertThat(toString, containsString("postalCode=XX MASKED XX"));
        assertThat(toString, containsString("country=XX MASKED XX"));
        assertThat(toString, containsString("region=XX MASKED XX"));
    }


    private Address getDefaultAddress() {
        Address address = new Address();
        address.setAddressLine1("addressLine1");
        address.setAddressLine2("addressLine2");
        address.setCity("city");
        address.setPostalCode("postalCode");
        address.setCountry(Address.Country.AD);
        address.setRegion("region");
        return address;
    }

}
