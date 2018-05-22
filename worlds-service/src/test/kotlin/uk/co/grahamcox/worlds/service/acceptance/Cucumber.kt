package uk.co.grahamcox.worlds.service.acceptance

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

/**
 * Runner to run all of the acceptance tests
 */
@RunWith(Cucumber::class)
@CucumberOptions(tags = arrayOf("not @wip", "not @ignore", "not @manual"),
        plugin = arrayOf(
                "pretty",
                "html:target/site/cucumber/cucumber"
        ),
        strict = true)
class AllAcceptanceTest

/**
 * Runner to run all of the work-in-progress acceptance tests
 */
@RunWith(Cucumber::class)
@CucumberOptions(tags = arrayOf("@wip", "not @ignore", "not @manual"),
        plugin = arrayOf(
                "pretty",
                "html:target/site/cucumber/cucumber"
        ),
        strict = false)
class WIPAcceptanceTest
