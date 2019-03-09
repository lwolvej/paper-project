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
            result.setAccessionNumber(incites.getAccessionNumber());//2
            result.setArticleName(incites.getArticleName());//5
            result.setAuthors(incites.getAuthors());//7
            result.setCategoryName(incites.getCategory().getCategoryName());
            result.setDoi(incites.getDoi());//3
            result.setJournalExpectCitedTimes(incites.getJournalExpectCitedTimes());//14
            result.setJournalImpactFactor(incites.getJournalImpactFactor());//19
            result.setJournalInfluence(incites.getJournalInfluence());//16
            result.setLink(incites.getLink());//6
            result.setPage(incites.getPage());//11
            result.setPeriod(incites.getPeriod());//10
            result.setPmid(incites.getPmid());//4
            result.setPublicationDate(incites.getPublicationDate());//12
            result.setSources(incites.getSources());//8
            result.setSubjectAreaPercentile(incites.getSubjectAreaPercentile());//18
            result.setSubjectExpectCitedTimes(incites.getSubjectExpectCitedTimes());//15
            result.setVolume(incites.getVolume());//9
            result.setSubjectInfluence(incites.getSubjectInfluence());//17
            result.setIncitesId(incites.getIncitesId());//1
            result.setCitedTimes(incites.getCitedTimes());//13
            return result;
        }
    }
}
