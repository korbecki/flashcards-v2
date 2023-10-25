package com.github.korbeckik.common.utils;

import com.github.korbeckik.common.Constants;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

@UtilityClass
public class CommonUtils {

    public static String getStringOrEmptyIfNull(String str) {
        return StringUtils.hasText(str) ? str : Constants.EMPTY_STRING;
    }
}
