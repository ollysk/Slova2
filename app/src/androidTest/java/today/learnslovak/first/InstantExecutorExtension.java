package today.learnslovak.first;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * This class is the JUnit5 replacement fo JUnit4 Test Rule that swaps the background executor used
 * by the Architecture Components with a different one which executes each task synchronously.
 *
 * @Rule public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
 */

public class InstantExecutorExtension implements BeforeEachCallback, AfterEachCallback {

  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    ArchTaskExecutor.getInstance().setDelegate(null);
  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {

    ArchTaskExecutor.getInstance().setDelegate(new TaskExecutor() {
      @Override
      public void executeOnDiskIO(@NonNull Runnable runnable) {
        runnable.run();
      }

      @Override
      public void postToMainThread(@NonNull Runnable runnable) {
        runnable.run();
      }

      @Override
      public boolean isMainThread() {
        return true;
      }
    });
  }
}
