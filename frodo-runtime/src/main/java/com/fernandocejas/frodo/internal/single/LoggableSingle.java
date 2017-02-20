package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.core.annotations.VisibleForTesting;
import com.fernandocejas.frodo.internal.messenger.SingleMessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;

abstract class LoggableSingle {

	final FrodoProceedingJoinPoint joinPoint;
	final SingleMessageManager messageManager;
	final SingleInfo singleInfo;

	LoggableSingle(FrodoProceedingJoinPoint joinPoint, SingleMessageManager messageManager,
				   SingleInfo singleInfo) {
		this.joinPoint = joinPoint;
		this.messageManager = messageManager;
		this.singleInfo = new SingleInfo(joinPoint);
	}

	abstract <T> rx.Single<T> get(T type) throws Throwable;

	@VisibleForTesting
	SingleInfo getInfo() {
		return singleInfo;
	}
}
