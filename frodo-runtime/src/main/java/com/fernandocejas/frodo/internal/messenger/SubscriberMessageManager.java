package com.fernandocejas.frodo.internal.messenger;

/**
 *
 */
public class SubscriberMessageManager {

  private final SubscriberMessageBuilder messageBuilder;
  private final DebugLog debugLog;

  public SubscriberMessageManager() {
    this(new SubscriberMessageBuilder(), new DebugLog());
  }

  public SubscriberMessageManager(SubscriberMessageBuilder messageBuilder, DebugLog debugLog) {
    this.messageBuilder = messageBuilder;
    this.debugLog = debugLog;
  }

  private void printMessage(String tag, String message) {
    debugLog.log(tag, message);
  }

  public void printSubscriberOnStart(String subscriberName) {
    final String message = messageBuilder.buildSubscriberOnStartMessage(subscriberName);
    this.printMessage(subscriberName, message);
  }

  public void printSubscriberOnNext(String subscriberName, Object value, String threadName) {
    final String message =
            messageBuilder.buildSubscriberOnNextMessage(subscriberName, value, threadName);
    this.printMessage(subscriberName, message);
  }

  public void printSubscriberOnError(String subscriberName, String error, long executionTimeMillis,
                                     int receivedItems) {
    final String itemTimeMessage =
            messageBuilder.buildSubscriberItemTimeMessage(subscriberName, executionTimeMillis,
                    receivedItems);
    final String onErrorMessage =
            messageBuilder.buildSubscriberOnErrorMessage(subscriberName, error);
    this.printMessage(subscriberName, itemTimeMessage);
    this.printMessage(subscriberName, onErrorMessage);
  }

  public void printSubscriberOnCompleted(String subscriberName, long executionTimeMillis,
                                         int receivedItems) {
    final String itemTimeMessage =
            messageBuilder.buildSubscriberItemTimeMessage(subscriberName, executionTimeMillis,
                    receivedItems);
    final String onCompleteMessage =
            messageBuilder.buildSubscriberOnCompletedMessage(subscriberName);
    this.printMessage(subscriberName, itemTimeMessage);
    this.printMessage(subscriberName, onCompleteMessage);
  }

  public void printSubscriberRequestedItems(String subscriberName, long requestedItems) {
    final String message =
            messageBuilder.buildSubscriberRequestedItemsMessage(subscriberName, requestedItems);
    this.printMessage(subscriberName, message);
  }

  public void printSubscriberUnsubscribe(String subscriberName) {
    final String message = messageBuilder.buildSubscriberUnsubscribeMessage(subscriberName);
    this.printMessage(subscriberName, message);
  }
}
