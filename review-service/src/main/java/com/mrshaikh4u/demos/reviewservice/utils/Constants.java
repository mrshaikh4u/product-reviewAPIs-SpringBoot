package com.mrshaikh4u.demos.reviewservice.utils;

public class Constants {
    public static final String MSG_MAPPING_ERR= "Unable to map entity to TO";
    public static final String MSG_INVALID_TOKEN_ERR= "Null or invalid user token";
    public static final String FLD_REVIEW_ID= "reviewID";

    private Constants(){
        throw new IllegalStateException("Utility class");
    }
}
