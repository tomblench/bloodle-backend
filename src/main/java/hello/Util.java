package hello;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Util {

    public static String randomBase64(int nBytes) {
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            byte b[] = new byte[nBytes];
            random.nextBytes(b);
            return new String(Base64.getUrlEncoder().encode(b));
        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException(nsae);
        }
    }

}
