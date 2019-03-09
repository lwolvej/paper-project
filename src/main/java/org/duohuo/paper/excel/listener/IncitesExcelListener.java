package org.duohuo.paper.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.duohuo.paper.excel.model.IncitesExcelModel;
import org.duohuo.paper.utils.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

/**
 * @author lwolvej
 */
public class IncitesExcelListener extends AnalysisEventListener<IncitesExcelModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncitesExcelListener.class);

    private Vector<IncitesExcelModel> vector = new Vector<>();

    @Override
    public void invoke(IncitesExcelModel incitesExcelModel, AnalysisContext analysisContext) {
        if (ValidationUtil.validation(incitesExcelModel)) {
            vector.add(incitesExcelModel);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        LOGGER.info("成功处理一个Excel.被引频次.");
    }

    public Vector<IncitesExcelModel> getVector() {
        return vector;
    }
}
