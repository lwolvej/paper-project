package org.duohuo.paper.service.impl;

import org.duohuo.paper.manager.PaperManager;
import org.duohuo.paper.model.SchoolPaperImage;
import org.duohuo.paper.repository.SchoolPaperImageRepository;
import org.duohuo.paper.service.SchoolPaperImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("schoolPaperImageServiceImpl")
public class SchoolPaperImageServiceImpl implements SchoolPaperImageService {

    @Resource(name = "schoolPaperImageRepository")
    private SchoolPaperImageRepository schoolPaperImageRepository;

    @Resource(name = "paperManager")
    private PaperManager paperManager;

    @Override
    public List<SchoolPaperImage> getAllByPaperId(List<Long> paperId) {
        return schoolPaperImageRepository.findAllByPaperIdIn(
                paperManager.createNewPaperIdList(paperId)
        );
    }
}
