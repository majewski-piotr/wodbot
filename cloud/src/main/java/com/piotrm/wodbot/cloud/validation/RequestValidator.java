package com.piotrm.wodbot.cloud.validation;

import software.pando.crypto.nacl.Crypto;
import java.security.PublicKey;
import java.util.Map;

public class RequestValidator {
    private final String publicKey;
    public RequestValidator(String publicKey){
        this.publicKey = publicKey;
    }

    private static final String X_SIGNATURE_ED25519 = "x-signature-ed25519";
    private static final String X_SIGNATURE_TIMESTAMP = "x-signature-timestamp";

    public boolean validateRequest(Map<String,String> headers, String body){
        PublicKey key = Crypto.signingPublicKey(fromHex(publicKey));
        String message = headers.get(X_SIGNATURE_TIMESTAMP) + body;

        boolean validated = Crypto.signVerify(key,message.getBytes(),fromHex(headers.get(X_SIGNATURE_ED25519)));
        return validated;
    }

    private byte[] fromHex(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
