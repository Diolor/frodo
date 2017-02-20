package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.internal.messenger.SingleMessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;

import rx.Single;

@SuppressWarnings("unchecked") class LogNothingSingle extends LoggableSingle {

  LogNothingSingle(FrodoProceedingJoinPoint joinPoint,
                   SingleMessageManager messageManager, SingleInfo singleInfo) {
    super(joinPoint, messageManager, singleInfo);
  }

  @Override <T> Single<T> get(T type) throws Throwable {
    return ((Single<T>) joinPoint.proceed());
  }
}
