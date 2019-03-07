package org.duohuo.paper.convert;

import org.duohuo.paper.excel.model.PaperExcelModel;
import org.duohuo.paper.excel.model.download.PaperExcelDownloadModel;
import org.duohuo.paper.model.Paper;
import org.duohuo.paper.model.Time;

import java.util.function.Function;

class PaperEntityToDownloadConvert extends Converter<PaperExcelDownloadModel, Paper> {

    PaperEntityToDownloadConvert() {
        super(new PaperConvertFunction());
    }

    static class PaperConvertFunction implements Function<Paper, PaperExcelDownloadModel> {
        @Override
        public PaperExcelDownloadModel apply(Paper paper) {
            PaperExcelDownloadModel excelModel = new PaperExcelDownloadModel();
            excelModel.setAccessionNumber(paper.getAccessionNumber());
            excelModel.setDoi(paper.getDoi());
            excelModel.setPmid(paper.getPmid());
            excelModel.setArticleName(paper.getArticleName());
            excelModel.setSource(paper.getSource());
            excelModel.setResearchField(paper.getResearchField());
            excelModel.setTimeCited(paper.getTimesCited());
            excelModel.setAddress(paper.getAddresses());
            excelModel.setInstitutions(paper.getInstitutions());
            excelModel.setPublicationDate(paper.getPublicationDate());
            Time time = paper.getTime();
            excelModel.setTime(time.getYear() + "/" + time.getMonth());
            return excelModel;
        }
    }
}
