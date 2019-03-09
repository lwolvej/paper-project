package org.duohuo.paper.convert;

import org.duohuo.paper.excel.model.IncitesExcelModel;
import org.duohuo.paper.excel.model.download.IncitesDownloadExcelModel;
import org.duohuo.paper.model.Category;
import org.duohuo.paper.model.Incites;
import org.duohuo.paper.model.result.IncitesResult;

public final class IncitesConverter {

    private IncitesConverter() {

    }

    //使用了静态变量的静态方法需要加锁

    private static Converter<Incites, IncitesExcelModel> converterEntityToExcel = new IncitesExcelToEntityConvert();

    private static Converter<IncitesDownloadExcelModel, Incites> converterEntityToDownload = new IncitesEntityToDownloadConvert();

    private static Converter<IncitesResult, Incites> converterEntityToResult = new IncitesEntityToResultConvert();

    public synchronized static Incites convertExcelIncitesToEntity(final IncitesExcelModel model, final Category category) {
        Incites incites = converterEntityToExcel.convertFromOrigin(model);
        incites.setCategory(category);
        return incites;
    }

    public synchronized static IncitesDownloadExcelModel convertIncitesToDownload(final Incites incites, final Double value) {
        IncitesDownloadExcelModel model = converterEntityToDownload.convertFromOrigin(incites);
        model.setValue(value.toString());
        return model;
    }

    public synchronized static IncitesDownloadExcelModel convertIncitesToDownload(final Incites incites) {
        return converterEntityToDownload.convertFromOrigin(incites);
    }

    public synchronized static IncitesResult convertIncitesToResult(final Incites incites, final Double value, final Integer elemNum) {
        IncitesResult result = converterEntityToResult.convertFromOrigin(incites);
        result.setValueType(0);
        result.setValue(value);
        result.setElemNum(elemNum);
        return result;
    }

    public synchronized static IncitesResult convertIncitesToResult(final Incites incites, final Integer valueType, final Integer elemNum) {
        IncitesResult result = converterEntityToResult.convertFromOrigin(incites);
        result.setValueType(valueType);
        result.setElemNum(elemNum);
        return result;
    }
}
