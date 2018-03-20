package com.hfy.elasticsearch.service.interfaces;

import com.hfy.elasticsearch.dto.AlarmDto;
import com.hfy.elasticsearch.dto.ResultDto;
import com.hfy.elasticsearch.entity.Hit;
import org.elasticsearch.search.SearchHit;

import java.util.Date;
import java.util.List;


/**
 * Created by HuangFangyuan on 2018/2/25.
 */
public interface LogService {

    List<Hit> search(String index, String field, String value, long sTime, long eTime);

    ResultDto getLogs(String index, int from, int size);

    ResultDto getLogsByTime(String index, int from, int size, Date sTime, Date eTime);

    AlarmDto getErrorOrBug(String index);

    List<Hit> parseHits(SearchHit[] hits);

}
