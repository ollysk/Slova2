package today.learnslovak.first;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataTestUtil {

  public static <T> T getLiveDataValue(final LiveData<T> liveData) {
    final Object[] data = new Object[1];
    final CountDownLatch latch = new CountDownLatch(1);
    Observer<T> observer = o -> {
      data[0] = o;
      latch.countDown();
      ///liveData.removeObserver(this);
    };
    liveData.observeForever(observer);
    try {
      if (!latch.await(5, TimeUnit.SECONDS)) {
        throw new RuntimeException("LiveData was not set");
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //noinspection unchecked
    return (T) data[0];
    //return data[0] != null ? (T) data[0] : (T) new Object();
  }
}
