package org.duohuo.paper.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Vector;

/**
 * @author lwolvej
 */
public class BaseLineExcelListener extends AnalysisEventListener {

    private Vector<Object> vector = new Vector<>();

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        vector.add(o);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public Vector<Object> getVector() {
        return vector;
    }

    public void setVector(Vector<Object> vector) {
        this.vector = vector;
    }
}
