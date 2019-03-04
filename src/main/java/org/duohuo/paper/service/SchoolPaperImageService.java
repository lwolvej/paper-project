package org.duohuo.paper.service;

import org.duohuo.paper.model.SchoolPaperImage;

import java.util.List;

public interface SchoolPaperImageService {

    List<SchoolPaperImage> getAllByPaperId(List<Long> paperId);
}
