package com.huida.learn.saga.journal.mapper;

import com.huida.learn.saga.journal.model.OutBoundJournal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OutBoundJournalMapper {
    int deleteByPrimaryKey(@Param("sysEvtTraceId") String sysEvtTraceId, @Param("txTypeInd") String txTypeInd, @Param("sysTxCode") String sysTxCode);

    int insertSelective(OutBoundJournal record);

    OutBoundJournal selectByPrimaryKey(@Param("sysEvtTraceId") String sysEvtTraceId, @Param("txTypeInd") String txTypeInd, @Param("sysTxCode") String sysTxCode);

    int updateByPrimaryKeySelective(OutBoundJournal record);

}