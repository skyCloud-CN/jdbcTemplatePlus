/**
 * @(#)Page.java, 10月 25, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.util;

/**
 * @author yuntian
 */
public class Page {

    private int limit;

    private int offset;

    private int total;

    private boolean hasNext;

    public static Page byLimit(int offset, int limit) {
        Page page = new Page();
        page.offset = offset;
        page.limit = limit;
        return page;
    }

    public Page byPage(int pageNumber, int pageSize) {
        Page page = new Page();
        page.limit = pageSize;
        page.offset = (pageNumber - 1) * pageSize;
        return page;
    }

    public int getPageNumber() {
        return offset / limit + 1;
    }

    public int getPageSize() {
        return limit;
    }

    public int getTotal() {
        return total;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public void setTotal(int total) {
        this.total = total;
        if (total <= limit + offset) {
            hasNext = false;
        } else {
            hasNext = true;
        }
    }
}