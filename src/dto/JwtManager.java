// code pompé ici : https://developer.okta.com/blog/2018/10/31/jwts-with-java
// lui-même inspiré par : https://www.baeldung.com/java-json-web-tokens-jjwt
// et sinon la doc : https://github.com/jwtk/jjwt/blob/master/README.md
/**
 * JWTmanager permet de chiffrer et de déchiffrer un token JWT.
 * @author Pierre Goujet & Khatri Goujet
 * @source https://www.baeldung.com/java-json-web-tokens-jjwt
 * @source https://github.com/jwtk/jjwt/blob/master/README.md
 * @source  https://developer.okta.com/blog/2018/10/31/jwts-with-java
 * @since 2023-03-04
 */
package dto;

import com.google.common.hash.Hashing;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtManager {

    /**
     * permet de créer un token JWT.
      * @param username
     * @param password
     * @return token JWT
     */

    public static String createJWT(String username,String password) {
        // The JWT signature algorithm we will be using to sign the token
        password = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
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
                .signWith(signatureAlgorithm, signingKey);

        // if it has been specified, let's add the expiration
        long ttlMillis = 1000 * 60 * 15; // 15mn
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            token.setExpiration(exp); // 20mn par defaut
        }
            // Builds the JWT and serializes it to a compact, URL-safe string
            return token.compact();
        }

    /**
     *
     * @param jwt token JWT
     * @param password password pour déchiffrer le token JWT
     * @return Claims
     * @throws Exception erreur si le token n'est pas valide ou expiré
     */
    public static Claims decodeJWT(String jwt,String password) throws Exception {
        // This line will throw an exception if it is not a signed JWS (as expected)
        password=Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        return Jwts.parserBuilder()
                .setSigningKey(Base64.getDecoder().decode(password))
                .build()
                // verifie la signature et l'iat
                .parseClaimsJws(jwt).getBody();
    }

    /**
     * Permet de récupérer l'identifiant de l'utilisateur qui a créé le token JWT.'
     * @param tokenJWT
     * @return l'identifiant de l'utilisateur
     */
    public static String tokenUsername(String tokenJWT){
        String token[]=tokenJWT.split("\\.");
        String decode=new String(java.util.Base64.getDecoder().decode(token[1]));
        String payload[]=decode.split(",");
        String iss[]=payload[3].split(":");
        return iss[1].replace("\"","").replace("}","");
    }
}
