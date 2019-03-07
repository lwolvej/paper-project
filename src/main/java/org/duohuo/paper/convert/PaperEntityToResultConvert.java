package org.duohuo.paper.convert;

import org.duohuo.paper.model.Paper;
import org.duohuo.paper.model.result.PaperResult;

import java.util.function.Function;

class PaperEntityToResultConvert extends Converter<PaperResult, Paper> {

    PaperEntityToResultConvert() {
        super(new PaperConvertFunction());
    }

    static class PaperConvertFunction implements Function<Paper, PaperResult> {
        @Override
        public PaperResult apply(Paper paper) {
            PaperResult result = new PaperResult();
            result.setAccessionNumber(paper.getAccessionNumber());
            result.setAddresses(paper.getAddresses());
            result.setYear(paper.getTime().getYear());
            result.setMonth(paper.getTime().getMonth());
            result.setTimesCited(paper.getTimesCited());
            result.setSource(paper.getSource());
            result.setResearchField(paper.getResearchField());
            result.setPublicationDate(paper.getPublicationDate());
            result.setPmid(paper.getPmid());
            result.setPaperId(paper.getPaperId());
            result.setInstitutions(paper.getInstitutions());
            result.setArticleName(paper.getArticleName());
            result.setDoi(paper.getDoi());
            result.setCategoryName(paper.getCategory().getCategoryName());
            result.setCountries(paper.getCountries());
            result.setAuthors(paper.getAuthors());
            return result;
        }
    }
}
