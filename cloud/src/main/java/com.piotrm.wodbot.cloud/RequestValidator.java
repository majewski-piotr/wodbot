package com.piotrm.wodbot.cloud;

import software.pando.crypto.nacl.Crypto;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class RequestValidator {
    private final String publicKey;
    public RequestValidator(String publicKey){
        this.publicKey = publicKey;
    }

    public boolean validateRequest(HashMap event){
        Map<String, String> requestHeaders = (Map<String, String>)event.get("headers");
        String x_signature_timestamp = requestHeaders.get("x-signature-timestamp");
        String x_signature_ed25519 =  requestHeaders.get("x-signature-ed25519");

        PublicKey key = Crypto.signingPublicKey(fromHex(publicKey));
        String message = x_signature_timestamp + event.get("body");
        return Crypto.signVerify(key,message.getBytes(),fromHex(x_signature_ed25519));
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
