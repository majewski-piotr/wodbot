package com.piotrm.wodbot.cloud;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.pando.crypto.nacl.Crypto;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class RequestValidator {
    private final String publicKey;
    public RequestValidator(String publicKey){
        this.publicKey = publicKey;
    }

    private static final Logger logger = LoggerFactory.getLogger(RequestValidator.class);

    private final StopWatch stopWatch = new StopWatch();


    public boolean validateRequest(HashMap event){
        logger.debug("Validating request");
        stopWatch.start();

        Map<String, String> requestHeaders = (Map<String, String>)event.get("headers");
        String x_signature_timestamp = requestHeaders.get("x-signature-timestamp");
        String x_signature_ed25519 =  requestHeaders.get("x-signature-ed25519");

        PublicKey key = Crypto.signingPublicKey(fromHex(publicKey));
        String message = x_signature_timestamp + event.get("body");

        boolean validated = Crypto.signVerify(key,message.getBytes(),fromHex(x_signature_ed25519));

        stopWatch.stop();
        logger.debug("Validation completed after "+stopWatch.getTime());
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
