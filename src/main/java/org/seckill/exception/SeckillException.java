package org.seckill.exception;

/**
 * Created by liqiangpeng on 2017/4/9.
 */
public class SeckillException extends RuntimeException {
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
