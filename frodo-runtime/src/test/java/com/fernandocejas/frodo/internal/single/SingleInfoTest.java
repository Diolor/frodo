package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.joinpoint.FrodoJoinPoint;
import com.fernandocejas.frodo.joinpoint.TestJoinPoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Single;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SingleInfoTest {

  private static final String OBSERVABLE_STREAM_VALUE = "fernando";

  private SingleInfo singleInfo;

  @Before
  public void setUp() {
    final TestJoinPoint testJoinPoint = new TestJoinPoint.Builder(this.getClass())
            .withReturnType(Single.class)
            .withReturnValue(OBSERVABLE_STREAM_VALUE)
            .build();
    final FrodoJoinPoint frodoJoinPoint = new FrodoJoinPoint(testJoinPoint);
    singleInfo = new SingleInfo(frodoJoinPoint);
  }

  @Test
  public void shouldReturnAbsentValues() {
    final Optional<String> optionalSubscribeOnThread = singleInfo.getSubscribeOnThread();
    final Optional<String> optionalObserveOnThread = singleInfo.getObserveOnThread();
    final Optional<Long> optionalTotalExecutionTime = singleInfo.getTotalExecutionTime();

    assertThat(optionalSubscribeOnThread.isPresent()).isFalse();
    assertThat(optionalObserveOnThread.isPresent()).isFalse();
    assertThat(optionalTotalExecutionTime.isPresent()).isFalse();
  }

  @Test
  public void shouldReturnPresentValues() {
    singleInfo.setSubscribeOnThread("thread");
    singleInfo.setObserveOnThread("thread");
    singleInfo.setTotalExecutionTime(1000);

    final Optional<String> optionalSubscribeOnThread = singleInfo.getSubscribeOnThread();
    final Optional<String> optionalObserveOnThread = singleInfo.getObserveOnThread();
    final Optional<Long> optionalTotalExecutionTime = singleInfo.getTotalExecutionTime();

    assertThat(optionalSubscribeOnThread.isPresent()).isTrue();
    assertThat(optionalObserveOnThread.isPresent()).isTrue();
    assertThat(optionalTotalExecutionTime.isPresent()).isTrue();
  }
}