package org.duohuo.paper.convert;

import org.duohuo.paper.model.BaseLine;
import org.duohuo.paper.model.result.BaseLineResult;

public final class BaseLineConverter {

    private BaseLineConverter() {

    }

    private static Converter<BaseLineResult, BaseLine> convertEntityToResult = new BaseLineEntityToResultConvert();

    public synchronized static BaseLineResult convertBaseLineToResult(BaseLine baseLine) {
        return convertEntityToResult.convertFromOrigin(baseLine);
    }
}