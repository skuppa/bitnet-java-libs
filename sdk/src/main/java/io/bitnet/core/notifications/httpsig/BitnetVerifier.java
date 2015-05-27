package io.bitnet.core.notifications.httpsig;

/*
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <http://unlicense.org/>
 */

import net.adamcin.httpsig.api.*;

import java.util.*;

/**
 * This is a copy of https://github.com/adamcin/httpsig-java/blob/eb328ec93c9cb26e2bfead5b00568cb31d20ad3d/api/src/main/java/net/adamcin/httpsig/api/DefaultVerifier.java
 * The httpsig library currently has an issue dealing with parsing date in non english locales.
 * A patch will be submitted to the httpsig project but for now this class has been copied, the changes to the copy are:
 * Removed Verifier interface
 * Updated Classname to BitnetVerifier
 * Updated RequestContent class reference to BitnetRequestContent
 *
 * TODO(DMCF): If patch to httpsig is accepted remove this class.
 *
 * The Server-Side component of the protocol which verifies {@link Authorization} headers using SSH Public Keys
 */
public final class BitnetVerifier  {
    public static final long DEFAULT_SKEW = 300000L;

    private final Keychain keychain;
    private final KeyId keyId;
    private final long skew;

    public BitnetVerifier(Keychain keychain) {
        this(keychain, null, DEFAULT_SKEW);
    }

    public BitnetVerifier(Keychain keychain, KeyId keyId) {
        this(keychain, keyId, DEFAULT_SKEW);
    }

    public BitnetVerifier(Keychain keychain, KeyId keyId, long skew) {
        this.keychain = keychain != null ? new KeychainGuard(keychain) : new KeychainGuard(new DefaultKeychain());
        this.keyId = new CanVerifyId(keyId != null ? keyId : Constants.DEFAULT_KEY_IDENTIFIER);
        this.skew = skew;
    }

    public Keychain getKeychain() {
        return keychain;
    }

    /**
     * {@inheritDoc}
     */
    public long getSkew() {
        return skew;
    }

    /**
     * @param skew new server skew in milliseconds
     * @deprecated will remove to make the class immutable. use constructor overload instead.
     */
    public void setSkew(long skew) {
        // this is now a no-op.
    }

    /**
     * {@inheritDoc}
     */
    public Key selectKey(Authorization authorization) {
        return keychain.toMap(this.keyId).get(authorization.getKeyId());
    }

    /**
     * {@inheritDoc}
     */
    public boolean verify(Challenge challenge, BitnetRequestContent requestContent, Authorization authorization) {
        return verifyWithResult(challenge, requestContent, authorization) == VerifyResult.SUCCESS;
    }

    /**
     * {@inheritDoc}
     */
    public VerifyResult verifyWithResult(Challenge challenge, BitnetRequestContent requestContent, Authorization authorization) {
        if (challenge == null) {
            throw new IllegalArgumentException("challenge cannot be null");
        }

        if (requestContent == null) {
            throw new IllegalArgumentException("requestContent cannot be null");
        }

        if (authorization == null) {
            throw new IllegalArgumentException("authorization cannot be null");
        }

        // verify that all headers required by the challenge are declared by the authorization
        for (String header : challenge.getHeaders()) {
            if (!header.startsWith(":") && !authorization.getHeaders().contains(header)) {
                return VerifyResult.CHALLENGE_NOT_SATISFIED;
            }
        }

        // verify that all headers declared by the authorization are present in the request
        for (String header : authorization.getHeaders()) {
            if (requestContent.getHeaderValues(header).isEmpty()) {
                return VerifyResult.INCOMPLETE_REQUEST;
            }
        }

        // if date is declared by the authorization, verify that its value is within $skew of the current time
        if (authorization.getHeaders().contains(Constants.HEADER_DATE) && skew >= 0) {
            Date requestTime = requestContent.getDateGMT();
            Date currentTime = new GregorianCalendar(TimeZone.getTimeZone("UTC")).getTime();
            Date past = new Date(currentTime.getTime() - skew);
            Date future = new Date(currentTime.getTime() + skew);
            if (requestTime.before(past) || requestTime.after(future)) {
                return VerifyResult.EXPIRED_DATE_HEADER;
            }
        }

        Key key = selectKey(authorization);
        if (key == null) {
            return VerifyResult.KEY_NOT_FOUND;
        }

        if (key.verify(authorization.getAlgorithm(),
                requestContent.getContent(authorization.getHeaders(), Constants.CHARSET),
                authorization.getSignatureBytes())) {
            return VerifyResult.SUCCESS;
        } else {
            return VerifyResult.FAILED_KEY_VERIFY;
        }
    }

    private static class CanVerifyId implements KeyId {
        private KeyId delegatee;

        private CanVerifyId(KeyId delegatee) {
            this.delegatee = delegatee;
        }

        public String getId(Key key) {
            if (key != null && key.canVerify()) {
                return delegatee.getId(key);
            }
            return null;
        }
    }

    /**
     * Guards a Keychain from modification via reflection (not enough for JAAS?)
     */
    private static class KeychainGuard implements Keychain {
        private final Keychain keychain;

        private KeychainGuard(Keychain keychain) {
            this.keychain = keychain;
        }

        public Set<Algorithm> getAlgorithms() {
            return keychain.getAlgorithms();
        }

        public Keychain filterAlgorithms(Collection<Algorithm> algorithms) {
            return new KeychainGuard(keychain.filterAlgorithms(algorithms));
        }

        public Keychain discard() {
            return new KeychainGuard(keychain.discard());
        }

        public Key currentKey() {
            return keychain.currentKey();
        }

        public Map<String, Key> toMap(KeyId keyId) {
            return keychain.toMap(keyId);
        }

        public boolean isEmpty() {
            return keychain.isEmpty();
        }

        public Iterator<Key> iterator() {
            return keychain.iterator();
        }
    }
}
