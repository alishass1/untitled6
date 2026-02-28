
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AviaSoulsTest {

    private AviaSouls manager;
    private Ticket ticket1;
    private Ticket ticket2;
    private Ticket ticket3;
    private Ticket ticket4;
    private Ticket ticket5;

    @BeforeEach
    void setUp() {
        manager = new AviaSouls();

        ticket1 = new Ticket("MSK", "SPB", 5000, 1000, 1200);
        ticket2 = new Ticket("MSK", "SPB", 3000, 900, 1100);
        ticket3 = new Ticket("MSK", "SPB", 7000, 1100, 1400);
        ticket4 = new Ticket("MSK", "KZN", 4000, 1200, 1500);
        ticket5 = new Ticket("MSK", "SPB", 4500, 1300, 1500);

        manager.add(ticket1);
        manager.add(ticket2);
        manager.add(ticket3);
        manager.add(ticket4);
        manager.add(ticket5);
    }

    @Test
    void testCompareTo() {
        assertTrue(ticket2.compareTo(ticket1) < 0);
        assertTrue(ticket1.compareTo(ticket2) > 0);

        Ticket ticket6 = new Ticket("MSK", "SPB", 5000, 1400, 1600);
        assertEquals(0, ticket1.compareTo(ticket6));
    }

    @Test
    void testSearchWithSortingByPrice() {
        Ticket[] expected = {ticket2, ticket5, ticket1, ticket3};
        Ticket[] actual = manager.search("MSK", "SPB");
        assertArrayEquals(expected, actual);
    }

    @Test
    void testSearchNoMatches() {
        Ticket[] expected = new Ticket[0];
        Ticket[] actual = manager.search("MSK", "NYC");
        assertArrayEquals(expected, actual);
    }

    @Test
    void testSearchOneMatch() {
        AviaSouls smallManager = new AviaSouls();
        smallManager.add(ticket1);
        smallManager.add(ticket4);

        Ticket[] expected = {ticket1};
        Ticket[] actual = smallManager.search("MSK", "SPB");
        assertArrayEquals(expected, actual);
    }

    @Test
    void testTicketTimeComparator() {
        TicketTimeComparator comparator = new TicketTimeComparator();

        assertEquals(0, comparator.compare(ticket1, ticket2));
        assertTrue(comparator.compare(ticket1, ticket3) < 0);
        assertTrue(comparator.compare(ticket3, ticket1) > 0);
    }

    @Test
    void testSearchAndSortByTime() {
        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket[] expected = {ticket1, ticket2, ticket5, ticket3};
        Ticket[] actual = manager.searchAndSortBy("MSK", "SPB", comparator);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testSearchAndSortByTimeNoMatches() {
        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket[] expected = new Ticket[0];
        Ticket[] actual = manager.searchAndSortBy("MSK", "NYC", comparator);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testSearchAndSortByTimeOneMatch() {
        AviaSouls smallManager = new AviaSouls();
        smallManager.add(ticket1);
        smallManager.add(ticket4);

        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket[] expected = {ticket1};
        Ticket[] actual = smallManager.searchAndSortBy("MSK", "SPB", comparator);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testFindAll() {
        Ticket[] expected = {ticket1, ticket2, ticket3, ticket4, ticket5};
        Ticket[] actual = manager.findAll();
        assertArrayEquals(expected, actual);
    }
}