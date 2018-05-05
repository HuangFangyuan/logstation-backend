package com.hfy.logstation.service.interfaces;

import com.hfy.logstation.dto.LogDto;
import com.hfy.logstation.entity.QueryBean;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;


public interface LogService {

    //日志模块接口

    LogDto getAll(String index, int from, int size);

    LogDto getAll(String index, int from, int size, String sortField, SortOrder order);

    LogDto getByCondition(String index, List<QueryBean> queryList, int from, int size);

    LogDto range(String index, String rangField, int from, int size, Object start, Object end);

    LogDto range(String index, String rangField, int from, int size, Object start, Object end, String sortField, SortOrder order);

    LogDto matchAndRange(String index, String matchField, Object value, String rangeField, Object start, Object end);

    long matchAndRangeAndCount(String index, String matchField, Object value, String rangeField, Object start, Object end,String countField);

    Double matchAndRangeAndAvg(String index, String matchField, Object value, String rangeField, Object start, Object end,String avgField);

    //暂时没用到
    LogDto match(String index, String matchField, Object value, int from, int size);

    LogDto match(String index, String matchField, Object value, int from, int size, String sortField, SortOrder order);

    LogDto notMatch(String index, String field, Object value, int from, int size);

    LogDto notMatch(String index, String field, Object value, int from, int size, String sortField, SortOrder order);
}
