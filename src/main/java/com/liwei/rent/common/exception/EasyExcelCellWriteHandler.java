package com.liwei.rent.common.exception;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;


public class EasyExcelCellWriteHandler implements CellWriteHandler {
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList,
                                 Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if(9 == cell.getColumnIndex()){
            int index = cell.getRowIndex()+1;
            cell.setCellFormula("H" + index + "-" + "I" + index);
        }
        if(13 == cell.getColumnIndex()){
            int index = cell.getRowIndex()+1;
            cell.setCellFormula("G"+index+ "+" + "J"+index+ "+" + "K"+index+ "+" + "L"+index+ "+" + "M"+index);
        }
    }
}
