package org.duohuo.paper.service.impl.help;

import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.Time;
import org.duohuo.paper.model.result.JsonResult;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JournalSearchServiceHelper {

    List<Journal> createJournalById(List<Long> journalId);

    JsonResult createJsonResult(Page<Journal> page);

    List<Long> createJournalByCompareTwoTime(String keyWord, List<Integer> categoryList, Time now, Time old);
}
