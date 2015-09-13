package org.wangwei.proxy;

import org.wangwei.Anno.Case;

public interface Hello {
    public void say();

    @Case(id = 0)
    public void talk();
}
