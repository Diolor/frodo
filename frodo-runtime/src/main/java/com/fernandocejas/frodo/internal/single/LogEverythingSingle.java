package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.internal.Counter;
import com.fernandocejas.frodo.internal.StopWatch;
import com.fernandocejas.frodo.internal.messenger.SingleMessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;

import rx.Notification;
import rx.Single;
import rx.functions.Action0;
import rx.functions.Action1;

@SuppressWarnings("unchecked")
class LogEverythingSingle extends LoggableSingle {

	LogEverythingSingle(FrodoProceedingJoinPoint joinPoint, SingleMessageManager messageManager,
						SingleInfo singleInfo) {
		super(joinPoint, messageManager, singleInfo);
	}

	@Override
	<T> rx.Single<T> get(T type) throws Throwable {
		final StopWatch stopWatch = new StopWatch();
		final Counter emittedItems = new Counter(joinPoint.getMethodName());
		return ((Single<T>) joinPoint.proceed())
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						stopWatch.start();
						messageManager.printSingleOnSubscribe(singleInfo);
					}
				})
				.doOnEach(new Action1<Notification<? extends T>>() {
					@Override
					public void call(Notification<? extends T> notification) {
						if (!singleInfo.getSubscribeOnThread().isPresent()
								&& (notification.isOnNext() || notification.isOnError())) {
							singleInfo.setSubscribeOnThread(Thread.currentThread().getName());
						}
					}
				})
				.doOnSuccess(new Action1<T>() {
					@Override
					public void call(T value) {
						emittedItems.increment();
						messageManager.printSingleOnSuccessWithValue(singleInfo, value);
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
						if (!singleInfo.getObserveOnThread().isPresent()) {
							singleInfo.setObserveOnThread(Thread.currentThread().getName());
						}
						messageManager.printSingleThreadInfo(singleInfo);
						messageManager.printSingleOnUnsubscribe(singleInfo);
					}
				});
	}
}
