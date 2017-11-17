package com.coho.moki.service;

import com.coho.moki.data.remote.ProductCategoryResponse;

import java.util.List;

/**
 * Created by nguyenthanhtung on 17/11/2017.
 */

public interface CategoryService {

    void getCategoryList(String parentId, ResponseListener<List<ProductCategoryResponse>> listener);
}
