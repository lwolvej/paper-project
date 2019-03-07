package org.duohuo.paper.convert;

import org.duohuo.paper.excel.model.PaperExcelModel;
import org.duohuo.paper.model.Paper;

import java.util.function.Function;

class PaperExcelToEntityConvert extends Converter<Paper, PaperExcelModel> {

    PaperExcelToEntityConvert() {
        super(new PaperConvertFunction());
    }

    static class PaperConvertFunction implements Function<PaperExcelModel, Paper> {
        @Override
        public Paper apply(PaperExcelModel paperExcelModel) {
            Paper paper = new Paper();
            paper.setAccessionNumber(paperExcelModel.getAccessionNumber());
            paper.setAddresses(paperExcelModel.getAddress());
            paper.setAuthors(paperExcelModel.getAuthors());
            paper.setArticleName(paperExcelModel.getArticleName());
            paper.setCountries(paperExcelModel.getCountries());
            paper.setPmid(paperExcelModel.getPmid());
            paper.setSource(paperExcelModel.getSource());
            paper.setDoi(paperExcelModel.getDoi());
            paper.setPublicationDate(paperExcelModel.getPublicationDate());
            paper.setInstitutions(paperExcelModel.getInstitutions());
            paper.setResearchField(paperExcelModel.getResearchField());
            paper.setTimesCited(paperExcelModel.getTimeCited());
            return null;
        }
    }
}
