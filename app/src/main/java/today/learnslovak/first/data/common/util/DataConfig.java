package today.learnslovak.first.data.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DataConfig {

  //API 28 doesn't accept http, https only
  public final String SERVER_URL = "http://192.168.0.2/";
  public final String SERVER_ANNOUNCE_FILENAME = "announce.json";
  public final String SERVER_PATCH_DIR = "patches/";
  public final String DB_NAME = "slova.db";
  public final String TABLE_NAME_MAIN = "main";
  public final String TABLE_NAME_SNIPPET = "snippet";
  public final String TABLE_NAME_SKIP = "skip";
  //public final String TABLE_NAME_MAIN = "main";
  public final String TABLE_NAME_MAIN_FTS = "mainFts";
  public final String TABLE_NAME_MAIN_ASCII_FTS = "mainAsciiFts";
}
