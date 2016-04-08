package com.soft2com.vjavalib.vutil;

public class VConcurrentModificationException extends RuntimeException
{
    /**
     * Constructs a ConcurrentModificationException with no
     * detail message.
     */
    public VConcurrentModificationException() {
    }

    /**
     * Constructs a <tt>ConcurrentModificationException</tt> with the
     * specified detail message.
     *
     * @param message the detail message pertaining to this exception.
     */
    public VConcurrentModificationException(String message) {
        super(message);
    }

}