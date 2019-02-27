package org.duohuo.paper.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.duohuo.paper.excel.model.PaperExcelModel;
import org.duohuo.paper.utils.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

/**
 * @author lwolvej
 */
public class PaperExcelListener extends AnalysisEventListener<PaperExcelModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaperExcelListener.class);

    private Vector<PaperExcelModel> vector = new Vector<>(128);

    @Override
    public void invoke(PaperExcelModel paperExcelModel, AnalysisContext analysisContext) {
        if (ValidationUtil.validation(paperExcelModel)) {
            vector.add(paperExcelModel);
        } else {
            LOGGER.info("插入: {} 失败!", paperExcelModel);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        LOGGER.info("成功处理一个Excel!");
    }

    public Vector<PaperExcelModel> getVector() {
        return vector;
    }

    public void setVector(Vector<PaperExcelModel> vector) {
        this.vector = vector;
    }
}
