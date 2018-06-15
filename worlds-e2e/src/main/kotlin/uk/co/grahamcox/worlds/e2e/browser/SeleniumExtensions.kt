package uk.co.grahamcox.worlds.e2e.browser

import org.openqa.selenium.*
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

/** Interval to wait between tries */
private const val DEFAULT_INTERVAL = 100L

/** Total number of times to try before giving up */
private const val DEFAULT_TRIES = 30

/** The logger to use */
private val LOG = LoggerFactory.getLogger(SearchContext::class.java)

/**
 * Execute an action, repeating it on an exception occurring
 * @param interval The interval to wait between repeats
 * @param tries The number of times to try
 * @param exceptions The set of exceptions to repeat on
 * @param action The action to perform
 */
fun <T> repeatOnFailure(interval: Long, tries: Int, exceptions: Set<Class<out Throwable>>, action: () -> T): T {
    var counter = 0

    while (true) {
        try {
            return action()
        } catch (e: Throwable) {
            if (counter < tries && exceptions.contains(e.javaClass)) {
                TimeUnit.MILLISECONDS.sleep(interval)
                ++counter
                LOG.debug("Failed attempt {}. Retrying.", counter)
            } else {
                LOG.info("Failed action", e)
                throw e
            }
        }
    }
}

/**
 * Wait until a given element exists
 */
fun SearchContext.waitUntilExists(by: By, interval: Long = DEFAULT_INTERVAL, tries: Int = DEFAULT_TRIES): WebElement {
    return repeatOnFailure(interval, tries, setOf(NoSuchElementException::class.java)) {
        LOG.debug("Finding element: {}", by)
        findElement(by)
    }
}

/**
 * Safe check for if an element is visible or not
 */
fun WebElement.safeIsVisible(): Boolean {
    return try {
        this.isDisplayed
    } catch (e: NoSuchElementException) {
        false
    }
}
