package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.core.strings.Strings;
import com.fernandocejas.frodo.internal.messenger.SingleMessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo.joinpoint.TestJoinPoint;
import com.fernandocejas.frodo.joinpoint.TestProceedingJoinPoint;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Single;

class SingleRule implements TestRule {

  static final String OBSERVABLE_STREAM_VALUE = "fernando";

  private final Class declaringType;

  private FrodoProceedingJoinPoint frodoProceedingJoinPoint;
  private SingleInfo singleInfo;

  @Mock
  private SingleMessageManager messageManager;

  SingleRule(Class declaringType) {
    MockitoAnnotations.initMocks(this);
    this.declaringType = declaringType;
  }

  @Override
  public Statement apply(Statement statement, Description description) {
    final TestJoinPoint testJoinPoint =
            new TestJoinPoint.Builder(declaringType)
                    .withReturnType(Single.class)
                    .withReturnValue(OBSERVABLE_STREAM_VALUE)
                    .build();
    final TestProceedingJoinPoint testProceedingJoinPoint =
            new TestProceedingJoinPoint(testJoinPoint);
    frodoProceedingJoinPoint = new FrodoProceedingJoinPoint(testProceedingJoinPoint);
    singleInfo = new SingleInfo(frodoProceedingJoinPoint);
    return statement;
  }

  FrodoProceedingJoinPoint joinPoint() {
    return frodoProceedingJoinPoint;
  }

  SingleMessageManager messageManager() {
    return messageManager;
  }

  SingleInfo info() {
    return singleInfo;
  }

  String stringType() {
    return Strings.EMPTY;
  }
}
