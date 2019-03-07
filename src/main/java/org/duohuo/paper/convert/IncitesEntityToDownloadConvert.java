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
            excelModel.setVolume(incites.getVolume());
            excelModel.setSubjectInfluence(incites.getSubjectInfluence());
            excelModel.setCategoryName(incites.getCategory().getCategoryName());
            excelModel.setPeriod(incites.getPeriod());
            excelModel.setPmid(incites.getPmid());
            excelModel.setPublicationDate(incites.getPublicationDate());
            excelModel.setDoi(incites.getDoi());
            excelModel.setJournalExpectCitedTimes(incites.getJournalExpectCitedTimes());
            excelModel.setJournalImpactFactor(incites.getJournalImpactFactor());
            excelModel.setAccessionNumber(incites.getAccessionNumber());
            excelModel.setArticleName(incites.getArticleName());
            excelModel.setAuthors(incites.getAuthors());
            excelModel.setJournalInfluence(incites.getJournalInfluence());
            excelModel.setLink(incites.getLink());
            excelModel.setPage(incites.getPage());
            excelModel.setSources(incites.getSources());
            excelModel.setSubjectAreaPercentile(incites.getSubjectAreaPercentile());
            excelModel.setSubjectExpectCitedTimes(incites.getSubjectExpectCitedTimes());
            return excelModel;
        }
    }
}
