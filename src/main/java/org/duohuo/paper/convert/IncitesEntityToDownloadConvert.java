package org.duohuo.paper.convert;

import org.duohuo.paper.excel.model.download.IncitesDownloadExcelModel;
import org.duohuo.paper.model.Incites;

import java.util.function.Function;

class IncitesEntityToDownloadConvert extends Converter<IncitesDownloadExcelModel, Incites> {

    IncitesEntityToDownloadConvert() {
        super(new IncitesEntityFunction());
    }

    static class IncitesEntityFunction implements Function<Incites, IncitesDownloadExcelModel> {
        @Override
        public IncitesDownloadExcelModel apply(Incites incites) {
            IncitesDownloadExcelModel excelModel = new IncitesDownloadExcelModel();
            excelModel.setAccessionNumber(incites.getAccessionNumber());
            excelModel.setDoi(incites.getDoi());
            excelModel.setPmid(incites.getPmid());
            excelModel.setArticleName(incites.getArticleName());
            excelModel.setLink(incites.getLink());
            excelModel.setAuthors(incites.getAuthors());
            excelModel.setSources(incites.getSources());
            excelModel.setCategoryName(incites.getCategory().getCategoryName());
            excelModel.setVolume(incites.getVolume());
            excelModel.setPeriod(incites.getPeriod());
            excelModel.setPage(incites.getPage());
            excelModel.setPublicationDate(incites.getPublicationDate());
            excelModel.setCitedTimes(incites.getCitedTimes());
            excelModel.setJournalExpectCitedTimes(incites.getJournalExpectCitedTimes());
            excelModel.setSubjectExpectCitedTimes(incites.getSubjectExpectCitedTimes());
            excelModel.setJournalInfluence(incites.getJournalInfluence());
            excelModel.setSubjectInfluence(incites.getSubjectInfluence());
            excelModel.setSubjectAreaPercentile(incites.getSubjectAreaPercentile());
            excelModel.setJournalImpactFactor(incites.getJournalImpactFactor());
            return excelModel;
        }
    }
}
