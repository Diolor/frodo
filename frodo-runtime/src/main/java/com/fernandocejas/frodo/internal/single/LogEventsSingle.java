package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.internal.messenger.SingleMessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;

import rx.Single;
import rx.functions.Action0;
import rx.functions.Action1;

@SuppressWarnings("unchecked")
class LogEventsSingle extends LoggableSingle {
  LogEventsSingle(FrodoProceedingJoinPoint joinPoint,
                  SingleMessageManager messageManager, SingleInfo singleInfo) {
    super(joinPoint, messageManager, singleInfo);
  }

  @Override
  <T> Single<T> get(T type) throws Throwable {
    return ((Single<T>) joinPoint.proceed())
            .doOnSubscribe(new Action0() {
              @Override
              public void call() {
                messageManager.printSingleOnSubscribe(singleInfo);
              }
            })
            .doOnSuccess(new Action1<T>() {
              @Override
              public void call(T value) {
                messageManager.printSingleOnSuccess(singleInfo);
              }
            })
            .doOnError(new Action1<Throwable>() {
              @Override
              public void call(Throwable throwable) {
                messageManager.printSingleOnError(singleInfo, throwable);
              }
            })
            .doOnUnsubscribe(new Action0() {
              @Override
              public void call() {
                messageManager.printSingleOnUnsubscribe(singleInfo);
              }
            });
  }
}
