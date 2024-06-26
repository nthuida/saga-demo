package com.huida.learn.saga.reverse.mapper;

import com.huida.learn.saga.reverse.model.ExceptionJournal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExceptionJournalMapper {
    int deleteByPrimaryKey(@Param("sysEvtTraceId") String sysEvtTraceId, @Param("txTypeInd") String txTypeInd, @Param("sysTxCode") String sysTxCode);

    int insertSelective(ExceptionJournal record);

    ExceptionJournal selectByPrimaryKey(@Param("sysEvtTraceId") String sysEvtTraceId, @Param("txTypeInd") String txTypeInd, @Param("sysTxCode") String sysTxCode);

    int updateByPrimaryKeySelective(ExceptionJournal record);

    List<ExceptionJournal> getReverseJournal();

}