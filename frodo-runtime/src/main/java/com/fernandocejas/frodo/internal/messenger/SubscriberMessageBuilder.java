package com.fernandocejas.frodo.internal.messenger;

/**
 * Class used to build different messages that will be shown in debug mode
 */
class SubscriberMessageBuilder extends MessengerBuilder {

  private static final String SUBSCRIBER_LABEL = LOG_START + "Subscriber";
  private static final String LABEL_SUBSCRIBER_ON_START = "onStart()";
  private static final String LABEL_SUBSCRIBER_ON_NEXT = "onNext()";
  private static final String LABEL_SUBSCRIBER_ON_ERROR = "onError()";
  private static final String LABEL_SUBSCRIBER_ON_COMPLETED = "onCompleted()";
  private static final String LABEL_SUBSCRIBER_UN_SUBSCRIBE = "unSubscribe()";
  private static final String REQUESTED_ELEMENTS_LABEL = LOG_START + "Requested" + VALUE_SEPARATOR;
  private static final String RECEIVED_ELEMENTS_LABEL = LOG_START + "Received" + VALUE_SEPARATOR;
  private static final String LABEL_MESSAGE_NULL_OBSERVABLES = "You received a null observable";

  SubscriberMessageBuilder() {
  }


  String buildSubscriberOnStartMessage(String subscriberName) {
    final StringBuilder message = buildSubscriberSB();
    message.append(SEPARATOR);
    message.append(subscriberName);
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SUBSCRIBER_ON_START);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSubscriberOnNextMessage(String subscriberName, Object value, String threadName) {
    final StringBuilder message = buildSubscriberSB();
    message.append(SEPARATOR);
    message.append(subscriberName);
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SUBSCRIBER_ON_NEXT);
    message.append(VALUE_SEPARATOR);
    message.append(value != null ? value.toString() : LABEL_MESSAGE_NULL_OBSERVABLES);
    message.append(SEPARATOR);
    message.append(LABEL_SUBSCRIBER_OBSERVE_ON);
    message.append(threadName);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSubscriberOnErrorMessage(String subscriberName, String error) {
    final StringBuilder message = buildSubscriberSB();
    message.append(SEPARATOR);
    message.append(subscriberName);
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SUBSCRIBER_ON_ERROR);
    message.append(VALUE_SEPARATOR);
    message.append(error);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSubscriberOnCompletedMessage(String subscriberName) {
    final StringBuilder message = buildSubscriberSB();
    message.append(SEPARATOR);
    message.append(subscriberName);
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SUBSCRIBER_ON_COMPLETED);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSubscriberItemTimeMessage(String subscriberName, long executionTimeMillis,
                                        int receivedItems) {
    final StringBuilder message = buildSubscriberSB();
    message.append(SEPARATOR);
    message.append(subscriberName);
    message.append(VALUE_SEPARATOR);
    message.append(RECEIVED_ELEMENTS_LABEL);
    message.append(receivedItems);
    message.append(receivedItems == 1 ? LABEL_ELEMENT_SINGULAR : LABEL_ELEMENT_PLURAL);
    message.append(SEPARATOR);
    message.append(TIME_LABEL);
    message.append(executionTimeMillis);
    message.append(TIME_MILLIS);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSubscriberRequestedItemsMessage(String subscriberName, long requestedItems) {
    final StringBuilder message = buildSubscriberSB();
    message.append(SEPARATOR);
    message.append(subscriberName);
    message.append(VALUE_SEPARATOR);
    message.append(REQUESTED_ELEMENTS_LABEL);
    message.append(requestedItems);
    message.append(requestedItems == 1 ? LABEL_ELEMENT_SINGULAR : LABEL_ELEMENT_PLURAL);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSubscriberUnsubscribeMessage(String subscriberName) {
    final StringBuilder message = buildSubscriberSB();
    message.append(SEPARATOR);
    message.append(subscriberName);
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SUBSCRIBER_UN_SUBSCRIBE);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }


  private StringBuilder buildSubscriberSB() {
    final int avgStringSize = 75;
    final StringBuilder message = new StringBuilder(avgStringSize + LIBRARY_LABEL.length());
    message.append(LIBRARY_LABEL);
    message.append(LOG_ENCLOSING_OPEN);
    message.append(SUBSCRIBER_LABEL);
    return message;
  }

}
