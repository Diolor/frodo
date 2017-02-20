package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.internal.messenger.SingleMessageManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.annotation.Annotation;
import java.util.Collections;

import rx.observers.TestSubscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class FrodoSingleTest {

  @Rule public SingleRule singleRule = new SingleRule(this.getClass());

  private FrodoSingle frodoSingle;
  private TestSubscriber subscriber;

  @Mock private SingleMessageManager messageManager;
  @Mock private LoggableSingleFactory observableFactory;

  @Before
  public void setUp() {
    frodoSingle =
        new FrodoSingle(singleRule.joinPoint(), messageManager, observableFactory);
    subscriber = new TestSubscriber();

    given(observableFactory.create(any(Annotation.class))).willReturn(
        createLogEverythingSingle());
  }

  @Test
  public void shouldPrintSingleInfo() throws Throwable {
    frodoSingle.getSingle();

    verify(messageManager).printSingleInfo(any(SingleInfo.class));
  }

  @Test
  public void shouldBuildSingle() throws Throwable {
    frodoSingle.getSingle().subscribe(subscriber);

    subscriber.assertReceivedOnNext(
        Collections.singletonList(singleRule.OBSERVABLE_STREAM_VALUE));
    subscriber.assertNoErrors();
    subscriber.assertCompleted();
    subscriber.assertUnsubscribed();
  }

  @Test
  public void shouldLogSingleInformation() throws Throwable {
    frodoSingle.getSingle().subscribe(subscriber);

    verify(messageManager).printSingleInfo(any(SingleInfo.class));
  }

  private LogEverythingSingle createLogEverythingSingle() {
    return new LogEverythingSingle(singleRule.joinPoint(), messageManager,
        new SingleInfo(singleRule.joinPoint()));
  }
}