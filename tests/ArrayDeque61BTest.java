import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import edu.princeton.cs.algs4.ST;
import jh61b.utils.Reflection;
import org.apache.commons.collections.list.SynchronizedList;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    @Order(1)
    /**This test is to verify addFirst works correctly. */
    public void addFirstTestBasic(){
        Deque61B<String> ald1 = new ArrayDeque61B<>();

        ald1.addFirst("a"); // after this call we expect: ["a"]
        assertThat(ald1.toList()).containsExactly("a").inOrder();

        ald1.addFirst("b"); // after this call we expect: ["b", "a"]
        assertThat(ald1.toList()).containsExactly("b", "a").inOrder();

        ald1.addFirst("c"); // after this call we expect: ["c", "b", "a"]
        assertThat(ald1.toList()).containsExactly("c", "b", "a").inOrder();

        ald1.addFirst("e");
        ald1.addFirst("ee");
        ald1.addFirst("eee");
        ald1.addFirst("eeee");
        ald1.addFirst("eeeee");
        ald1.addFirst("eeeeee");
        assertThat(ald1.size()).isEqualTo(9);
        assertThat(ald1.get(1)).isEqualTo("eeeee");
        ald1.addFirst("eeeeeee");
        assertThat(ald1.size()).isEqualTo(10);

    }

    @Test
    @Order(2)
    /**This test is to verify addlast works correctly. */
    public void addLastTestBasic(){
        Deque61B<String> ald1 = new ArrayDeque61B<>();

        ald1.addLast("d"); // after this call we expect: ["d"]
        assertThat(ald1.toList()).containsExactly("d").inOrder();

        ald1.addLast("e"); // after this call we expect: ["d", "e"]
        assertThat(ald1.toList()).containsExactly("d", "e").inOrder();

        ald1.addLast("f"); // after this call we expect: ["d", "e", "f"]
        assertThat(ald1.toList()).containsExactly("d", "e", "f").inOrder();

        ald1.addLast("g");
        ald1.addLast("gg");
        ald1.addLast("ggg");
        ald1.addLast("gggg");
        ald1.addLast("ggggg");
        ald1.addLast("gggggg");
        ald1.addLast("ggggggg");
        assertThat(ald1.size()).isEqualTo(10);
        assertThat(ald1.get(8)).isEqualTo("gggggg");
    }

    @Test
    @Order(3)
    /**This test is to verify interspersed addFirst and addLast calls. */
    public void addFirstAndaddLastTest(){
        Deque61B<Integer> ald1 = new ArrayDeque61B<>();

        ald1.addLast(0); //[0]
        ald1.addFirst(1); //[1, 0]
        ald1.addFirst(-2); //[-2, 1, 0]
        ald1.addLast(4); //[-2, 1, 0, 4]
        ald1.addFirst(9); //[9, -2, 1, 0, 4]
        assertThat(ald1.toList()).containsExactly(9, -2, 1, 0, 4).inOrder();

        ald1.addFirst(1);
        ald1.addFirst(1);
        ald1.addFirst(1); //[1, 1, 1, 9, -2, 1, 0, 4]
        ald1.addLast(44); //[1, 1, 1, 9, -2, 1, 0, 4, 44]
        assertThat(ald1.size()).isEqualTo(9);

        ald1.addFirst(1);
        ald1.addFirst(1);
        ald1.addFirst(1);
        ald1.addLast(44);
        ald1.addLast(44);
        ald1.addLast(44);
        ald1.addLast(44);
        ald1.addLast(44); //[1, 1, 1, 1, 1, 1, 9, -2, 1, 0, 4, 44, 44, 44, 44, 44, 44]
        assertThat(ald1.toList()).containsExactly(1, 1, 1, 1, 1, 1, 9, -2, 1, 0, 4, 44, 44, 44, 44, 44, 44).inOrder();
        assertThat(ald1.size()).isEqualTo(17);
        assertThat(ald1.get(7)).isEqualTo(-2);
        assertThat(ald1.get(0)).isEqualTo(1);
        assertThat(ald1.get(16)).isEqualTo(44);
        assertThat(ald1.get(18)).isNull();


    }

    @Test
    @Order(4)
    /**This test is to verify get calls. */
    public void getTestBasic(){
        Deque61B<String> ald1 = new ArrayDeque61B<>();
        assertThat(ald1.get(-1)).isNull();
        assertThat(ald1.get(0)).isNull();

        // Add elements and verify
        ald1.addLast("front");
        assertThat(ald1.get(0)).isEqualTo("front");
        assertThat(ald1.get(1)).isNull();

        ald1.addLast("middle");
        assertThat(ald1.get(0)).isEqualTo("front");
        assertThat(ald1.get(1)).isEqualTo("middle");
        assertThat(ald1.get(2)).isNull();

        ald1.addFirst("back");
        assertThat(ald1.get(0)).isEqualTo("back");
        assertThat(ald1.get(1)).isEqualTo("front");
        assertThat(ald1.get(2)).isEqualTo("middle");
        assertThat(ald1.get(3)).isNull();
        assertThat(ald1.get(-3)).isNull();
        assertThat(ald1.get(33452)).isNull();

        ald1.addLast("a");
        ald1.addLast("b");
        ald1.addLast("c");
        assertThat(ald1.get(4)).isEqualTo("b");
        assertThat(ald1.get(6)).isNull();

        // Remove elements and verify
        ald1.removeLast();
        assertThat(ald1.get(0)).isEqualTo("back");
        assertThat(ald1.get(1)).isEqualTo("front");
        assertThat(ald1.get(5)).isNull();
        assertThat(ald1.get(6)).isNull();

        ald1.removeFirst();
        assertThat(ald1.get(0)).isEqualTo("front");
        assertThat(ald1.get(4)).isNull();
        assertThat(ald1.get(5)).isNull();

        ald1.removeLast();
        assertThat(ald1.get(3)).isNull();
        assertThat(ald1.get(4)).isNull();
    }

    @Test
    @Order(5)
    /**This test is to verify isEmpty calls work correctly. */
    public void isEmptyTestBasic(){
        Deque61B<String> ald1 = new ArrayDeque61B<>();
        assertThat(ald1.isEmpty()).isTrue();

        ald1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(ald1.isEmpty()).isFalse();

        ald1.addLast("middle"); // after this call we expect: ["front", "middle"]
        assertThat(ald1.isEmpty()).isFalse();

        ald1.removeLast();
        ald1.removeLast();
        assertThat(ald1.isEmpty()).isTrue();

        ald1.removeLast(); // Attempt to remove from empty deque (should stay empty)
        assertThat(ald1.isEmpty()).isTrue();

    }

    @Test
    @Order(6)
    /**This test is to verify size calls work correctly. */
    public void sizeTestBasic(){
        Deque61B<String> ald1 = new ArrayDeque61B<>();
        assertThat(ald1.size()).isEqualTo(0);

        // Add elements and verify
        ald1.addLast("front");
        assertThat(ald1.size()).isEqualTo(1);

        ald1.addLast("middle");
        assertThat(ald1.size()).isEqualTo(2);

        ald1.addFirst("back");
        assertThat(ald1.size()).isEqualTo(3);

        // Remove elements and verify
        ald1.removeLast();
        assertThat(ald1.size()).isEqualTo(2);

        ald1.removeFirst();
        assertThat(ald1.size()).isEqualTo(1);

        ald1.removeLast();
        assertThat(ald1.size()).isEqualTo(0);

    }

    @Test
    @Order(7)
    /**This test is to verify interspersed removeFirst and removeLast calls work correctly. */
    public void removeFirstAndremoveLastTestBasic(){
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        // Add elements using both addFirst and addLast
        deque.addFirst(10); // [10]
        deque.addLast(20);  // [10, 20]
        deque.addFirst(5);  // [5, 10, 20]
        deque.addLast(25);  // [5, 10, 20, 25]

        // Remove elements and verify
        assertThat(deque.removeFirst()).isEqualTo(5); // [10, 20, 25]
        assertThat(deque.toList()).containsExactly(10, 20, 25).inOrder();
        assertThat(deque.size()).isEqualTo(3);

        assertThat(deque.removeLast()).isEqualTo(25); // [10, 20]
        assertThat(deque.toList()).containsExactly(10, 20).inOrder();
        assertThat(deque.size()).isEqualTo(2);

        assertThat(deque.removeFirst()).isEqualTo(10); // [20]
        assertThat(deque.toList()).containsExactly(20).inOrder();
        assertThat(deque.size()).isEqualTo(1);

        assertThat(deque.removeLast()).isEqualTo(20); // []
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.size()).isEqualTo(0);

        // Removing from an empty deque
        assertThat(deque.removeFirst()).isNull();
        assertThat(deque.removeLast()).isNull();
        assertThat(deque.size()).isEqualTo(0);

    }



    /*@Test
     *//** This test is to verify resizing down works correctly after removing elements *//*
    public void resizingDownTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // Add 32 elements to force the deque to grow to a larger size
        for (int i = 0; i < 33; i++) {
            deque.addLast(i);
        }
        assertThat(deque.size()).isEqualTo(33);
        assertThat(((ArrayDeque61B<Integer>) deque).getLength()).isEqualTo(64); // Ensure the internal array size is doubled from 32 to 64

        // Remove elements to drop the usage below 25%, which should trigger resizing down
        for (int i = 0; i < 24; i++) {
            deque.removeLast();
        }

        // After removing 24 elements, 8 elements remain, usage factor is <= 25%, so array length should resize
        assertThat(deque.size()).isEqualTo(9);
        assertThat(((ArrayDeque61B<Integer>) deque).getLength()).isEqualTo(32); // Array should resize down from 64 to 32
        assertThat(deque.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8).inOrder();

        // Remove 4 more elements to further test resizing down
        for (int i = 0; i < 4; i++) {
            deque.removeFirst();
        }

        // Now 4 elements remain, usage factor is <= 25%, array length should resize again
        assertThat(deque.size()).isEqualTo(5);
        assertThat(((ArrayDeque61B<Integer>) deque).getLength()).isEqualTo(32);
        assertThat(deque.toList()).containsExactly(4, 5, 6, 7, 8).inOrder();

        // Remove all elements to test empty case
        for (int i = 0; i < 5; i++) {
            deque.removeFirst();
        }

        // Ensure the deque is empty and does not resize below the minimum threshold
        assertThat(deque.isEmpty()).isTrue();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(((ArrayDeque61B<Integer>) deque).getLength()).isEqualTo(32);
        assertThat(deque.toList()).isEmpty();
    }*/


    @Test
    @Order(8)
    public void iteratorTestBasic() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("front");
        deque.addLast("middle");
        deque.addLast("back");

        // Collect elements using the iterator
        List<String> iteratorResult = new ArrayList<>();
        for (String item : deque) {
            iteratorResult.add(item);
        }

        // Verify that the iterator result matches toList
        assertThat(iteratorResult).containsExactlyElementsIn(deque.toList()).inOrder();
    }

    @Test
    @Order(9)
    public void equalTestBasic(){
        Deque61B<String> lld1 = new ArrayDeque61B<>();
        Deque61B<String> lld2 = new ArrayDeque61B<>();

        // Test equal deques
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);

        // Test unequal deques (different sizes)
        lld2.addLast("extra");
        assertThat(lld1).isNotEqualTo(lld2);

        // Test unequal deques (same size, different elements)
        lld2 = new ArrayDeque61B<>();
        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("different");
        assertThat(lld1).isNotEqualTo(lld2);

        // Test comparison with null
        assertThat(lld1).isNotEqualTo(null);

        // Test comparison with different type
        assertThat(lld1).isNotEqualTo(new Object());
    }

    @Test
    @Order(10)
    public void toStringTestbasic(){
        Deque61B<String> ald1 = new ArrayDeque61B<>();
        // Test empty deque. Expect []
        assertThat(ald1.toString()).isEqualTo("[]");

        // Test one-element deque
        ald1.addLast("front");  // expect [front]
        assertThat(ald1.toString()).isEqualTo("[front]");

        // Test multi-elements deque
        ald1.addLast("middle");
        ald1.addLast("back");
        assertThat(ald1.toString()).isEqualTo("[front, middle, back]");

        // Test if empty after removing all
        ald1.removeFirst();
        ald1.removeFirst();
        ald1.removeFirst();
        assertThat(ald1.toString()).isEqualTo("[]");

        // Test mixed types (Since Deque61B is generic)
        Deque61B<Object> mixedDeque = new ArrayDeque61B<>();
        mixedDeque.addLast("string");
        mixedDeque.addLast(123);  // Integer
        mixedDeque.addLast(45.67);  // Double
        assertThat(mixedDeque.toString()).isEqualTo("[string, 123, 45.67]");

    }



}

