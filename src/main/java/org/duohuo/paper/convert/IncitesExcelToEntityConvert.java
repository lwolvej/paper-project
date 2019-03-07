package org.duohuo.paper.convert;

import org.duohuo.paper.excel.model.IncitesExcelModel;
import org.duohuo.paper.model.Incites;

import java.util.function.Function;

class IncitesExcelToEntityConvert extends Converter<Incites, IncitesExcelModel> {

    IncitesExcelToEntityConvert() {
        super(new IncitesEntityFunction());
    }

    static class IncitesEntityFunction implements Function<IncitesExcelModel, Incites> {
        @Override
        public Incites apply(IncitesExcelModel incites) {
            Incites result = new Incites();
            result.setAccessionNumber(incites.getAccessionNumber());
            result.setArticleName(incites.getArticleName());
            result.setJournalExpectCitedTimes(incites.getJournalExpectCitedTimes());
            result.setJournalImpactFactor(incites.getJournalImpactFactor());
            result.setPeriod(incites.getPeriod());
            result.setPmid(incites.getPmid());
            result.setPublicationDate(incites.getPublicationDate());
            result.setSources(incites.getSources());
            result.setSubjectInfluence(incites.getSubjectInfluence());
            result.setAuthors(incites.getAuthors());
            result.setDoi(incites.getDoi());
            result.setJournalInfluence(incites.getJournalInfluence());
            result.setLink(incites.getLink());
            result.setPage(incites.getPage());
            result.setSubjectAreaPercentile(incites.getSubjectAreaPercentile());
            result.setSubjectExpectCitedTimes(incites.getSubjectExpectCitedTimes());
            result.setVolume(incites.getVolume());
            return result;
        }
    }
}
