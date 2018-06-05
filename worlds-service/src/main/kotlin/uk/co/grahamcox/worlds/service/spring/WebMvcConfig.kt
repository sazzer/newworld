package uk.co.grahamcox.worlds.service.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import uk.co.grahamcox.worlds.service.openid.rest.AccessTokenInterceptor

/**
 * Web MVC Configuration for the system
 */
@Configuration
class WebMvcConfig : WebMvcConfigurer {
    /** Interceptor to add for handling Access Tokens */
    @Autowired
    private lateinit var accessTokenInterceptor: AccessTokenInterceptor

    /**
     * Add Spring MVC lifecycle interceptors for pre- and post-processing of
     * controller method invocations. Interceptors can be registered to apply
     * to all requests or be limited to a subset of URL patterns.
     *
     * **Note** that interceptors registered here only apply to
     * controllers and not to resource handler requests. To intercept requests for
     * static resources either declare a
     * [MappedInterceptor][org.springframework.web.servlet.handler.MappedInterceptor]
     * bean or switch to advanced configuration mode by extending
     * [ WebMvcConfigurationSupport][org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport] and then override `resourceHandlerMapping`.
     */
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(accessTokenInterceptor)
    }
}
