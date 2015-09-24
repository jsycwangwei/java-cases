package org.wangwei.proxy;

import org.wangwei.Anno.Case;

public interface Hello {
    public void say();

    @Case
    public void talk();
}
