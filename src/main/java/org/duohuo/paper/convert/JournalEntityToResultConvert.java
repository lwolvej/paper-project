package org.duohuo.paper.convert;

import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.result.JournalResult;

import java.util.function.Function;

class JournalEntityToResultConvert extends Converter<JournalResult, Journal> {

    JournalEntityToResultConvert() {
        super(new JournalConvertFunction());
    }

    static class JournalConvertFunction implements Function<Journal, JournalResult> {
        @Override
        public JournalResult apply(Journal journal) {
            JournalResult result = new JournalResult();
            result.setFullTitle(journal.getFullTitle());
            result.setJournalId(journal.getJournalId());
            result.setTitle29(journal.getTitle29());
            result.setTitle20(journal.getTitle20());
            result.setEissn(journal.getEissn());
            result.setIssn(journal.getIssn());
            result.setCategoryName(journal.getCategory().getCategoryName());
            result.setYear(journal.getTime().getYear());
            result.setMonth(journal.getTime().getMonth());
            return result;
        }
    }
}
