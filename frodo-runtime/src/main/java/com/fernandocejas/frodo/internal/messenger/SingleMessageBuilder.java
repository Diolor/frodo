package com.fernandocejas.frodo.internal.messenger;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.internal.single.SingleInfo;
import com.fernandocejas.frodo.joinpoint.FrodoJoinPoint;

import java.util.List;

/**
 * Class used to build different messages that will be shown in debug mode
 */
class SingleMessageBuilder extends MessengerBuilder {

  private static final String SINGLE_LABEL = LOG_START + "Single";
  private static final String LABEL_SINGLE_ON_SUBSCRIBE = "onSubscribe()";
  private static final String LABEL_SINGLE_ON_SUCCESS = "onSuccess()";
  private static final String LABEL_SINGLE_ON_ERROR = "onError()";
  private static final String LABEL_SINGLE_ON_UNSUBSCRIBE = "onUnsubscribe()";


  SingleMessageBuilder() {
  }

  String buildSingleInfoMessage(SingleInfo observableInfo) {
    final FrodoJoinPoint joinPoint = observableInfo.getJoinPoint();
    final StringBuilder message = buildSingleSB();
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

  String buildSingleOnSubscribeMessage(SingleInfo observableInfo) {
    final StringBuilder message = buildSingleSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SINGLE_ON_SUBSCRIBE);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  <T> String buildSingleOnSuccessWithValueMessage(SingleInfo observableInfo, T value) {
    final StringBuilder message = buildSingleSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SINGLE_ON_SUCCESS);
    message.append(VALUE_SEPARATOR);
    message.append(String.valueOf(value));
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSingleOnSuccessMessage(SingleInfo observableInfo) {
    final StringBuilder message = buildSingleSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SINGLE_ON_SUCCESS);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSingleOnErrorMessage(SingleInfo observableInfo, String errorMessage) {
    final StringBuilder message = buildSingleSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SINGLE_ON_ERROR);
    message.append(VALUE_SEPARATOR);
    message.append(TEXT_ENCLOSING_SYMBOL);
    message.append(errorMessage);
    message.append(TEXT_ENCLOSING_SYMBOL);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }


  String buildSingleOnUnsubscribeMessage(SingleInfo observableInfo) {
    final StringBuilder message = buildSingleSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SINGLE_ON_UNSUBSCRIBE);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }


  String buildSingleThreadInfoMessage(SingleInfo observableInfo) {
    final Optional<String> subscribeOnThread = observableInfo.getSubscribeOnThread();
    final Optional<String> observeOnThread = observableInfo.getObserveOnThread();
    final StringBuilder message = buildSingleSB();
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

  private StringBuilder buildSingleSB() {
    final int avgStringSize = 75;
    final StringBuilder message = new StringBuilder(avgStringSize + LIBRARY_LABEL.length());
    message.append(LIBRARY_LABEL);
    message.append(LOG_ENCLOSING_OPEN);
    message.append(SINGLE_LABEL);
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
