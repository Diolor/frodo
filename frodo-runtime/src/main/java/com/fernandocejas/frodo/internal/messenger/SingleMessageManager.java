package com.fernandocejas.frodo.internal.messenger;

import com.fernandocejas.frodo.internal.single.SingleInfo;

public class SingleMessageManager {

  private final SingleMessageBuilder messageBuilder;
  private final DebugLog debugLog;

  public SingleMessageManager() {
    this(new SingleMessageBuilder(), new DebugLog());
  }

  public SingleMessageManager(SingleMessageBuilder messageBuilder, DebugLog debugLog) {
    this.messageBuilder = messageBuilder;
    this.debugLog = debugLog;
  }

  private void printMessage(String tag, String message) {
    debugLog.log(tag, message);
  }

  public void printSingleInfo(SingleInfo singleInfo) {
    final String message = messageBuilder.buildSingleInfoMessage(singleInfo);
    this.printMessage(singleInfo.getClassSimpleName(), message);
  }

  public void printSingleOnSubscribe(SingleInfo singleInfo) {
    final String message = messageBuilder.buildSingleOnSubscribeMessage(singleInfo);
    this.printMessage(singleInfo.getClassSimpleName(), message);
  }

  public <T> void printSingleOnSuccessWithValue(SingleInfo singleInfo, T value) {
    final String message =
            messageBuilder.buildSingleOnSuccessWithValueMessage(singleInfo, value);
    this.printMessage(singleInfo.getClassSimpleName(), message);
  }

  public void printSingleOnSuccess(SingleInfo singleInfo) {
    final String message =
            messageBuilder.buildSingleOnSuccessMessage(singleInfo);
    this.printMessage(singleInfo.getClassSimpleName(), message);
  }

  public void printSingleOnError(SingleInfo singleInfo,
                                 Throwable throwable) {
    final String message =
            messageBuilder.buildSingleOnErrorMessage(singleInfo, throwable.getMessage());
    this.printMessage(singleInfo.getClassSimpleName(), message);
  }

  public void printSingleOnUnsubscribe(SingleInfo singleInfo) {
    final String message = messageBuilder.buildSingleOnUnsubscribeMessage(singleInfo);
    this.printMessage(singleInfo.getClassSimpleName(), message);
  }

  public void printSingleThreadInfo(SingleInfo singleInfo) {
    if (singleInfo.getSubscribeOnThread().isPresent() ||
            singleInfo.getObserveOnThread().isPresent()) {
      final String message = messageBuilder.buildSingleThreadInfoMessage(singleInfo);
      this.printMessage(singleInfo.getClassSimpleName(), message);
    }
  }

}
