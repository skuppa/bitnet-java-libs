/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.validation;

import feign.codec.EncodeException;
import io.bitnet.Bitnet;
import io.bitnet.Blockchain;
import io.bitnet.TestUtilities;
import io.bitnet.core.exceptions.BitnetRequestCouldNotBeProcessedException;
import io.bitnet.model.payer.PayerCreate;
import org.junit.Test;

import static io.bitnet.TestCredentials.testAccountId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.fail;

/**
 * Tests for custom validation rules.
 */
public class CustomPaymentAddressValidationTest {

    @Test
    public void shouldThrowLocalExceptionForInvalidPaymentAddressForTestnet() {
        Bitnet bitnet = TestUtilities.newBitnetService(Blockchain.TESTNET);
        try {
            bitnet.payerService().createPayer(new PayerCreate().withAccountId(testAccountId()).withEmail("Bandit@instil.co").withRefundPaymentAddress("3EktnHQD7RiAE6uzMj2ZifT9YgRrkSgzQX"));
            fail("Should have thrown an encode exception");
        } catch (EncodeException ee) {
            assertThat(ee.getMessage(), containsString("refundPaymentAddress 3EktnHQD7RiAE6uzMj2ZifT9YgRrkSgzQX is not valid for configured bitcoin blockchain (testnet)"));
        }
    }

    @Test
    public void shouldThrowRemoteExceptionForInvalidPaymentAddressForEnvironment() {
        Bitnet bitnet = TestUtilities.newBitnetService(Blockchain.BITCOIN);
        try {
            bitnet.payerService().createPayer(new PayerCreate().withAccountId(testAccountId()).withEmail("Bandit@instil.co").withRefundPaymentAddress("3EktnHQD7RiAE6uzMj2ZifT9YgRrkSgzQX"));
            fail("Should have thrown an encode exception");
        } catch (BitnetRequestCouldNotBeProcessedException ee) {
            assertThat(ee.getMessage(), containsString("mainnet address '3EktnHQD7RiAE6uzMj2ZifT9YgRrkSgzQX' is not valid (testnet address required)"));
        }
    }

    @Test
    public void shouldNotThrowExceptionForValidPaymentAddressForEnvironment() {
        Bitnet bitnet = TestUtilities.newBitnetService(Blockchain.TESTNET);
        bitnet.payerService().createPayer(new PayerCreate().withAccountId(testAccountId()).withEmail("Bandit@instil.co").withRefundPaymentAddress("2MzQwSSnBHWHqSAqtTVQ6v47XtaisrJa1Vc"));
    }
}
