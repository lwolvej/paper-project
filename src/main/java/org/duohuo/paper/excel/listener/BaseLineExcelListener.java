package org.duohuo.paper.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

/**
 * @author lwolvej
 */
public class BaseLineExcelListener extends AnalysisEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseLineExcelListener.class);

    private Vector<Object> vector = new Vector<>();

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        vector.add(o);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        LOGGER.info("成功处理一个Excel.基准线.");
    }

    public Vector<Object> getVector() {
        return vector;
    }

    public void setVector(Vector<Object> vector) {
        this.vector = vector;
    }
}
