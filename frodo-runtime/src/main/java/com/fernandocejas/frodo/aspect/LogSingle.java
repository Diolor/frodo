package com.fernandocejas.frodo.aspect;

import com.fernandocejas.frodo.annotation.RxLogSingle;
import com.fernandocejas.frodo.internal.messenger.SingleMessageManager;
import com.fernandocejas.frodo.internal.single.FrodoSingle;
import com.fernandocejas.frodo.internal.single.LoggableSingleFactory;
import com.fernandocejas.frodo.internal.single.SingleInfo;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;

import rx.Observable;

@Aspect
public class LogSingle {
  private static final String METHOD =
      "execution(@com.fernandocejas.frodo.annotation.RxLogSingle* *(..)) && if()";

  @Pointcut(METHOD)
  public static boolean methodAnnotatedWithRxLogObservable(ProceedingJoinPoint joinPoint) {
    final FrodoProceedingJoinPoint frodoJoinPoint = new FrodoProceedingJoinPoint(joinPoint);
    final Annotation annotation = frodoJoinPoint.getAnnotation(RxLogSingle.class);
    return ((MethodSignature) joinPoint.getSignature()).getReturnType() == Observable.class
        && ((RxLogSingle) annotation).value() != RxLogSingle.Scope.NOTHING;
  }

  @Around("methodAnnotatedWithRxLogObservable(joinPoint)")
  public Object weaveAroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    final FrodoProceedingJoinPoint proceedingJoinPoint = new FrodoProceedingJoinPoint(joinPoint);
    final SingleMessageManager messageManager = new SingleMessageManager();
    final LoggableSingleFactory observableFactory =
        new LoggableSingleFactory(proceedingJoinPoint, messageManager,
            new SingleInfo(proceedingJoinPoint));

    return new FrodoSingle(proceedingJoinPoint, messageManager,
        observableFactory).getSingle();
  }
}
