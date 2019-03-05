package org.duohuo.paper.utils;

import org.duohuo.paper.excel.model.download.IncitesDownloadExcelModel;
import org.duohuo.paper.model.Incites;
import org.duohuo.paper.model.convert.ConvertIncitesToDownload;
import org.duohuo.paper.model.convert.ConvertIncitesToResult;
import org.duohuo.paper.model.convert.Converter;
import org.duohuo.paper.model.result.IncitesResult;

public final class ConvertUtil {

    private ConvertUtil() {

    }

    public static IncitesDownloadExcelModel convertIncitesToDownload(final Incites incites, final Double value) {
        Converter<IncitesDownloadExcelModel, Incites> converter = new ConvertIncitesToDownload();
        IncitesDownloadExcelModel model = converter.convertFromEntity(incites);
        model.setCategoryName(incites.getCategory().getCategoryName());
        model.setValue(value.toString());
        return model;
    }

    public static IncitesDownloadExcelModel convertIncitesToDownload(final Incites incites) {
        Converter<IncitesDownloadExcelModel, Incites> converter = new ConvertIncitesToDownload();
        IncitesDownloadExcelModel model = converter.convertFromEntity(incites);
        model.setCategoryName(incites.getCategory().getCategoryName());
        return model;
    }

    public static IncitesResult convertIncitesToResult(final Incites incites, final Double value) {
        Converter<IncitesResult, Incites> converter = new ConvertIncitesToResult();
        IncitesResult result = converter.convertFromEntity(incites);
        result.setValueExist(true);
        result.setValue(value);
        result.setCategoryName(incites.getCategory().getCategoryName());
        return result;
    }

    public static IncitesResult convertIncitesToResult(final Incites incites) {
        Converter<IncitesResult, Incites> converter = new ConvertIncitesToResult();
        IncitesResult result = converter.convertFromEntity(incites);
        result.setCategoryName(incites.getCategory().getCategoryName());
        result.setValueExist(false);
        return result;
    }
}
