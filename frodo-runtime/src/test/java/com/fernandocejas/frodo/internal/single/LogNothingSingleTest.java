package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.internal.messenger.SingleMessageManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.observers.TestSubscriber;

import static org.mockito.Mockito.verifyZeroInteractions;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class LogNothingSingleTest {

  @Rule
  public SingleRule singleRule = new SingleRule(this.getClass());

  private LogNothingSingle loggableSingle;
  private TestSubscriber subscriber;

  @Mock
  private SingleMessageManager messageManager;

  @Before
  public void setUp() {
    subscriber = new TestSubscriber();
    loggableSingle = new LogNothingSingle(singleRule.joinPoint(), messageManager,
            singleRule.info());
  }

  @Test
  public void shouldNotLogAnything() throws Throwable {
    loggableSingle.get(singleRule.stringType()).subscribe(subscriber);

    subscriber.assertNoErrors();
    subscriber.assertCompleted();
    verifyZeroInteractions(messageManager);
  }
}