package org.duohuo.paper.convert;

import org.duohuo.paper.model.BaseLine;
import org.duohuo.paper.model.result.BaseLineResult;

import java.util.function.Function;

class BaseLineEntityToResultConvert extends Converter<BaseLineResult, BaseLine> {

    public BaseLineEntityToResultConvert() {
        super(new BaseLineConvertFunction());
    }

    static class BaseLineConvertFunction implements Function<BaseLine, BaseLineResult> {
        @Override
        public BaseLineResult apply(BaseLine baseLine) {
            BaseLineResult result = new BaseLineResult();
            result.setYear(baseLine.getYear());
            result.setValue(baseLine.getValue());
            result.setPercent(baseLine.getPercent());
            result.setSubject(baseLine.getCategory().getCategoryName());
            return result;
        }
    }
}
