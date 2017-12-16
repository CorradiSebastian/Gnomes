package sebastiancorradi.altran;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import sebastiancorradi.altran.interactors.DBInteractor;
import sebastiancorradi.altran.model.Gnome;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    Context mMockContext;

    @Test
    public void createFromCursor() throws Exception {
        DBInteractor interactor = DBInteractor.getInstance(mMockContext);

    }
}