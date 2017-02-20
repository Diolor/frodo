package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.fernandocejas.frodo.core.strings.Strings;
import com.fernandocejas.frodo.internal.messenger.ObservableMessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import java.lang.annotation.Annotation;

@SuppressWarnings("unchecked")
public class FrodoObservable {

  private final FrodoProceedingJoinPoint joinPoint;
  private final ObservableMessageManager messageManager;
  private final ObservableInfo observableInfo;
  private final LoggableObservableFactory observableFactory;

  private String observeOnThread = Strings.EMPTY;

  public FrodoObservable(FrodoProceedingJoinPoint joinPoint, ObservableMessageManager messageManager,
      LoggableObservableFactory observableFactory) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.observableInfo = new ObservableInfo(joinPoint);
    this.observableFactory = observableFactory;
  }

  public rx.Observable getObservable() throws Throwable {
    messageManager.printObservableInfo(observableInfo);
    final Class observableType = joinPoint.getGenericReturnTypes().get(0);
    final Annotation annotation = joinPoint.getAnnotation(RxLogObservable.class);
    return observableFactory.create(annotation).get(observableType);
  }
}
