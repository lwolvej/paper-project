package org.duohuo.paper.manager;

import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.Incites;
import org.duohuo.paper.repository.IncitesRepository;
import org.duohuo.paper.utils.ObjectUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("incitesManager")
public class IncitesManager {

    @Resource(name = "incitesRepository")
    private IncitesRepository incitesRepository;

    public Optional<Incites> findByAccessionNumber(final String accessionNumber) {
        return incitesRepository.findByAccessionNumber(accessionNumber);
    }

    public void saveAll(final List<Incites> incitesList) {
        incitesRepository.saveAll(incitesList);
    }

    public List<Integer> createNewIncitesIdList(final List<Integer> incitesIdList) {
        List<Integer> newIncitesIdList = incitesIdList.stream()
                .distinct()
                .flatMap(id -> Stream.of(incitesRepository.existsById(id) ? id : null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (!ObjectUtil.ifNotNullCollection(newIncitesIdList)) {
            throw new NotFoundException("无法通过给定incitesId查找到信息:" + incitesIdList);
        }
        return newIncitesIdList;
    }

    public List<Incites> findAllByIdList(final List<Integer> incitesIdList) {
        return incitesRepository.findAllByIncitesIdIn(incitesIdList);
    }

    @Cacheable(value = "incites_find_page_none", keyGenerator = "redisKeyGenerator")
    public Page<Incites> findAll(final PageRequest request) {
        return incitesRepository.findAll(request);
    }

    @Cacheable(value = "incites_find_page_accessionNumber", keyGenerator = "redisKeyGenerator")
    public Page<Incites> findAllByAccessionNumber(final String accessionNumber, final PageRequest pageRequest) {
        return incitesRepository.findAllByAccessionNumber(accessionNumber, pageRequest);
    }

    @Cacheable(value = "incites_find_page_doi", keyGenerator = "redisKeyGenerator")
    public Page<Incites> findAllByDoi(final String doi, final PageRequest pageRequest) {
        return incitesRepository.findAllByDoi(doi, pageRequest);
    }

    @Cacheable(value = "incites_find_page_keyWord", keyGenerator = "redisKeyGenerator")
    public Page<Incites> findAllByKeyWord(final String keyWord, final PageRequest pageRequest) {
        return incitesRepository.findAllByKeyWord(keyWord, pageRequest);
    }

    @Cacheable(value = "incites_find_page_category_list", keyGenerator = "redisKeyGenerator")
    public Page<Incites> findAllByCategoryList(final List<Integer> categoryList, final PageRequest pageRequest) {
        return incitesRepository.findAllByCategory_CategoryIdIn(categoryList, pageRequest);
    }

    @Cacheable(value = "incites_find_page_accessionNumber_category_list", keyGenerator = "redisKeyGenerator")
    public Page<Incites> findAllByAccessionNumberCategoryList(final String accessionNumber, final List<Integer> categoryList, final PageRequest pageRequest) {
        return incitesRepository.findAllByAccessionNumberAndCategory_CategoryIdIn(accessionNumber, categoryList, pageRequest);
    }

    @Cacheable(value = "incites_find_page_doi_category_list", keyGenerator = "redisKeyGenerator")
    public Page<Incites> findAllByDoiCategoryList(final String doi, final List<Integer> categoryList, final PageRequest pageRequest) {
        return incitesRepository.findAllByDoiAndCategory_CategoryIdIn(doi, categoryList, pageRequest);
    }

    @Cacheable(value = "incites_find_page_keyWord_category_list", keyGenerator = "redisKeyGenerator")
    public Page<Incites> findAllByKeyWordCategoryList(final String keyWord, final List<Integer> categoryList, final PageRequest pageRequest) {
        return incitesRepository.findAllByKeyWordAndCategory_CategoryIdIn(keyWord, categoryList, pageRequest);
    }
}
