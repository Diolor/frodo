package com.fernandocejas.frodo.internal.messenger;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.internal.observable.ObservableInfo;
import com.fernandocejas.frodo.joinpoint.FrodoJoinPoint;

import java.util.List;

/**
 * Class used to build different messages that will be shown in debug mode
 */
class ObservableMessageBuilder extends MessengerBuilder {

  private static final String OBSERVABLE_LABEL = LOG_START + "Observable";
  private static final String LABEL_OBSERVABLE_ON_SUBSCRIBE = "onSubscribe()";
  private static final String LABEL_OBSERVABLE_ON_NEXT = "onNext()";
  private static final String LABEL_OBSERVABLE_ON_ERROR = "onError()";
  private static final String LABEL_OBSERVABLE_ON_COMPLETED = "onCompleted()";
  private static final String LABEL_OBSERVABLE_ON_TERMINATE = "onTerminate()";
  private static final String LABEL_OBSERVABLE_ON_UNSUBSCRIBE = "onUnsubscribe()";

  ObservableMessageBuilder() {
  }

  String buildObservableInfoMessage(ObservableInfo observableInfo) {
    final FrodoJoinPoint joinPoint = observableInfo.getJoinPoint();
    final StringBuilder message = buildObservableSB();
    message.append(SEPARATOR);
    message.append(CLASS_LABEL);
    message.append(observableInfo.getClassSimpleName());
    message.append(SEPARATOR);
    message.append(METHOD_LABEL);
    message.append(observableInfo.getMethodName());
    message.append(buildMethodSignatureWithValues(joinPoint));
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnSubscribeMessage(ObservableInfo observableInfo) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_SUBSCRIBE);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  <T> String buildObservableOnNextWithValueMessage(ObservableInfo observableInfo, T value) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_NEXT);
    message.append(VALUE_SEPARATOR);
    message.append(String.valueOf(value));
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnNextMessage(ObservableInfo observableInfo) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_NEXT);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnErrorMessage(ObservableInfo observableInfo, String errorMessage) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_ERROR);
    message.append(VALUE_SEPARATOR);
    message.append(TEXT_ENCLOSING_SYMBOL);
    message.append(errorMessage);
    message.append(TEXT_ENCLOSING_SYMBOL);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnCompletedMessage(ObservableInfo observableInfo) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_COMPLETED);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnTerminateMessage(ObservableInfo observableInfo) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_TERMINATE);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnUnsubscribeMessage(ObservableInfo observableInfo) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_UNSUBSCRIBE);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableItemTimeInfoMessage(ObservableInfo observableInfo) {
    final int totalEmittedItems = observableInfo.getTotalEmittedItems().or(0);
    final long totalExecutionTime = observableInfo.getTotalExecutionTime().or(0L);
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(EMITTED_ELEMENTS_LABEL);
    message.append(totalEmittedItems);
    message.append(totalEmittedItems == 1 ? LABEL_ELEMENT_SINGULAR : LABEL_ELEMENT_PLURAL);
    message.append(SEPARATOR);
    message.append(TIME_LABEL);
    message.append(totalExecutionTime);
    message.append(TIME_MILLIS);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableThreadInfoMessage(ObservableInfo observableInfo) {
    final Optional<String> subscribeOnThread = observableInfo.getSubscribeOnThread();
    final Optional<String> observeOnThread = observableInfo.getObserveOnThread();
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    if (subscribeOnThread.isPresent()) {
      message.append(LABEL_SUBSCRIBER_SUBSCRIBE_ON);
      message.append(subscribeOnThread.get());
    }
    if (observeOnThread.isPresent()) {
      message.append(SEPARATOR);
      message.append(LABEL_SUBSCRIBER_OBSERVE_ON);
      message.append(observeOnThread.get());
    }
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  private StringBuilder buildObservableSB() {
    final int avgStringSize = 75;
    final StringBuilder message = new StringBuilder(avgStringSize + LIBRARY_LABEL.length());
    message.append(LIBRARY_LABEL);
    message.append(LOG_ENCLOSING_OPEN);
    message.append(OBSERVABLE_LABEL);
    return message;
  }

  private String buildMethodSignatureWithValues(FrodoJoinPoint joinPoint) {
    final int avg = 30;
    final StringBuilder stringBuilder = new StringBuilder(avg + joinPoint.getMethodName().length());
    stringBuilder.append("(");
    List<String> methodParamNames = joinPoint.getMethodParamNamesList();
    if (methodParamNames != null && !methodParamNames.isEmpty()) {
      for (int i = 0; i < joinPoint.getMethodParamNamesList().size(); i++) {
        stringBuilder.append(methodParamNames.get(i));
        stringBuilder.append("=");
        stringBuilder.append("'");
        stringBuilder.append(String.valueOf(joinPoint.getMethodParamValuesList().get(i)));
        stringBuilder.append("'");
        if ((i != methodParamNames.size() - 1)) {
          stringBuilder.append(", ");
        }
      }
    }
    stringBuilder.append(")");

    return stringBuilder.toString();
  }
}
