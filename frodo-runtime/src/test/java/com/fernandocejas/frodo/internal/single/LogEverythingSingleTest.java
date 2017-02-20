package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.internal.messenger.SingleMessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class LogEverythingSingleTest {

  @Rule
  public SingleRule singleRule = new SingleRule(this.getClass());

  private LogEverythingSingle loggableSingle;
  private TestSubscriber subscriber;

  @Mock
  private SingleMessageManager messageManager;

  @Before
  public void setUp() {
    subscriber = new TestSubscriber();
    loggableSingle = new LogEverythingSingle(singleRule.joinPoint(), messageManager,
            singleRule.info());
  }

  @Test
  public void shouldLogEverythingSingle() throws Throwable {
    loggableSingle.get(singleRule.stringType()).subscribe(subscriber);

    verify(messageManager).printSingleOnSubscribe(any(SingleInfo.class));
    verify(messageManager).printSingleOnSuccessWithValue(any(SingleInfo.class), anyString());
    verify(messageManager).printSingleOnUnsubscribe(any(SingleInfo.class));
    verify(messageManager).printSingleThreadInfo(any(SingleInfo.class));
  }

  @Test
  public void shouldFillInSingleBasicInfo() throws Throwable {
    loggableSingle.get(singleRule.stringType()).subscribe(subscriber);
    final SingleInfo observableInfo = loggableSingle.getInfo();
    final FrodoProceedingJoinPoint frodoProceedingJoinPoint = singleRule.joinPoint();

    assertThat(observableInfo.getClassSimpleName()).isEqualTo(
            frodoProceedingJoinPoint.getClassSimpleName());
    assertThat(observableInfo.getJoinPoint()).isEqualTo(frodoProceedingJoinPoint);
    assertThat(observableInfo.getMethodName()).isEqualTo(frodoProceedingJoinPoint.getMethodName());
  }

  @Test
  public void shouldFillInSingleThreadInfo() throws Throwable {
    loggableSingle.get(singleRule.stringType())
            .subscribeOn(Schedulers.immediate())
            .observeOn(Schedulers.immediate())
            .subscribe(subscriber);

    final SingleInfo observableInfo = loggableSingle.getInfo();
    final Optional<String> subscribeOnThread = observableInfo.getSubscribeOnThread();
    final Optional<String> observeOnThread = observableInfo.getObserveOnThread();
    final String currentThreadName = Thread.currentThread().getName();

    assertThat(subscribeOnThread.isPresent()).isTrue();
    assertThat(observeOnThread.isPresent()).isTrue();
    assertThat(subscribeOnThread.get()).isEqualTo(currentThreadName);
    assertThat(observeOnThread.get()).isEqualTo(currentThreadName);
  }

  @Test
  public void shouldFillInSingleItemsInfo() throws Throwable {
    loggableSingle.get(singleRule.stringType())
            .delay(2, TimeUnit.SECONDS)
            .subscribe(subscriber);

    final SingleInfo observableInfo = loggableSingle.getInfo();
    final Optional<Integer> totalEmittedItems = observableInfo.getTotalEmittedItems();
    final Optional<Long> totalExecutionTime = observableInfo.getTotalExecutionTime();

    assertThat(totalEmittedItems.isPresent()).isTrue();
    assertThat(totalExecutionTime.isPresent()).isTrue();
    assertThat(totalEmittedItems.get()).isEqualTo(1);
  }
}