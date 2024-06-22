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
        int index = cell.getRowIndex()+1;
        //电费
        if(9 == cell.getColumnIndex()){
            int cellValue = (int)cell.getRow().getCell(0).getNumericCellValue();
            //这如下房间每度1.2
            if(102 == cellValue ||
                    107 == cellValue ||
                    206 == cellValue ||
                    306 == cellValue ||
                    307 == cellValue ||
                    310 == cellValue ||
                    311 == cellValue){
                cell.setCellFormula(("(H" + index + "-" + "I" + index + ")")+ "*1.2");
            }else {
                //每度一块钱
                cell.setCellFormula("H" + index + "-" + "I" + index);
            }
        }
        //水费
        if(10 == cell.getColumnIndex()){
            cell.setCellFormula((int)cell.getNumericCellValue() + "*C" + index);
        }
        //合计金额
        if(13 == cell.getColumnIndex()){
            cell.setCellFormula("G"+index+ "+" + "J"+index+ "+" + "K"+index+ "+" + "L"+index+ "+" + "M"+index);
        }
    }
}
