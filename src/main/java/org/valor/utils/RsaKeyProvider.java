package org.valor.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

@Component
public class RsaKeyProvider {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public RsaKeyProvider(
            @Value("classpath:keys/private_key.pem") Resource privateKeyResource,
            @Value("classpath:keys/public_key.pem") Resource publicKeyResource) throws Exception {

        KeyFactory kf = KeyFactory.getInstance("RSA");

        // Загружаем приватный ключ
        byte[] privateBytes = readPemContent(privateKeyResource);
        PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(privateBytes);
        this.privateKey = kf.generatePrivate(privateSpec);

        // Загружаем публичный ключ
        byte[] publicBytes = readPemContent(publicKeyResource);
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(publicBytes);
        this.publicKey = kf.generatePublic(publicSpec);
    }

    private byte[] readPemContent(Resource resource) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String base64 = ((BufferedReader) reader).lines()
                    .filter(line -> !line.startsWith("-----"))
                    .collect(Collectors.joining());
            return Base64.getDecoder().decode(base64);
        }
    }

    public PrivateKey getPrivateKey() { return privateKey; }
    public PublicKey getPublicKey() { return publicKey; }
}