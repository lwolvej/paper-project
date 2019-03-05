package org.duohuo.paper.model.convert;

import org.duohuo.paper.model.Incites;
import org.duohuo.paper.model.result.IncitesResult;

import java.util.function.Function;

public class ConvertIncitesToResult extends Converter<IncitesResult, Incites> {

    public ConvertIncitesToResult() {
        super(new IncitesResultFunction(), new IncitesEntityFunction());
    }

    static class IncitesResultFunction implements Function<IncitesResult, Incites> {
        @Override
        public Incites apply(IncitesResult incitesResult) {
            //没有转换的必要
            return new Incites();
        }
    }

    static class IncitesEntityFunction implements Function<Incites, IncitesResult> {
        @Override
        public IncitesResult apply(Incites incites) {
            IncitesResult result = new IncitesResult();
            result.setAccessionNumber(incites.getAccessionNumber());
            result.setArticleName(incites.getArticleName());
            result.setAuthors(incites.getAuthors());
            result.setCategoryName(incites.getArticleName());
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
