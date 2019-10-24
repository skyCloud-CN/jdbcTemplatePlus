package io.github.skycloud.fastdao.core.plugins.logicdelete;

import com.google.common.collect.Lists;
import io.github.skycloud.fastdao.core.plugins.Pluggable;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;
import io.github.skycloud.fastdao.core.plugins.Plugin;

import java.util.List;

public class LogicDeletePlugin implements Plugin{
    @Override
    public List<PluggableHandler> getHandlers() {
        return Lists.newArrayList(new LogicDeleteHandler());
    }
}
