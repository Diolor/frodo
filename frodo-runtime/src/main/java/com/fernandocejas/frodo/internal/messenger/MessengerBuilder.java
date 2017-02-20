package com.fernandocejas.frodo.internal.messenger;

import com.fernandocejas.frodo.internal.single.SingleInfo;

/**
 *
 */
abstract class MessengerBuilder {
  static final String LOG_START = "@";
  static final String SEPARATOR = " :: ";
  static final String METHOD_SEPARATOR = "#";
  static final String VALUE_SEPARATOR = " -> ";
  static final String TEXT_ENCLOSING_SYMBOL = "'";
  static final String LOG_ENCLOSING_OPEN = "[";
  static final String LOG_ENCLOSING_CLOSE = "]";
  static final String LIBRARY_LABEL = "Frodo => ";
  static final String CLASS_LABEL = LOG_START + "InClass" + VALUE_SEPARATOR;
  static final String METHOD_LABEL = LOG_START + "Method" + VALUE_SEPARATOR;
  static final String TIME_LABEL = LOG_START + "Time" + VALUE_SEPARATOR;
  static final String TIME_MILLIS = " ms";
  static final String EMITTED_ELEMENTS_LABEL = LOG_START + "Emitted" + VALUE_SEPARATOR;


}
