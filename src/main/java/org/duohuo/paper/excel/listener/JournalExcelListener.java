package org.duohuo.paper.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.duohuo.paper.excel.model.JournalExcelModel;
import org.duohuo.paper.utils.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

/**
 * @author lwolvej
 */
public class JournalExcelListener extends AnalysisEventListener<JournalExcelModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JournalExcelListener.class);

    private Vector<JournalExcelModel> vector = new Vector<>(8192);

    @Override
    public void invoke(JournalExcelModel journalExcelModel, AnalysisContext analysisContext) {
        if (ValidationUtil.validation(journalExcelModel)) {
            vector.add(journalExcelModel);
        } else {
            LOGGER.info("插入: {} 失败!", journalExcelModel);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        LOGGER.info("成功处理一个Excel!");
    }

    public Vector<JournalExcelModel> getVector() {
        return vector;
    }

    public void setVector(Vector<JournalExcelModel> vector) {
        this.vector = vector;
    }
}
