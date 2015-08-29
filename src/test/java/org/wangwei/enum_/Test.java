package org.wangwei.enum_;

/**
 * @author jsycwangwei
 * @since 15/8/29 上午11:27
 */
public class Test {

    @org.junit.Test
    public void testEnum(){
        switch (Status.valueOf(2)){
            case BAD:
                System.out.println("it's bad");
                break;
            default:
                break;
        }
    }
}
