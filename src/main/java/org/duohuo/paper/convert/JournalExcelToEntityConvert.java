package org.duohuo.paper.convert;

import org.duohuo.paper.excel.model.JournalExcelModel;
import org.duohuo.paper.model.Journal;

import java.util.function.Function;

class JournalExcelToEntityConvert extends Converter<Journal, JournalExcelModel> {

    JournalExcelToEntityConvert() {
        super(new JournalConvertFunction());
    }

    static class JournalConvertFunction implements Function<JournalExcelModel, Journal> {
        @Override
        public Journal apply(JournalExcelModel journalExcelModel) {
            Journal journal = new Journal();
            journal.setFullTitle(journalExcelModel.getFullTitle());
            journal.setTitle29(journalExcelModel.getTitle29());
            journal.setTitle20(journalExcelModel.getTitle20());
            journal.setIssn(journalExcelModel.getIssn());
            journal.setEissn(journalExcelModel.getEissn());
            return journal;
        }
    }
}
