package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.annotation.RxLogSingle;
import com.fernandocejas.frodo.internal.messenger.SingleMessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;

import java.lang.annotation.Annotation;

@SuppressWarnings("unchecked") public class LoggableSingleFactory {

  private final FrodoProceedingJoinPoint joinPoint;
  private final SingleMessageManager messageManager;
  private final SingleInfo singleInfo;

  public LoggableSingleFactory(FrodoProceedingJoinPoint joinPoint,
                               SingleMessageManager messageManager, SingleInfo singleInfo) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.singleInfo = singleInfo;
  }

  LoggableSingle create(Annotation annotation) {
    final Class observableType = joinPoint.getGenericReturnTypes().get(0);
    if (annotation != null) {
      switch (((RxLogSingle) annotation).value()) {
        case NOTHING:
          return new LogNothingSingle(joinPoint, messageManager, singleInfo);
        case SCHEDULERS:
          return new LogSchedulersSingle(joinPoint, messageManager, singleInfo);
        case EVENTS:
          return new LogEventsSingle(joinPoint, messageManager, singleInfo);
        case EVERYTHING:
        default:
          return new LogEverythingSingle(joinPoint, messageManager, singleInfo);
      }
    } else {
      return new LogNothingSingle(joinPoint, messageManager, singleInfo);
    }
  }
}
