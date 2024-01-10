package remote.ultil;

public class Pass {
    public static String randomPassword(int length) {
        int randomInt = (int) (Math.random() * Math.pow(10, length));

        // random trúng số có dưới length chữ số thì random lại
        if (randomInt < 1000) return randomPassword(length);
        return Integer.toString(randomInt);
    }
}
