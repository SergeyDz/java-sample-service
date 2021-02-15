package com.intapp.releasepromotionsample;

import static org.junit.Assert.assertTrue;

import com.intapp.releasepromotionsample.App;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        String one = "1";
        String two = "2";

        assertTrue(one != two);
    }

    /**
     * Version Test :-)
     */
    @Test
    public void shouldBeNotEmpty()
    {
        App app = new App();
        String version = app.GetVersion();

        assertTrue(!version.isEmpty());
    }
}
