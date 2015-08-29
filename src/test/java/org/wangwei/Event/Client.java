package org.wangwei.Event;

import org.junit.Before;
import org.wangwei.event.events.Event;
import org.wangwei.event.handler.ClickEventHandler;
import org.wangwei.event.source.ButtonSource;

/**
 * @author jsycwangwei
 * @since 15/8/29 下午12:36
 */
public class Client {
    private Event currentEvent;
    private ButtonSource buttonSource;

    @Before
    public void initCompnent(){
        buttonSource = new ButtonSource();
        buttonSource.addListener(new ClickEventHandler() {
            public void handleEvent(Event event) {

            }
        });
    }



}
