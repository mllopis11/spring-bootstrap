package mike.bootstrap.springboot.application;

import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import mike.bootstrap.utilities.helpers.Dates;
import mike.bootstrap.utilities.system.AppInfo;

/**
 * Interface to implement when the application is ready to ensure that the
 * application resources are configured before starting your business services.<br>
 * Overrides the method {@link ContainerBootstrapReady#onContainerReady()} to perform your operations
 * 
 * @author Mike (2021-02)
 *
 */
public interface ContainerBootstrapReady extends ApplicationListener<ApplicationReadyEvent> {

	default void onContainerReady() {
		// TODO operations to perform
	}
	
	@Override
	default void onApplicationEvent(ApplicationReadyEvent event) {
		final var log = LoggerFactory.getLogger(ContainerBootstrapReady.class);

		log.info("{} (node: {}) application up and ready at {}", AppInfo.module(), AppInfo.node(), Dates.zNow());

        this.onContainerReady();
	}
}
