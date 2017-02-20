package com.fernandocejas.frodo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <br>Annotated methods which return rx.Singles will print the following information on
 * android logcat when emitting items.
 * A {@link Scope} option can be passed to choose different logging scopes.<br>
 *
 * <br>OUTPUT EXAMPLE:<br>
 *
 * <br>Frodo => [@Single :: @InClass -> SingleSample :: @Method -> names()]
 * <br>Frodo => [@Single#names -> onSubscribe()]
 * <br>Frodo => [@Single#names -> onSuccess() -> Fernando :: @Time -> 1 ms]
 * <br>Frodo => [@Single#names -> @SubscribeOn -> RxNewThreadScheduler-8 :: @ObserveOn -> main]
 * <br>Frodo => [@Single#names -> onUnsubscribe()]<br>
 *
 * @see <a href="https://github.com/android10/frodo/wiki">Frodo Documentation</a>
 */
@Retention(RUNTIME)
@Target({ METHOD })
public @interface RxLogSingle {
  Scope value() default Scope.EVERYTHING;

  /**
   * Logging scope of the current annotated rx.Single.<br>
   *
   * <li>{@link #EVERYTHING}: Logs stream data, schedulers and rx.Single events. Default.</li>
   * <li>{@link #SCHEDULERS}: Logs schedulers where the annotated rx.Single operates on.</li>
   * <li>{@link #EVENTS}: Logs rx.Single events only.</li>
   * <li>{@link #NOTHING}: Turns off logging for the annotated rx.Single.</li>
   */
  enum Scope { EVERYTHING, SCHEDULERS, EVENTS, NOTHING }
}
