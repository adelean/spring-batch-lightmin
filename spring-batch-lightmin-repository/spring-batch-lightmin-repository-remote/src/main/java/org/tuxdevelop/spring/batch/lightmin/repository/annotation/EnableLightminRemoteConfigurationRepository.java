package org.tuxdevelop.spring.batch.lightmin.repository.annotation;

import org.springframework.context.annotation.Import;
import org.tuxdevelop.spring.batch.lightmin.repository.configuration.RemoteJobConfigurationRepositoryConfiguration;

import java.lang.annotation.*;

/**
 * @author Marcel Becker
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {RemoteJobConfigurationRepositoryConfiguration.class})
public @interface EnableLightminRemoteConfigurationRepository {
}
