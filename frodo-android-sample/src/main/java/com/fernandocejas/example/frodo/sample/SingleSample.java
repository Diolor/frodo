package com.fernandocejas.example.frodo.sample;

import android.view.View;

import com.fernandocejas.frodo.annotation.RxLogSingle;

import java.util.Arrays;
import java.util.List;

import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

import static com.fernandocejas.frodo.annotation.RxLogSingle.Scope.EVENTS;
import static com.fernandocejas.frodo.annotation.RxLogSingle.Scope.EVERYTHING;
import static com.fernandocejas.frodo.annotation.RxLogSingle.Scope.NOTHING;
import static com.fernandocejas.frodo.annotation.RxLogSingle.Scope.SCHEDULERS;

public class SingleSample {
  public SingleSample() {
  }

  @RxLogSingle(EVERYTHING)
  public Single<Integer> numbers() {
    return Single.just(1);
  }

  @RxLogSingle
  public Single<String> error() {
    return Single.error(new IllegalArgumentException("My error"));
  }

  @RxLogSingle(SCHEDULERS)
  public Single<List<MyDummyClass>> list() {
    return Single.just(buildDummyList());
  }

  @RxLogSingle(EVENTS)
  public Single<String> stringItemWithDefer() {
    return Single.defer(new Func0<Single<String>>() {
      @Override
      public Single<String> call() {
        return Single.create(new Single.OnSubscribe<String>() {
          @Override
          public void call(SingleSubscriber<? super String> singleSubscriber) {
            try {
              singleSubscriber.onSuccess("String item Three");
            } catch (Exception e) {
              singleSubscriber.onError(e);
            }
          }
        }).subscribeOn(Schedulers.computation());
      }
    });
  }

  /**
   * Nothing should happen here when annotating this method with {@link RxLogSingle}
   * because it does not returns an {@link Single}.
   */
  @RxLogSingle(NOTHING)
  public List<MyDummyClass> buildDummyList() {
    return Arrays.asList(new MyDummyClass("Batman"), new MyDummyClass("Superman"));
  }

  @RxLogSingle
  public Single<Void> doSomething(View view) {
    return Single.just(null);
  }

  @RxLogSingle
  public Single<String> sendNull() {
    return Single.just(null);
  }

  public static final class MyDummyClass {
    private final String name;

    MyDummyClass(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "Name: " + name;
    }
  }
}
