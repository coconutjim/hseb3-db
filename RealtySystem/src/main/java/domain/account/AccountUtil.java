package domain.account;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/***
 * Represents static methods connected to account
 */
public class AccountUtil {

    /***
     * Does not allow object creation
     */
    private AccountUtil() {

    }

    /***
     * Calculates hash (SHA-256) for corresponding password and salt
     * @param password password
     * @param salt salt
     * @return hash
     */
    public static String getSHA256(String password, String salt) {
        String base = password;
        base += salt;
        byte[] buffer = base.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(buffer);
            byte[] bytes = md.digest();
            String hex = "";
            for (byte digest : bytes) {
                int b = digest & 0xff;
                if (Integer.toHexString(b).length() == 1) hex = hex + "0";
                hex = hex + Integer.toHexString(b);
            }
            return hex;
        } catch (NoSuchAlgorithmException e) {
            // no chances
        }
        return null;
    }

    /***
     * Generates a salt of 7 random characters (of English lower case letters)
     * @return salt
     */
    public static String generateSalt() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            char c = chars[random.nextInt(chars.length)];
            builder.append(c);
        }
        return builder.toString();
    }
}
