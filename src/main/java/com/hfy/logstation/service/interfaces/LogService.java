package com.hfy.logstation.service.interfaces;

import com.hfy.logstation.dto.ResultDto;
import com.hfy.logstation.entity.Hit;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

public interface LogService {
    ResultDto get(String index, int from, int size);

    ResultDto get(String index, String field, String value, int from, int size);

    List<Hit> get(String index, String field, String value, Object start, Object to);

    ResultDto get(String index, int from, int size, String sortField, SortOrder order);

    ResultDto get(String index, String field, String value, int from, int size, String sortField, SortOrder order);

    List<Hit> get(String index, String field, String value, String rangeField, Object start, Object end);
}
