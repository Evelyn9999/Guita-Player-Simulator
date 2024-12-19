import deque.Deque61B;
import deque.LinkedListDeque61B;
import jh61b.utils.Reflection;
import net.sf.saxon.functions.Remove;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LinkedListDeque61BTest {

    @Test
    @Order(1)
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
    }

    @Test
    @Order(2)
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }


    @Test
    @Order(3)
    public void addLastTestBasicWithoutToList() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1).containsExactly("front", "middle", "back");
    }

    @Test
    @Order(4)
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    @Order(5)
    /** In this test, we now have multiple different statements that verify isEmpty works correctly. */
    public void isEmptyTestBasic(){
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.isEmpty()).isFalse();

        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        assertThat(lld1.isEmpty()).isFalse();

        lld1.removeLast();
        lld1.removeLast();
        assertThat(lld1.isEmpty()).isTrue();

        lld1.removeLast(); // Attempt to remove from empty deque (should stay empty)
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    @Order(6)
    /**In this test, we now have multiple different statements that verify size works correctly.*/
    public void sizeTestBasic(){
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.size()).isEqualTo(0);

        // Add elements and verify
        lld1.addLast("front");
        assertThat(lld1.size()).isEqualTo(1);

        lld1.addLast("middle");
        assertThat(lld1.size()).isEqualTo(2);

        lld1.addFirst("back");
        assertThat(lld1.size()).isEqualTo(3);

        // Remove elements and verify
        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(2);

        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(1);

        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    @Order(7)
    /** Edge case: Mix addFirst and addLast with size and isEmpty checks. */
    public void sizeAndisEmptyTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        // Initial state
        assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.size()).isEqualTo(0);

        // Mix addFirst and addLast
        lld1.addFirst(10); // [10]
        lld1.addLast(20);  // [10, 20]
        lld1.addFirst(5);  // [5, 10, 20]
        lld1.addLast(25);  // [5, 10, 20, 25]

        // Verify size and content
        assertThat(lld1.size()).isEqualTo(4);
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld1.toList()).containsExactly(5, 10, 20, 25).inOrder();

        // Remove elements and verify
        lld1.removeFirst(); // [10, 20, 25]
        assertThat(lld1.size()).isEqualTo(3);

        lld1.removeLast(); // [10, 20]
        assertThat(lld1.size()).isEqualTo(2);

        // Verify final state
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld1.toList()).containsExactly(10, 20).inOrder();
    }

    @Test
    @Order(8)
    /**In this test, we now have multiple different statements that verify get works correctly.*/
    public void getTestBasic(){
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.get(-1)).isNull();
        assertThat(lld1.get(0)).isNull();

        // Add elements and verify
        lld1.addLast("front");
        assertThat(lld1.get(0)).isEqualTo("front");
        assertThat(lld1.get(1)).isNull();

        lld1.addLast("middle");
        assertThat(lld1.get(0)).isEqualTo("front");
        assertThat(lld1.get(1)).isEqualTo("middle");
        assertThat(lld1.get(2)).isNull();

        lld1.addFirst("back");
        assertThat(lld1.get(0)).isEqualTo("back");
        assertThat(lld1.get(1)).isEqualTo("front");
        assertThat(lld1.get(2)).isEqualTo("middle");
        assertThat(lld1.get(3)).isNull();
        assertThat(lld1.get(-3)).isNull();
        assertThat(lld1.get(33452)).isNull();

        // Remove elements and verify
        lld1.removeLast();
        assertThat(lld1.get(0)).isEqualTo("back");
        assertThat(lld1.get(1)).isEqualTo("front");
        assertThat(lld1.get(2)).isNull();
        assertThat(lld1.get(3)).isNull();

        lld1.removeFirst();
        assertThat(lld1.get(0)).isEqualTo("front");
        assertThat(lld1.get(1)).isNull();
        assertThat(lld1.get(2)).isNull();

        lld1.removeLast();
        assertThat(lld1.get(0)).isNull();
        assertThat(lld1.get(1)).isNull();
    }

    @Test
    @Order(9)
    /**In this test, we now have multiple different statements that verify get works correctly.*/
    public void getRecursiveTestBasic(){
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.getRecursive(-1)).isNull();
        assertThat(lld1.getRecursive(0)).isNull();

        // Add elements and verify
        lld1.addLast("front");
        assertThat(lld1.getRecursive(0)).isEqualTo("front");
        assertThat(lld1.getRecursive(1)).isNull();

        lld1.addLast("middle");
        assertThat(lld1.getRecursive(0)).isEqualTo("front");
        assertThat(lld1.getRecursive(1)).isEqualTo("middle");
        assertThat(lld1.getRecursive(2)).isNull();

        lld1.addFirst("back");
        assertThat(lld1.getRecursive(0)).isEqualTo("back");
        assertThat(lld1.getRecursive(1)).isEqualTo("front");
        assertThat(lld1.getRecursive(2)).isEqualTo("middle");
        assertThat(lld1.getRecursive(3)).isNull();
        assertThat(lld1.getRecursive(-3)).isNull();
        assertThat(lld1.getRecursive(33452)).isNull();

        // Remove elements and verify
        lld1.removeLast();
        assertThat(lld1.getRecursive(0)).isEqualTo("back");
        assertThat(lld1.getRecursive(1)).isEqualTo("front");
        assertThat(lld1.getRecursive(2)).isNull();
        assertThat(lld1.getRecursive(3)).isNull();

        lld1.removeFirst();
        assertThat(lld1.getRecursive(0)).isEqualTo("front");
        assertThat(lld1.getRecursive(1)).isNull();
        assertThat(lld1.getRecursive(2)).isNull();

        lld1.removeLast();
        assertThat(lld1.getRecursive(0)).isNull();
        assertThat(lld1.getRecursive(1)).isNull();
    }

    @Test
    @Order(10)
    /**In this test, we now have multiple different statements that verify removeFirst works correctly.*/
    public void removeFirstAndremoveLast(){
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.removeFirst()).isNull();
        assertThat(lld1.removeLast()).isNull();

        // Mix addFirst and addLast
        lld1.addFirst(10); // [10]
        lld1.addLast(20);  // [10, 20]
        lld1.addFirst(5);  // [5, 10, 20]
        lld1.addLast(25);  // [5, 10, 20, 25]

        assertThat(lld1.removeFirst()).isEqualTo(5);
        assertThat(lld1.toList()).containsExactly(10, 20, 25).inOrder();

        assertThat(lld1.removeLast()).isEqualTo(25);
        assertThat(lld1.toList()).containsExactly(10, 20).inOrder();

        assertThat(lld1.removeFirst()).isEqualTo(10);
        assertThat(lld1.removeLast()).isEqualTo(20);
        assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.removeFirst()).isNull();
        assertThat(lld1.removeLast()).isNull();
    }

    @Test
    @Order(11)
    /** Integration test for LinkedListDeque61B. */
    public void randomOperationsIntegrationTest() {
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        List<Integer> referenceList = new ArrayList<>(); // Used as a reference for correctness
        Random random = new Random();

        int operations = 1000; // Number of random operations to perform
        for (int i = 0; i < operations; i++) {
            int operation = random.nextInt(6); // Randomly choose between 6 operations

            switch (operation) {
                case 0: // addFirst
                    int addFirstValue = random.nextInt(1000);
                    deque.addFirst(addFirstValue);
                    referenceList.add(0, addFirstValue);
                    assertThat(deque.toList()).containsExactlyElementsIn(referenceList).inOrder();
                    break;

                case 1: // addLast
                    int addLastValue = random.nextInt(1000);
                    deque.addLast(addLastValue);
                    referenceList.add(addLastValue);
                    assertThat(deque.toList()).containsExactlyElementsIn(referenceList).inOrder();
                    break;

                case 2: // removeFirst
                    if (!referenceList.isEmpty()) {
                        Integer removedFirst = deque.removeFirst();
                        Integer referenceFirst = referenceList.remove(0);
                        assertThat(removedFirst).isEqualTo(referenceFirst);
                    } else {
                        assertThat(deque.removeFirst()).isNull();
                    }
                    break;

                case 3: // removeLast
                    if (!referenceList.isEmpty()) {
                        Integer removedLast = deque.removeLast();
                        Integer referenceLast = referenceList.remove(referenceList.size() - 1);
                        assertThat(removedLast).isEqualTo(referenceLast);
                    } else {
                        assertThat(deque.removeLast()).isNull();
                    }
                    break;

                case 4: // get
                    if (!referenceList.isEmpty()) {
                        int index = random.nextInt(referenceList.size());
                        Integer dequeValue = deque.get(index);
                        Integer referenceValue = referenceList.get(index);
                        assertThat(dequeValue).isEqualTo(referenceValue);
                    } else {
                        assertThat(deque.get(random.nextInt(10))).isNull();
                    }
                    break;

                case 5: // getRecursive
                    if (!referenceList.isEmpty()) {
                        int index = random.nextInt(referenceList.size());
                        Integer dequeValueRecursive = deque.getRecursive(index);
                        Integer referenceValue = referenceList.get(index);
                        assertThat(dequeValueRecursive).isEqualTo(referenceValue);
                    } else {
                        assertThat(deque.getRecursive(random.nextInt(10))).isNull();
                    }
                    break;

                default:
                    break;
            }

            // Verify size consistency
            assertThat(deque.size()).isEqualTo(referenceList.size());

            // Verify contents of the deque after each operation
            assertThat(deque.toList()).containsExactlyElementsIn(referenceList).inOrder();
        }
    }

    @Test
    @Order(12)
    public void iteratorTestBasic() {
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        // Collect elements using the iterator
        List<Integer> iteratorResult = new ArrayList<>();
        for (Integer item : deque) {
            iteratorResult.add(item);
        }

        // Verify that the iterator result matches toList
        assertThat(iteratorResult).containsExactlyElementsIn(deque.toList()).inOrder();
    }

    @Test
    @Order(13)
    public void equalTestBasic(){
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

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
        lld2 = new LinkedListDeque61B<>();
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
    @Order(14)
    public void toStringTestbasic(){
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        // Test empty deque. Expect []
        assertThat(lld1.toString()).isEqualTo("[]");

        // Test one-element deque
        lld1.addLast("front");  // expect [front]
        assertThat(lld1.toString()).isEqualTo("[front]");

        // Test multi-elements deque
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toString()).isEqualTo("[front, middle, back]");

        // Test if empty after removing all
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        assertThat(lld1.toString()).isEqualTo("[]");

        // Test mixed types (Since Deque61B is generic)
        Deque61B<Object> mixedDeque = new LinkedListDeque61B<>();
        mixedDeque.addLast("string");
        mixedDeque.addLast(123);  // Integer
        mixedDeque.addLast(45.67);  // Double
        assertThat(mixedDeque.toString()).isEqualTo("[string, 123, 45.67]");

    }

}