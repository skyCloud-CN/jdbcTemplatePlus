/**
 * @(#)MetaClassTest.java, 10月 10, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.springjdbc.plus.metaclass;

import com.skycloud.fastdao.core.reflection.MetaClass;
import org.junit.Test;

/**
 * @author yuntian
 */

public class MetaClassTest {
    @Test
    public void metaClassTest(){
        MetaClass metaClass=MetaClass.of(BuilderSon.class);
    }
}