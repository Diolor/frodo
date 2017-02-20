package com.fernandocejas.frodo.internal.single;

import com.fernandocejas.frodo.annotation.RxLogSingle;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class LoggableSingleFactoryTest {

  @Rule
  public final SingleRule singleRule = new SingleRule(this.getClass());

  private LoggableSingleFactory observableFactory;

  @Before
  public void setUp() {
    observableFactory = new LoggableSingleFactory(singleRule.joinPoint(),
            singleRule.messageManager(), singleRule.info());
  }

  @Test
  public void shouldCreateLogEverythingSingle() {
    final RxLogSingle annotation = mock(RxLogSingle.class);
    given(annotation.value()).willReturn(RxLogSingle.Scope.EVERYTHING);

    final LoggableSingle loggableSingle = observableFactory.create(annotation);

    assertThat(loggableSingle).isInstanceOf(LogEverythingSingle.class);
  }

  @Test
  public void shouldCreateLogEventsSingle() {
    final RxLogSingle annotation = mock(RxLogSingle.class);
    given(annotation.value()).willReturn(RxLogSingle.Scope.EVENTS);

    final LoggableSingle loggableSingle = observableFactory.create(annotation);

    assertThat(loggableSingle).isInstanceOf(LogEventsSingle.class);
  }

  @Test
  public void shouldCreateLogSchedulersSingle() {
    final RxLogSingle annotation = mock(RxLogSingle.class);
    given(annotation.value()).willReturn(RxLogSingle.Scope.SCHEDULERS);

    final LoggableSingle loggableSingle = observableFactory.create(annotation);

    assertThat(loggableSingle).isInstanceOf(LogSchedulersSingle.class);
  }

  @Test
  public void shouldCreateLogNothingSingle() {
    final RxLogSingle annotation = mock(RxLogSingle.class);
    given(annotation.value()).willReturn(RxLogSingle.Scope.NOTHING);

    final LoggableSingle loggableSingle = observableFactory.create(annotation);

    assertThat(loggableSingle).isInstanceOf(LogNothingSingle.class);
  }
}