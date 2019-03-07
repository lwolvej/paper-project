package org.duohuo.paper.convert;

import org.duohuo.paper.model.Incites;
import org.duohuo.paper.model.result.IncitesResult;

import java.util.function.Function;

class IncitesEntityToResultConvert extends Converter<IncitesResult, Incites> {

    IncitesEntityToResultConvert() {
        super(new IncitesEntityFunction());
    }

    static class IncitesEntityFunction implements Function<Incites, IncitesResult> {
        @Override
        public IncitesResult apply(Incites incites) {
            IncitesResult result = new IncitesResult();
            result.setAccessionNumber(incites.getAccessionNumber());
            result.setArticleName(incites.getArticleName());
            result.setAuthors(incites.getAuthors());
            result.setCategoryName(incites.getCategory().getCategoryName());
            result.setDoi(incites.getDoi());
            result.setJournalExpectCitedTimes(incites.getJournalExpectCitedTimes());
            result.setJournalImpactFactor(incites.getJournalImpactFactor());
            result.setJournalInfluence(incites.getJournalInfluence());
            result.setLink(incites.getLink());
            result.setPage(incites.getPage());
            result.setPeriod(incites.getPeriod());
            result.setPmid(incites.getPmid());
            result.setPublicationDate(incites.getPublicationDate());
            result.setSources(incites.getSources());
            result.setSubjectAreaPercentile(incites.getSubjectAreaPercentile());
            result.setSubjectExpectCitedTimes(incites.getSubjectExpectCitedTimes());
            result.setVolume(incites.getVolume());
            result.setSubjectInfluence(incites.getSubjectInfluence());
            result.setIncitesId(incites.getIncitesId());
            return result;
        }
    }
}
