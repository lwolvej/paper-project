package org.duohuo.paper.model.convert;

import org.duohuo.paper.excel.model.download.IncitesDownloadExcelModel;
import org.duohuo.paper.model.Incites;

import java.util.function.Function;

public class ConvertIncitesToDownload extends Converter<IncitesDownloadExcelModel, Incites> {

    public ConvertIncitesToDownload() {
        super(new IncitesDownloadExcelModelFunction(), new IncitesEntityFunction());
    }

    static class IncitesDownloadExcelModelFunction implements Function<IncitesDownloadExcelModel, Incites> {
        @Override
        public Incites apply(IncitesDownloadExcelModel incitesDownloadExcelModel) {
            return new Incites();
        }
    }

    static class IncitesEntityFunction implements Function<Incites, IncitesDownloadExcelModel> {
        @Override
        public IncitesDownloadExcelModel apply(Incites incites) {
            IncitesDownloadExcelModel excelModel = new IncitesDownloadExcelModel();
            excelModel.setAccessionNumber(incites.getAccessionNumber());
            excelModel.setArticleName(incites.getArticleName());
            excelModel.setAuthors(incites.getAuthors());
            excelModel.setDoi(incites.getDoi());
            excelModel.setJournalExpectCitedTimes(incites.getJournalExpectCitedTimes());
            excelModel.setJournalImpactFactor(incites.getJournalImpactFactor());
            excelModel.setJournalInfluence(incites.getJournalInfluence());
            excelModel.setLink(incites.getLink());
            excelModel.setPage(incites.getPage());
            excelModel.setPeriod(incites.getPeriod());
            excelModel.setPmid(incites.getPmid());
            excelModel.setPublicationDate(incites.getPublicationDate());
            excelModel.setSources(incites.getSources());
            excelModel.setSubjectAreaPercentile(incites.getSubjectAreaPercentile());
            excelModel.setSubjectExpectCitedTimes(incites.getSubjectExpectCitedTimes());
            excelModel.setVolume(incites.getVolume());
            excelModel.setSubjectInfluence(incites.getSubjectInfluence());
            return excelModel;
        }
    }
}
