package test;

import Top.Top;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import static org.junit.Assert.*;

/**
 * Created by pavlo on 13.03.17.
 */
public class testTop {

    @Test
    public void testGetTop() throws IOException {
        Assert.assertEquals(3, new Top(new File(System.getProperty("user.dir") + "/file.txt"), 3).getTop().size());
    }
}
