package today.learnslovak.first.data.store.asset;

import android.content.Context;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import javax.inject.Inject;
import timber.log.Timber;

public class LocalAssetDataStore implements AssetDataStore {
  private final Context context;

  @Inject public LocalAssetDataStore(@ApplicationContext Context context) {
    this.context = context;
  }

  @Override public String get(String fileName) {
    String result = "";
    try (InputStream inputStream = fromAssets(fileName)) {
      result = stringFromInputStream(inputStream);
    } catch (IOException e) {
      Timber.e(e);
    }
    return result;
  }

  private ByteArrayOutputStream fromInputStream(InputStream inputStream) throws IOException {
    ByteArrayOutputStream result = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int length;
    while ((length = inputStream.read(buffer)) != -1) {
      result.write(buffer, 0, length);
    }
    return result;
  }

  private InputStream fromAssets(String fileName) throws IOException {
    return context.getAssets().open(fileName);
  }

  private String stringFromInputStream(InputStream inputStream) throws IOException {
    return fromInputStream(inputStream).toString(StandardCharsets.UTF_8.name());
  }

  private byte[] byteArrayFromInputStream(InputStream inputStream) throws IOException {
    return fromInputStream(inputStream).toByteArray();
  }

  /**
   * ~5x slower than pure JDK ByteArrayOutputStream version
   **/
  private String getFromAssetsJdk8(String fileName) throws IOException {
    InputStream inputStream = context.getAssets().open(fileName);
    return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
        .collect(Collectors.joining("\n"));
  }
}
