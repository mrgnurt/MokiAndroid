package com.coho.moki.data.constant;

/**
 * Created by trung on 06/10/2017.
 */

public enum ResponseCode {

    OK(1000, "OK"),
    SPAM(9991, "Spam"),
    PRODUCT_NOT_EXIT(9992, "Product is not existed."),
    CODE_VERIFY_INCORRECT(9993, "Code verify is incorrect."),
    NO_DATA_OR_END_LIST_DATA(9994, "No Data or end of list data.");

    public int code;

    public String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
