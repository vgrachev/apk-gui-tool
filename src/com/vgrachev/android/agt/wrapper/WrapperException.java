package com.vgrachev.android.agt.wrapper;

/**
 * Created by vgrachev on 08/06/14.
 */
public class WrapperException extends Exception {

    /**
     * Constructs an <code>WrapperException</code> with no detail  message.
     */
    public WrapperException() {
        super();
    }

    /**
     * Constructs an <code>WrapperException</code> with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public WrapperException(String s) {
        super(s);
    }
}
