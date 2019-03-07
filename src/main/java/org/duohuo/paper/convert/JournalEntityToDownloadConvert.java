package org.duohuo.paper.convert;

import org.duohuo.paper.excel.model.download.JournalDownloadModel;
import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.Time;

import java.util.function.Function;

class JournalEntityToDownloadConvert extends Converter<JournalDownloadModel, Journal> {

    JournalEntityToDownloadConvert() {
        super(new JournalConvertFunction());
    }

    static class JournalConvertFunction implements Function<Journal, JournalDownloadModel> {
        @Override
        public JournalDownloadModel apply(Journal journal) {
            JournalDownloadModel journalDownloadModel = new JournalDownloadModel();
            journalDownloadModel.setCategoryName(journal.getCategory().getCategoryName());
            journalDownloadModel.setEissn(journal.getEissn());
            journalDownloadModel.setIssn(journal.getIssn());
            journalDownloadModel.setTitle20(journal.getTitle20());
            journalDownloadModel.setTitle29(journal.getTitle29());
            journalDownloadModel.setFullTitle(journal.getFullTitle());
            Time time = journal.getTime();
            journalDownloadModel.setTime(time.getYear() + "/" + time.getMonth());
            return journalDownloadModel;
        }
    }
}
