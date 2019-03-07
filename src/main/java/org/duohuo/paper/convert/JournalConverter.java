package org.duohuo.paper.convert;

import org.duohuo.paper.excel.model.JournalExcelModel;
import org.duohuo.paper.excel.model.download.JournalDownloadModel;
import org.duohuo.paper.model.Category;
import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.Time;
import org.duohuo.paper.model.result.JournalResult;

public final class JournalConverter {

    private JournalConverter() {

    }

    private static Converter<Journal, JournalExcelModel> converterExcelToEntity = new JournalExcelToEntityConvert();

    private static Converter<JournalResult, Journal> converterEntityToResult = new JournalEntityToResultConvert();

    private static Converter<JournalDownloadModel, Journal> converterEntityToDownload = new JournalEntityToDownloadConvert();

    public synchronized static Journal convertExcelJournalToEntity(final JournalExcelModel model, final Category category, final Time time) {
        Journal journal = converterExcelToEntity.convertFromOrigin(model);
        journal.setCategory(category);
        journal.setTime(time);
        return journal;
    }

    public synchronized static JournalResult convertEntityJournalToResult(final Journal journal, final Integer value) {
        JournalResult result = converterEntityToResult.convertFromOrigin(journal);
        result.setElemNum(value);
        return result;
    }

    public synchronized static JournalDownloadModel convertEntityJournalToDownload(Journal journal) {
        return converterEntityToDownload.convertFromOrigin(journal);
    }
}
