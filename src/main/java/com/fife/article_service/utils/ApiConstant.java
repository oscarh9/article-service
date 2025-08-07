package com.fife.article_service.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiConstant {

    public static final String API = "/api";
    public static final String V1 = "/v1";
    public static final String ARTICLE = "/article";
    public static final String API_V1_ARTICLE = API + V1 + ARTICLE;
    public static final String ID = "/{id}";
}
