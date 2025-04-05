package us.codecraft.webmagic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import us.codecraft.webmagic.recover.DuplicateStorageRemover;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateStorageRemoverTest {

    private DuplicateStorageRemover remover;

    @BeforeEach
    void setUp() {
        remover = new DuplicateStorageRemover("test.db");
        remover.resetDuplicateCheck(null);
    }

    @Test
    void testIsDuplicate_firstCall_returnsFalse() {
        Task task = new DummyTask();
        Request r1 = new Request("http://site1.com");

        int initialCount = remover.getTotalRequestsCount(task);

        assertFalse(remover.isDuplicate(r1, task));
        assertEquals(initialCount + 1, remover.getTotalRequestsCount(task));

        assertTrue(remover.isDuplicate(r1, task));
        assertEquals(initialCount + 1, remover.getTotalRequestsCount(task));
    }

    @Test
    void testIsDuplicate_multipleUrls() {
        Task task = new DummyTask();

        Request r1 = new Request("http://site1.com");
        Request r2 = new Request("http://site2.com");

        int initialCount = remover.getTotalRequestsCount(task);

        assertFalse(remover.isDuplicate(r1, task));
        assertFalse(remover.isDuplicate(r2, task));

        assertTrue(remover.isDuplicate(r1, task));
        assertTrue(remover.isDuplicate(r2, task));

        assertEquals(initialCount + 2, remover.getTotalRequestsCount(task));
    }

    static class DummyTask implements Task {
        @Override
        public String getUUID() {
            return "dummy";
        }

        @Override
        public Site getSite() {
            return null;
        }
    }
}
