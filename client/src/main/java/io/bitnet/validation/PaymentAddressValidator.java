/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.validation;

import io.bitnet.Blockchain;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.NetworkParameters;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static io.bitnet.Blockchain.BITCOIN;

/**
 * Validates bitnet payment address for a bitnet environment using BitcoinJ.
 */
public class PaymentAddressValidator implements ConstraintValidator<CheckPaymentAddress, String> {
    private final NetworkParameters networkMode;
    private final Blockchain blockchain;

    public PaymentAddressValidator(Blockchain blockchain) {
        this(blockchain, blockchain == BITCOIN ? NetworkParameters.fromID(NetworkParameters.ID_MAINNET) : NetworkParameters.fromID(NetworkParameters.ID_TESTNET));
    }

    @Override
    public void initialize(CheckPaymentAddress constraintAnnotation) {

    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if (!validate(object)) {
            buildConstraintViolationOnContext(object, constraintContext);
            return false;
        }
        return true;
    }

    private PaymentAddressValidator(Blockchain blockchain, NetworkParameters networkMode) {
        this.networkMode = networkMode;
        this.blockchain = blockchain;
    }

    private void buildConstraintViolationOnContext(String object, ConstraintValidatorContext constraintContext) {
        constraintContext.disableDefaultConstraintViolation();
        constraintContext
                .buildConstraintViolationWithTemplate(object + " " + constraintContext.getDefaultConstraintMessageTemplate() + (blockchain == BITCOIN ? "(mainnet)" : "(testnet)"))
                .addConstraintViolation();
    }

    private Boolean validate(String address) {
        if (addressAbsent(address)) {
            return true;
        }

        try {
            return networkMode == Address.getParametersFromAddress(address);
        } catch (AddressFormatException e) {
            return false;
        }
    }

    private boolean addressAbsent(String address) {
        return address == null;
    }
}


