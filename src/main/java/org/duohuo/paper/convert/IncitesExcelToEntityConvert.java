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
            result.setAccessionNumber(incites.getAccessionNumber());//1
            result.setArticleName(incites.getArticleName());//3
            result.setJournalExpectCitedTimes(incites.getJournalExpectCitedTimes());
            result.setJournalImpactFactor(incites.getJournalImpactFactor());
            result.setPeriod(incites.getPeriod());//7
            result.setPmid(incites.getPmid());
            result.setPublicationDate(incites.getPublicationDate());//9
            result.setSources(incites.getSources());//5
            result.setSubjectInfluence(incites.getSubjectInfluence());
            result.setAuthors(incites.getAuthors());
            result.setDoi(incites.getDoi());//2
            result.setJournalInfluence(incites.getJournalInfluence());
            result.setLink(incites.getLink());//4
            result.setPage(incites.getPage());//8
            result.setSubjectAreaPercentile(incites.getSubjectAreaPercentile());
            result.setSubjectExpectCitedTimes(incites.getSubjectExpectCitedTimes());
            result.setVolume(incites.getVolume());//6
            result.setCitedTimes(incites.getCitedTimes());//10
            return result;
        }
    }
}
