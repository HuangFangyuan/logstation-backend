package com.hfy.logstation.service.interfaces;

import com.hfy.logstation.dto.ResultDto;
import org.elasticsearch.search.sort.SortOrder;


public interface LogService {

    ResultDto getAll(String index, int from, int size);

    ResultDto getAll(String index, int from, int size, String sortField, SortOrder order);

    ResultDto match(String index, String matchField, Object value, int from, int size);

    ResultDto match(String index, String matchField, Object value, int from, int size, String sortField, SortOrder order);

    ResultDto notMatch(String index, String field, Object value, int from, int size);

    ResultDto notMatch(String index, String field, Object value, int from, int size, String sortField, SortOrder order);

    ResultDto getByCondition(String index, String field, Object fromValue, Object toValue, String operator, int from, int size);

    ResultDto range(String index, String rangField, int from, int size, Object start, Object end);

    ResultDto range(String index, String rangField, int from, int size, Object start, Object end, String sortField, SortOrder order);

    ResultDto matchAndRange(String index, String matchField, Object value, String rangeField, Object start, Object end);

    ResultDto matchAndRangeAndCount(String index, String matchField, Object value, String rangeField, Object start, Object end,String countField);

    ResultDto matchAndRangeAndAvg(String index, String matchField, Object value, String rangeField, Object start, Object end,String avgField);
}
