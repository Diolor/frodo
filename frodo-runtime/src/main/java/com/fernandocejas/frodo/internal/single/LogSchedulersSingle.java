package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.internal.messenger.SingleMessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;

import rx.Notification;
import rx.Single;
import rx.functions.Action0;
import rx.functions.Action1;

@SuppressWarnings("unchecked")
class LogSchedulersSingle extends LoggableSingle {
	LogSchedulersSingle(FrodoProceedingJoinPoint joinPoint,
						SingleMessageManager messageManager, SingleInfo singleInfo) {
		super(joinPoint, messageManager, singleInfo);
	}

	@Override
	<T> Single<T> get(T type) throws Throwable {
		return ((Single<T>) joinPoint.proceed())
				.doOnEach(new Action1<Notification<? extends T>>() {
					@Override
					public void call(Notification<? extends T> notification) {
						if (!singleInfo.getSubscribeOnThread().isPresent()
								&& (notification.isOnNext() || notification.isOnError())) {
							singleInfo.setSubscribeOnThread(Thread.currentThread().getName());
						}
					}
				})
				.doOnUnsubscribe(new Action0() {
					@Override
					public void call() {
						if (!singleInfo.getObserveOnThread().isPresent()) {
							singleInfo.setObserveOnThread(Thread.currentThread().getName());
						}
						messageManager.printSingleThreadInfo(singleInfo);
					}
				});
	}
}
