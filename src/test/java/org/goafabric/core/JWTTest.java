package org.goafabric.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.HashMap;

public class JWTTest {
    //https://www.baeldung.com/java-json-web-tokens-jjwt
    //https://www.baeldung.com/java-jwt-token-decode
    
    @Test
    public void jwt() throws JsonProcessingException {
        var token = "eyJraWQiOiI2N2RiMzVjMS02NTA2LTQ3OTctOTBjMy04ZDQ2NDA1YTI2ZDAiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImF1ZCI6Im9hdXRoMi1wcm94eSIsImF6cCI6Im9hdXRoMi1wcm94eSIsInJvbGVzIjpbInN0YW5kYXJkIl0sImlzcyI6Imh0dHA6Ly8xMjcuMC4wLjE6MzAyMDAiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ1c2VyMSIsImV4cCI6MTY4ODM5NzAxMCwiaWF0IjoxNjg4Mzk1MjEwLCJub25jZSI6InhjeFRITXNqR0pBeTJOcWVXeXZQTXd1UjZVOGc2ZF92cDFCUjc3WlJteVUiLCJlbWFpbCI6InVzZXIxQGV4YW1wbGUub3JnIn0.lBRSNwzxr9M9xCLTWU5IyJdqUGX-GqTkch9fn7rBCZC5xxSLaa_Zx_95X6fzMasr_jLrWnN18CB0U_jbejIIbkFmT2_eLF2TVTV3y3SMkde-69Lwefy4bNf2432pivqUsZ2ZMCBca38MZ2syC-vTvFLQfQAlxkozWlcHZQL-E49ZHTZjrVrOsm7XOwED4yAFNDgRRxmSEKyf5Xktg8mopLCmf5W7Jnoln6lT_qINkMUdu7UV8C36jU-nu7JruBfry7NYSZQdY05ToklPYHx-lTR6pLd6Le74uCXW01ntqMIZTcovV5W2YMwv0JJYhsYL58Bp-Hm_F6dc71yNnCJGLw";
        var chunks = token.split("\\.");
        var decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        System.out.println(header);
        System.out.println(payload);

        HashMap map = new ObjectMapper().readValue(payload, HashMap.class);
        System.out.println(map.get("preferred_username"));
        //System.out.println(map.get("sub"));
        System.out.println(map.get("iss"));

        //we need tenantId + user, role could be managed by core itself in the first step
    }
}
