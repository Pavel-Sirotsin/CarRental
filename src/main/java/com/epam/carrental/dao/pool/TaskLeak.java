package com.epam.carrental.dao.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The TaskLeak class extends {@link TimerTask} and was implemented to
 * follow the events in the connection pool.
 * */
final class TaskLeak extends TimerTask {
    private static final Logger logger = LogManager.getFormatterLogger(TaskLeak.class);
    /**
     * A {@code String} constant holding the maximum value of active connections.
     */
    private static final String MAX_ACTIVE = PropertiesUtil.CONFIG.getProperty("maxActive");
    /**
     * A {@code ReentrantLock} field holding the locker.
     */
    private final ReentrantLock locker = new ReentrantLock();
    /**
     * The method runs a new {@link Thread} with task.
     * <p>Gets the {@code FREE_CONNECTIONS} size and compares it with
     * {@code USED_CONNECTIONS} size.</p>
     */
    @Override
    public void run() {
        logger.traceEntry("- try to check connection pool on leaks.");
        logger.debug("line(%d), start checking: %s", 19, new Date(this.scheduledExecutionTime()));
        try {
            locker.lock();
            int freeSize = ConnectionPoolImpl.INSTANCE.getFreeSize();
            int inUseSize = ConnectionPoolImpl.INSTANCE.getInUseSize();

            logger.debug("line(%d), freeSize: %d", 29, freeSize);
            logger.debug("line(%d), inUseSize: %d", 30, inUseSize);

            int result = inUseSize + freeSize;
            logger.debug("line(%d), control sum: %d", 34, result);
            int capacity = Integer.parseInt(MAX_ACTIVE);

            if (result != capacity) {
                int leaks = capacity - result;
                logger.traceExit("there are leaks! : %d", leaks);
            } else {
                logger.traceExit("no leaks.");
            }
        } finally {
            locker.unlock();
        }
    }
}
