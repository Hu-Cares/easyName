 
package ltd.newbee.mall.common;

public class ItemaException extends RuntimeException {

    public ItemaException() {
    }

    public ItemaException(String message) {
        super(message);
    }

    /**
     * 丢出一个异常
     *
     * @param message
     */
    public static void fail(String message) {
        throw new ItemaException(message);
    }

}
