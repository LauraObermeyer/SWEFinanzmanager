package test;

import org.junit.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class EintraegeDetailansichtAdapterTest {

    public EintraegeDetailansichtAdapterTest () {

    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getEigenschaften() {
        //Arrange
            String[] test = new String[2];
            test[0] = "Eintrag 1";
            test[1] = "Eintrag 2";

        //Act
            int length = test.length;

        //Assert
        assertThat(length, is(2));
    }
}
