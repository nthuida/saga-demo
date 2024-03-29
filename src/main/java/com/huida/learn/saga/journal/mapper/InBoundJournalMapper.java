package com.huida.learn.saga.journal.mapper;

import com.huida.learn.saga.journal.model.InBoundJournal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InBoundJournalMapper {
    int deleteByPrimaryKey(@Param("sysEvtTraceId") String sysEvtTraceId, @Param("txTypeInd") String txTypeInd, @Param("sysTxCode") String sysTxCode);

    int insertSelective(InBoundJournal record);

    InBoundJournal selectByPrimaryKey(@Param("sysEvtTraceId") String sysEvtTraceId, @Param("txTypeInd") String txTypeInd, @Param("sysTxCode") String sysTxCode);

    int updateByPrimaryKeySelective(InBoundJournal record);

}