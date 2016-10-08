package tutorial;



import io.reactors.japi.*;
/*!begin-include!*/
/*!begin-code!*/
import io.reactors.japi.services.Log;
/*!end-code!*/
/*!end-include(reactors-java-services-log-import.html)!*/
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;



public class services {
  static FakeSystem System = new FakeSystem();

  @Test
  public void useLog() {
    ReactorSystem system = ReactorSystem.create("test-system");
    try {
      /*!begin-include!*/
      /*!begin-code!*/
      Proto<String> proto = Reactor.apply(self -> {
        Log log = system.service(Log.class);
        log.info("Test reactor started!");
        self.main().seal();
      });
      system.spawn(proto);
      /*!end-code!*/
      /*!end-include(reactors-java-services-log-example.html)!*/

      Thread.sleep(100);
    } catch (Throwable t) {
      throw new RuntimeException(t);
    } finally {
      system.shutdown();
    }
  }

  @Test
  public void useClock() {
    ReactorSystem system = ReactorSystem.create("test-system");
    try {
      /*!begin-include!*/
      /*!begin-code!*/
      Proto<String> proto = Reactor.apply(self -> {
        system.clock().timeout(1000).onEvent(v -> {
          System.out.println("Timeout!");
          self.main().seal();
        });
      });
      system.spawn(proto);
      /*!end-code!*/
      /*!end-include(reactors-java-services-log-example.html)!*/

      Assert.assertEquals("Timeout!", System.out.queue.take());
    } catch (Throwable t) {
      throw new RuntimeException(t);
    } finally {
      system.shutdown();
    }
  }
}
