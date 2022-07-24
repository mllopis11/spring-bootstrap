package mike.bootstrap.springboot.application;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import mike.bootstrap.utilities.helpers.Dates;
import mike.bootstrap.utilities.system.AppInfo;

/**
 * Interface to implement when the spring context has been initialized to configure the application
 * resources.<br>
 * Overrides the method {@link ContainerBootstrapInitialized#onContainerInitialized()} to perform
 * additional configuration.
 * 
 * @author Mike (2021-02)
 *
 */
public interface ContainerBootstrapInitialized extends ApplicationListener<ContextRefreshedEvent> {

    default void onContainerInitialized() {
        // Additional configuration here
    }

    @Override
    default void onApplicationEvent(ContextRefreshedEvent event) {

        final var logger = LoggerFactory.getLogger(ContainerBootstrapInitialized.class);

        logger.info("{} (node: {}) context initialized and ready at {}", AppInfo.module(), AppInfo.node(),
                Dates.zNow());

        this.onContainerInitialized();
    }
}
