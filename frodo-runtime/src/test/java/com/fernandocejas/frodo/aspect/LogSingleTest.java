package com.fernandocejas.frodo.aspect;

import com.fernandocejas.frodo.joinpoint.TestJoinPoint;
import com.fernandocejas.frodo.joinpoint.TestProceedingJoinPoint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LogSingleTest {

  @Test
  public void shouldNotWeaveAroundMethodReturningOtherTypeThanSingle() {
    final TestJoinPoint joinPoint = new TestJoinPoint.Builder(this.getClass(), "toString")
        .withReturnType(this.getClass()).build();
    final TestProceedingJoinPoint proceedingJoinPoint = new TestProceedingJoinPoint(joinPoint);

    assertThat(LogSingle.methodAnnotatedWithRxLogSingle(proceedingJoinPoint)).isFalse();
  }
}