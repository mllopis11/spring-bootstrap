package mike.bootstrap.springboot.application;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import mike.bootstrap.utilities.helpers.Dates;
import mike.bootstrap.utilities.helpers.Print;
import mike.bootstrap.utilities.helpers.Timer;
import mike.bootstrap.utilities.system.AppInfo;

/**
 * Interface to implement on your shutdown component to ensure that all
 * resources are safely closed before stopping the services and the
 * application.<br>
 * Overrides method {@link ContainerBootstrapShutdown#onContainerShutdown()} to
 * perform operations on shutdown
 * 
 * @author Mike (2021-02)
 *
 */
public interface ContainerBootstrapShutdown extends DisposableBean {

    default void onContainerShutdown() {
	// Add operations to perform on shutdown
    }

    @Override
    default void destroy() {

	final var log = LoggerFactory.getLogger(ContainerBootstrapShutdown.class);

	var tm = new Timer();

	Print.out("%s (node: %s) shutdown applicative components in progress at %s ...", AppInfo.module(),
		AppInfo.node(), Dates.zNow());
	log.info("{} (node: {}) shutdown applicative components in progress ...", AppInfo.module(), AppInfo.node());

	this.onContainerShutdown();

	String elapsed = tm.elapsToSeconds();
	log.info("{} (node: {}) shutdown applicative components completed in {}", AppInfo.module(), AppInfo.node(),
		elapsed);
    }
}
