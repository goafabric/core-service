package org.goafabric.core.jwt;

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
        //var token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJfTDhoU3RhdWc0T3Y1dTVsMEo0NUNONEtEcnAySmZVb3N2MWhEUDZlbUYwIn0.eyJleHAiOjE2ODgzOTUyMTgsImlhdCI6MTY4ODM5NDkxOCwiYXV0aF90aW1lIjoxNjg4Mzk0OTE4LCJqdGkiOiI3YmU5YjJiMC1lODZlLTQ2ODgtYjI1My00OWFkNjUyODUzZjQiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvb2lkYy9yZWFsbXMvdGVuYW50LTAiLCJhdWQiOiJvYXV0aDItcHJveHkiLCJzdWIiOiI0NmZiZDhiMC1iOTliLTQwYTUtODA3ZS0xNGM2NjE5Zjk1ZDQiLCJ0eXAiOiJJRCIsImF6cCI6Im9hdXRoMi1wcm94eSIsIm5vbmNlIjoia0YwTm56ejNqUEFnRGlZaEFwTEVaQUtHMFpYYUQwWFV5ZlhtRGllRVVjRSIsInNlc3Npb25fc3RhdGUiOiJjNGY2MjEwMS0zMWE1LTQ3NTUtYWZiMS0yMTg5MTQwZWRlZDMiLCJhdF9oYXNoIjoiaE05Tk9iazdLQi1rYmdUdmtlZG54QSIsInNpZCI6ImM0ZjYyMTAxLTMxYTUtNDc1NS1hZmIxLTIxODkxNDBlZGVkMyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ1c2VyMSIsImdpdmVuX25hbWUiOiIiLCJmYW1pbHlfbmFtZSI6IiIsImVtYWlsIjoidXNlcjFAdXNlcjEuZGUifQ.UmK_LGFSdkoxRTRfiWy2E8Bt2J3d5DeolPycGCvhU3jenCTouWGpdZEzpPYUZbUCxsbEw_8C95_guQtK2TkmvuDV1Oio2Zy1AYWZCWvYaf0pKGF31EMMyv5gHh8EIZ-DIbnFP1RWI9OhVFxsnNnzDzvnxPOxsr63_rBDJsjPFk61f4Cj-UrySeLRmBnnLK2d0WXAudMMe7AOhS3gKraPZYgBhiFVvtVnMJ8h2vGZ3xvtVrWtHiIn2dwEIbv49lHEwJ-AhHFeRPy2TtzykKgFonljGImDkbJGMmHcoHDuQgrFk-plRMU3-djjG2_VD6LaoA3vzEhAiFyKRGfmWSf6ZQ";

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
        //tenant origin should be from url either dns or /param
    }

    private void logJwtFromSpring() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DefaultOidcUser) {
            log.info("JWT Token: " + ((OidcUser) authentication.getPrincipal()).getIdToken().getTokenValue());
        }
    }
}