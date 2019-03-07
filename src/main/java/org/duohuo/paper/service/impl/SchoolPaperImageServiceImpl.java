package org.duohuo.paper.service.impl;

import org.duohuo.paper.manager.PaperManager;
import org.duohuo.paper.manager.SchoolPaperImageManager;
import org.duohuo.paper.model.SchoolPaperImage;
import org.duohuo.paper.service.SchoolPaperImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("schoolPaperImageServiceImpl")
public class SchoolPaperImageServiceImpl implements SchoolPaperImageService {

    @Resource(name = "paperManager")
    private PaperManager paperManager;

    @Resource(name = "schoolPaperImageManager")
    private SchoolPaperImageManager schoolPaperImageManager;

    @Override
    public List<SchoolPaperImage> getAllByPaperId(List<Long> paperId) {
        return schoolPaperImageManager.findAllByPaperIdList(
                paperManager.createNewPaperIdList(paperId)
        );
    }
}
