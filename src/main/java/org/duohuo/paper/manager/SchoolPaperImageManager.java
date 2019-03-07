package org.duohuo.paper.manager;

import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.SchoolPaperImage;
import org.duohuo.paper.repository.SchoolPaperImageRepository;
import org.duohuo.paper.utils.ObjectUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component("schoolPaperImageManager")
public class SchoolPaperImageManager {

    @Resource(name = "schoolPaperImageRepository")
    private SchoolPaperImageRepository schoolPaperImageRepository;

    public List<SchoolPaperImage> findAllByPaperIdList(final List<Long> paperIdList) {
        List<Long> newPaperIdList = paperIdList.stream().distinct().collect(Collectors.toList());
        List<SchoolPaperImage> imageList = schoolPaperImageRepository.findAllByPaperIdIn(newPaperIdList);
        if (!ObjectUtil.ifNotNullCollection(imageList)) {
            throw new NotFoundException("无法通过指定paperId序列找到school image:" + paperIdList);
        }
        return imageList;
    }

    public void save(final byte[] data, final String fileName, final Long paperId) {
        SchoolPaperImage paperImage = new SchoolPaperImage();
        paperImage.setImageName(fileName);
        paperImage.setImageData(data);
        paperImage.setPaperId(paperId);
        schoolPaperImageRepository.save(paperImage);
    }
}
