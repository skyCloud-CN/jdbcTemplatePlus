/**
 * @(#)MessageFormatter.java, 10月 10, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.exceptions;

/**
 * @author yuntian
 */
public class MessageFormatter {

    public MessageFormatter() {
    }

    public static final String arrayFormat(String messagePattern, Object[] argArray) {
        if (argArray == null) {
            return messagePattern;
        }
        StringBuilder sb = new StringBuilder();
        int index = 0;
        boolean prepareReplace = false;
        for (char ch : messagePattern.toCharArray()) {
            switch (ch) {
                case '{':
                    prepareReplace = true;
                    break;
                case '}':
                    if (prepareReplace) {
                        sb.append(argArray[index++]);
                    } else {
                        sb.append(ch);
                    }
                    prepareReplace = false;
                    break;
                default:
                    sb.append(ch);
            }
        }
        return sb.toString();
    }
}