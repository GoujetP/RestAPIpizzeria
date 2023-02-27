// code pompé ici : https://developer.okta.com/blog/2018/10/31/jwts-with-java
// lui-même inspiré par : https://www.baeldung.com/java-json-web-tokens-jjwt
// et sinon la doc : https://github.com/jwtk/jjwt/blob/master/README.md

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;

public class JwtManager {
    // pour SHA256 : 256 bits mini
    

    public static String createJWT(String username,String password) {
        // The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(password);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // Let's set the JWT Claims
        JwtBuilder token = Jwts.builder()
                .setId(UUID.randomUUID().toString().replace("-", ""))
                .setIssuedAt(now)
                .setSubject("PizzaJWTToken")
                .setIssuer(username)
                .signWith(signingKey, signatureAlgorithm);

        // if it has been specified, let's add the expiration
        long ttlMillis = 1000 * 60 * 4; // 4mn
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            token.setExpiration(exp); // 20mn par defaut
        }
        // Builds the JWT and serializes it to a compact, URL-safe string
        return token.compact();
    }

    public static Claims decodeJWT(String jwt,String password) throws Exception {
        // This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Base64.getDecoder().decode(password))
                .build()
                // verifie la signature et l'iat
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
}
