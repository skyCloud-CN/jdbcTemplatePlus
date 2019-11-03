/**
 * @(#)GenUtil.java, 9月 26, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.util;

/**
 * @author yuntian
 */
public class NameUtil {

    public static String mapUnderscoreToCamelCase(String underScore) {
        StringBuilder sb = new StringBuilder();
        if (underScore == null) {
            return null;
        }
        boolean afterUnderscore = false;
        for (char c : underScore.toCharArray()) {
            if (c == '_') {
                afterUnderscore = true;
            } else {
                if (afterUnderscore) {
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(c);
                }
                afterUnderscore = false;
            }
        }
        return sb.toString();
    }

}