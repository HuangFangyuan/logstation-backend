package com.hfy.logstation.constant;

import org.elasticsearch.search.sort.SortOrder;

public class Constants {

    public static final String DEFAULT_RANGE_FIELD = Fields.EVENT_TIME;

    public static final String DEFAULT_SORT_FIELD = Fields.EVENT_TIME;

    public static final SortOrder DEFAULT_SORT_ORDER = SortOrder.DESC;

    public static final int DEFAULT_FROM = 0;

    public static final int DEFAULT_SIZE = 10;
}
