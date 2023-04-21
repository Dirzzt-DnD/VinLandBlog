package com.warzero.vinlandblog.mapper;

import com.warzero.vinlandblog.domain.vo.ArchiveCountVo;
import com.warzero.vinlandblog.domain.vo.ArchiveVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArchiveMapper{
    List<ArchiveCountVo> selectArchiveCount(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    List <ArchiveVo> selectArchiveList(@Param("start") Integer start, @Param("pageSize") Integer pageSize);
    Long selectArchiveTotalCount();

}
