package org.duohuo.paper.service;

import org.duohuo.paper.model.result.JsonResult;

public interface UserService {

    JsonResult login(String userName, String password, String uuid, String code);

    JsonResult register(String userName, String password, String uuid, String code);
}
