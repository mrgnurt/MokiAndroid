package com.coho.moki.service;

import com.coho.moki.data.remote.SearchProductResponseData;

import java.util.List;

/**
 * Created by trung on 06/11/2017.
 */

public interface SearchService {
    void searchProduct(String token, String keyword, String categoryId, String brandId,
                       String productSizeId, String priceMin, String priceMax, String condition,
                       int index, int count, ResponseListener<List<SearchProductResponseData>> listener);
}
