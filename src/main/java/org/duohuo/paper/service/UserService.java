package org.duohuo.paper.service;

import org.duohuo.paper.model.result.JsonResult;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    JsonResult login(String userName, String password, String uuid, String code);

    @Transactional
    JsonResult register(String userName, String password, String uuid, String code);
}
