package mike.bootstrap.test.utilities.system;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import mike.bootstrap.utilities.exceptions.ApplicationErrorException;
import mike.bootstrap.utilities.helpers.Utils;
import mike.bootstrap.utilities.system.SysInfo;

@DisplayName("Helpers::SysInfo")
@TestMethodOrder(OrderAnnotation.class)
class SysInfoTest {

	private static final String hostname = "127.0.0.1";
    private static final String port = "9191";
    
    @Test
    @Order(1)
    void should_return_main_class_informations() {
        
    	assertThat(SysInfo.mainClass()).isEmpty();
    	assertThat(SysInfo.mainClassName()).isEmpty();
    	assertThat(SysInfo.mainClassPackage()).isEmpty();
		
		assertThatExceptionOfType(ClassCastException.class).isThrownBy(() -> SysInfo.setMainClass(SysInfo.class));
		
		assertThatNoException().isThrownBy(() -> SysInfo.setMainClass(FooAppTest.class));
		assertThat(FooAppTest.class.getCanonicalName()).isEqualTo(SysInfo.mainClass());
		assertThat(FooAppTest.class.getSimpleName()).isEqualTo(SysInfo.mainClassName());
		assertThat(FooAppTest.class.getPackage().getName()).isEqualTo(SysInfo.mainClassPackage());
    }
    
    @Test
    void should_return_system_informations() {
    	assertThat(SysInfo.platform()).isNotEmpty();
    	assertThat(SysInfo.isUnix() || SysInfo.isWindows() ).isTrue();
    	assertThat(SysInfo.cores()).isPositive();
    	assertThat(SysInfo.hostIP()).isNotEmpty();
    	assertThat(SysInfo.hostname()).isNotEmpty();
    	assertThat(SysInfo.pid()).isNotEmpty();
    	assertThat(SysInfo.localhost()).isNotEmpty();
    	assertThat(SysInfo.machine()).isNotEmpty();
    }
    
    @Test
    void should_throw_application_error_exception_when_host_address_invalid_args() {
    	
    	assertThatExceptionOfType(ApplicationErrorException.class)
    		.isThrownBy( () -> SysInfo.hostAddressAlreadyBound(" ", null) );
    	
    	assertThatExceptionOfType(ApplicationErrorException.class)
			.isThrownBy( () -> SysInfo.hostAddressAlreadyBound(null, port) );
    	
    	assertThatExceptionOfType(ApplicationErrorException.class)
			.isThrownBy( () -> SysInfo.hostAddressAlreadyBound(hostname, null) );
    	
    	assertThatExceptionOfType(ApplicationErrorException.class)
			.isThrownBy( () -> SysInfo.hostAddressAlreadyBound(hostname, "aa") );
    	
    	assertThatExceptionOfType(ApplicationErrorException.class)
			.isThrownBy( () -> SysInfo.hostAddressAlreadyBound(hostname, "999") );
    }

    @Test
    void should_not_throw_application_error_exception_when_host_address_not_already_bound() {
    	assertThatNoException().isThrownBy(() -> SysInfo.hostAddressAlreadyBound(hostname, port));
    }

    @Test
    void should_throw_application_error_exception_when_host_address_already_bound() throws IOException {

        InetAddress localhost = InetAddress.getByName(hostname);

        try (ServerSocket socket = new ServerSocket(Utils.toInteger(port), 0, localhost);) {
        	assertThatExceptionOfType(ApplicationErrorException.class)
        		.isThrownBy( () -> SysInfo.hostAddressAlreadyBound(hostname, port) );
        }
    }
}

class FooAppTest {
    
    public static void main(String[] args) {
        // Foo application
    } 
}
