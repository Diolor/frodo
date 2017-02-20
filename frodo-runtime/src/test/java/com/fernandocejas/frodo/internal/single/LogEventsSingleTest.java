package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.internal.messenger.SingleMessageManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.observers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SuppressWarnings("unchecked") @RunWith(MockitoJUnitRunner.class)
public class LogEventsSingleTest {

  @Rule public SingleRule singleRule = new SingleRule(this.getClass());

  private LogEventsSingle loggableSingle;
  private TestSubscriber subscriber;

  @Mock private SingleMessageManager messageManager;

  @Before
  public void setUp() {
    subscriber = new TestSubscriber();
    loggableSingle =
        new LogEventsSingle(singleRule.joinPoint(), messageManager, singleRule.info());
  }

  @Test
  public void shouldLogOnlySingleEvents() throws Throwable {
    loggableSingle.get(singleRule.stringType()).subscribe(subscriber);

    verify(messageManager).printSingleOnSubscribe(any(SingleInfo.class));
    verify(messageManager).printSingleOnSuccess(any(SingleInfo.class));
    verify(messageManager).printSingleOnUnsubscribe(any(SingleInfo.class));
    verifyNoMoreInteractions(messageManager);
  }
}