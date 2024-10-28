package com.javafullstackguru.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomIdGenerator implements IdentifierGenerator {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PREFIX = "klit";
    private static final AtomicInteger counter = new AtomicInteger(1000); // Start from 1000

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        return PREFIX + counter.incrementAndGet(); // Generate a unique identifier
    }
    
    public static void resetCounter() {
        counter.set(1000); // Reset the counter if needed
    }
}
