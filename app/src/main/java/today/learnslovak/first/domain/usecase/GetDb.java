package today.learnslovak.first.domain.usecase;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import javax.inject.Inject;
import today.learnslovak.first.data.repo.DbPopulatorRepo;
import today.learnslovak.first.domain.model.Config;
import today.learnslovak.first.domain.model.Pref;
import today.learnslovak.first.domain.repo.ConfigRepo;
import today.learnslovak.first.domain.repo.PrefRepo;

public class GetDb {

  private final ConfigRepo configRepo;
  private final DbPopulatorRepo dbPopulatorRepo;
  private final ScheduledThreadPoolExecutor executor;
  private final PrefRepo pref;

  @Inject public GetDb(ConfigRepo configRepo,
      DbPopulatorRepo dbPopulatorRepo, ScheduledThreadPoolExecutor executor,
      PrefRepo pref) {
    this.configRepo = configRepo;
    this.dbPopulatorRepo = dbPopulatorRepo;
    this.executor = executor;
    this.pref = pref;
  }

  public CompletableFuture<Config> getConfig() {
    return configRepo.getConfig();
  }

  public void populateDbIfNeeded() {
    CompletableFuture<Integer> wordsCount = getWordCount();
    wordsCount.thenAcceptAsync(this::populateFromLocalIfNeeded, executor);
    getConfig().thenAcceptAsync((c) ->
        wordsCount.thenAcceptAsync(this::populateFromRemoteIfNeeded, executor)
            .thenRun(() -> patchWordTableIfNeeded(c.getWordPatchLevel()))
            .thenRun(() -> patchSnippetTableIfNeeded(c.getSnippetPatchLevel())), executor);
  }

  CompletableFuture<Integer> getWordCount() {
    return dbPopulatorRepo.getWordCount();//.thenAcceptAsync((wordCount) -> populateTablesIfNeeded(wordCount));
  }

  public void populateFromLocalIfNeeded(int wordCount) {
    if (shouldPopulateFromLocal(wordCount)) {
      dbPopulatorRepo.populateAllFromLocal();
    }
  }

  //todo thoughts
  //If incorporate all patches as a service pack, there will be no need for new users to download all patches
  //so, if user downloads full db, his patchId should be set to the server's patchLevel
  public void populateFromRemoteIfNeeded(int wordCount) {
    if (shouldPopulateFromRemote(wordCount)) {
      dbPopulatorRepo.populateWordTable();
      dbPopulatorRepo.populateSnippetTable();
    }
  }

  public void patchWordTableIfNeeded(int toPatchLevel) {
    int currentPatchLevel = pref.get(Pref.WORD_PATCH_LEVEL, 0);
    for (int i = ++currentPatchLevel; i <= toPatchLevel; i++) {
      dbPopulatorRepo.patchWordTable(i);
    }
    pref.put(Pref.WORD_PATCH_LEVEL, toPatchLevel);
  }

  public void patchSnippetTableIfNeeded(int toPatchLevel) {
    int currentPatchLevel = pref.get(Pref.SNIPPET_PATCH_LEVEL, 0);
    for (int i = ++currentPatchLevel; i <= toPatchLevel; i++) {
      dbPopulatorRepo.patchSnippetTable(i);
    }
    pref.put(Pref.SNIPPET_PATCH_LEVEL, toPatchLevel);
  }

  boolean shouldPopulateFromLocal(int wordCount) {
    return wordCount < 10;
  }

  boolean shouldPopulateFromRemote(int wordCount) {
    return wordCount < 1000;
  }
}
