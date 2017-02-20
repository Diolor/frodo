package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.annotation.RxLogSingle;
import com.fernandocejas.frodo.internal.messenger.SingleMessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;

import java.lang.annotation.Annotation;

public class FrodoSingle {

  private final FrodoProceedingJoinPoint joinPoint;
  private final SingleMessageManager messageManager;
  private final SingleInfo singleInfo;
  private final LoggableSingleFactory singleFactory;

  public FrodoSingle(FrodoProceedingJoinPoint joinPoint, SingleMessageManager messageManager,
                     LoggableSingleFactory singleFactory) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.singleInfo = new SingleInfo(joinPoint);
    this.singleFactory = singleFactory;
  }

  public rx.Single getSingle() throws Throwable {
    messageManager.printSingleInfo(singleInfo);
    final Class singleType = joinPoint.getGenericReturnTypes().get(0);
    final Annotation annotation = joinPoint.getAnnotation(RxLogSingle.class);
    return singleFactory.create(annotation).get(singleType);
  }

}
