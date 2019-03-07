package org.duohuo.paper.convert;

import org.duohuo.paper.excel.model.PaperExcelModel;
import org.duohuo.paper.excel.model.download.PaperExcelDownloadModel;
import org.duohuo.paper.model.*;
import org.duohuo.paper.model.result.PaperResult;

public final class PaperConverter {

    private PaperConverter() {

    }

    private static Converter<Paper, PaperExcelModel> converterExcelToEntity = new PaperExcelToEntityConvert();

    private static Converter<PaperExcelDownloadModel, Paper> converterEntityToDownload = new PaperEntityToDownloadConvert();

    private static Converter<PaperResult, Paper> converterEntityToResult = new PaperEntityToResultConvert();

    public synchronized static Paper convertExcelPaperToEntity(final PaperExcelModel model, final Category category, final Time time, final PaperType paperType) {
        Paper paper = converterExcelToEntity.convertFromOrigin(model);
        paper.setCategory(category);
        paper.setTime(time);
        paper.setPaperType(paperType);
        return paper;
    }

    public synchronized static PaperResult convertEntityPaperToResult(final Paper paper, final Integer value) {
        PaperResult paperResult = converterEntityToResult.convertFromOrigin(paper);
        paperResult.setElemNum(value);
        return paperResult;
    }

    public synchronized static PaperExcelDownloadModel convertEntityToDownload(final Paper paper) {
        return converterEntityToDownload.convertFromOrigin(paper);
    }
}
