package dhu.rs.common;

public class RSException extends RuntimeException {

    public RSException() {
    }

    public RSException(String message) {
        super(message);
    }

    /**
     * 丢出一个异常
     *
     * @param message
     */
    public static void fail(String message) {
        throw new RSException(message);
    }

}
