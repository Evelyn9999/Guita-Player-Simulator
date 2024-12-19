import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.*;

import java.util.Comparator;
import deque.MaxArrayDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    @Test
    public void basicTest() {
        // Test Comparator c which is a StringLength comparator
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    @Test
    public void testDefaultComparator() {
        MaxArrayDeque61B<Object> mad2 = new MaxArrayDeque61B<>();
        mad2.addLast("string");
        mad2.addLast("#");
        mad2.addLast("/");
        mad2.addLast(123);  // Integer
        mad2.addLast(45.67);  // Double
        mad2.addLast("banana");

        // Max is determined by string representation
        assertThat(mad2.max()).isEqualTo("string");
    }

    /*@Test
    public void testIntegerNaturalOrder() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<>(Comparator.naturalOrder());
        mad.addLast(10);
        mad.addLast(20);
        mad.addLast(5);

        assertThat(mad.max()).isEqualTo(20);
        assertThat(mad.max(Comparator.reverseOrder())).isEqualTo(5);
    }*/

    @Test
    public void testEmptyDeque() {
        // Test on an empty deque
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        assertThat(mad.max()).isNull();
    }

    @Test
    public void testSingleElementDeque() {
        // Test with a single element
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addLast("only one");

        // Max should return the only element
        assertThat(mad.max()).isEqualTo("only one");
    }

    @Test
    public void testDifferentComparators() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());

        mad.addLast("abc");
        mad.addLast("defgh");
        mad.addLast("z");

        // Max based on length
        assertThat(mad.max()).isEqualTo("defgh");

        // Max based on lexicographical order
        assertThat(mad.max(Comparator.naturalOrder())).isEqualTo("z");
    }




}
