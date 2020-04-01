package org.tuxdevelop.spring.batch.lightmin.server.scheduler.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.tuxdevelop.spring.batch.lightmin.server.repository.LightminApplicationRepository;
import org.tuxdevelop.spring.batch.lightmin.server.scheduler.repository.SchedulerConfigurationRepository;
import org.tuxdevelop.spring.batch.lightmin.server.scheduler.repository.SchedulerExecutionRepository;
import org.tuxdevelop.spring.batch.lightmin.server.scheduler.repository.annotation.EnableServerSchedulerMapRepository;
import org.tuxdevelop.spring.batch.lightmin.server.scheduler.service.*;
import org.tuxdevelop.spring.batch.lightmin.server.service.JobServerService;

@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "spring.batch.lightmin.server.scheduler", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableServerSchedulerMapRepository
@EnableConfigurationProperties(value = {ServerSchedulerCoreConfigurationProperties.class})
public class ServerSchedulerCoreConfiguration {

    @Bean
    public ExecutionRunnerService executionRunnerService(final SchedulerConfigurationService schedulerConfigurationService,
                                                         final SchedulerExecutionService schedulerExecutionService,
                                                         final JobServerService jobServerService,
                                                         final LightminApplicationRepository lightminApplicationRepository) {
        return new ExecutionRunnerService(
                schedulerConfigurationService,
                schedulerExecutionService,
                jobServerService,
                lightminApplicationRepository);
    }

    @Bean
    public SchedulerConfigurationService schedulerConfigurationService(
            final SchedulerConfigurationRepository schedulerConfigurationRepository) {
        return new SchedulerConfigurationService(schedulerConfigurationRepository);
    }

    @Bean
    public SchedulerExecutionService schedulerExecutionService(
            final SchedulerExecutionRepository schedulerExecutionRepository) {
        return new SchedulerExecutionService(schedulerExecutionRepository);
    }

    @Bean
    public ServerPollerService serverPollerService(final ExecutionPollerService executionPollerService) {
        return new ServerPollerService(executionPollerService);
    }

    @Bean
    @ConditionalOnMissingBean(ExecutionPollerService.class)
    public ExecutionPollerService executionPollerService(final ExecutionRunnerService executionRunnerService,
                                                         final SchedulerExecutionService schedulerExecutionService,
                                                         final ServerSchedulerCoreConfigurationProperties properties) {
        return new StandaloneExecutionPollerService(executionRunnerService, schedulerExecutionService, properties);
    }
}