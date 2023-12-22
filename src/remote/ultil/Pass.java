package remote.ultil;

import java.util.Random;

public class Pass {
    public static String RandomPassword(int length) {
        StringBuilder kq = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            char c = (char) (r.nextInt(26) + 'a');
            kq.append(c);
        }
        return kq.toString();
    }
}
