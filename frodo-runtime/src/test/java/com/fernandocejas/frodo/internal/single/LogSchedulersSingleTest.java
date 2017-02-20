package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.internal.messenger.SingleMessageManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class LogSchedulersSingleTest {

  @Rule
  public SingleRule singleRule = new SingleRule(this.getClass());

  private LogSchedulersSingle loggableSingle;
  private TestSubscriber subscriber;

  @Mock
  private SingleMessageManager messageManager;

  @Before
  public void setUp() {
    subscriber = new TestSubscriber();
    loggableSingle =
            new LogSchedulersSingle(singleRule.joinPoint(), messageManager,
                    singleRule.info());
  }

  @Test
  public void shouldLogOnlySingleSchedulers() throws Throwable {
    loggableSingle.get(singleRule.stringType()).subscribe(subscriber);

    verify(messageManager).printSingleThreadInfo(any(SingleInfo.class));
    verifyNoMoreInteractions(messageManager);
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
}