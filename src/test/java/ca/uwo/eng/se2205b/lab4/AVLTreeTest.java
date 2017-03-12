package ca.uwo.eng.se2205b.lab4;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

import static ca.uwo.eng.se2205b.lab4.Tree.Traversal.InOrder;
import static ca.uwo.eng.se2205b.lab4.Tree.Traversal.PostOrder;
import static ca.uwo.eng.se2205b.lab4.Tree.Traversal.PreOrder;
import static junit.framework.TestCase.assertEquals;

/**
 * Test an AVL tree
 */
public class AVLTreeTest{

    static AVLTree<Integer> underTest = new AVLTree<>(Integer::compareTo);
    static AVLTree<Integer> defaultTest = new AVLTree<>();

    @Test
    public void removes() throws Exception {
        assertEquals(true, underTest.put(30));
        assertEquals(true, underTest.put(20));
        assertEquals(true, underTest.put(40));
        assertEquals(true, underTest.put(15));
        assertEquals(true, underTest.put(5));
        assertEquals(true, underTest.put(10));
        assertEquals(true, underTest.put(35));
        assertEquals(true, underTest.put(31));
        assertEquals(true, underTest.put(36));
        assertEquals(true, underTest.put(25));
        assertEquals(true, underTest.put(22));
        assertEquals(true, underTest.put(29));
        assertEquals(true, underTest.put(45));
        assertEquals(true, underTest.put(44));
        assertEquals(true, underTest.put(46));
        assertEquals(true, underTest.isBalanced());
        assertEquals(true, underTest.remove(40));
        assertEquals(true, underTest.remove(45));
        assertEquals(true, underTest.remove(35));
        assertEquals(true, underTest.remove(44));
        assertEquals(true, underTest.remove(46));
        assertEquals(true, underTest.remove(31));
        assertEquals(true, underTest.remove(36));
        assertEquals(true, underTest.isBalanced());
        // Check how the AVL tree works with the `remove()` method
    }

    @Test
    public void puts() throws Exception {
        assertEquals(true, underTest.put(20));
        assertEquals(true, underTest.put(10));
        assertEquals(true, underTest.put(30));

        assertEquals(true, underTest.contains(30));
        assertEquals(true, underTest.contains(20));
        assertEquals(true, underTest.contains(10));

        assertEquals(3, underTest.size());
        assertEquals(false, underTest.isEmpty());

        assertEquals(true, defaultTest.put(20));
        assertEquals(true, defaultTest.put(10));
        assertEquals(true, defaultTest.put(30));

        assertEquals(true, defaultTest.contains(30));
        assertEquals(true, defaultTest.contains(20));
        assertEquals(true, defaultTest.contains(10));

        assertEquals(3, defaultTest.size());
        assertEquals(false, defaultTest.isEmpty());
        // Check how the AVL tree works with `put()` values in the tree

    }

    @Test
    public void sizeAndIsEmpty() throws Exception {
        assertEquals(true, underTest.isEmpty());
        assertEquals(0, underTest.size());

        assertEquals(true, underTest.put(20));
        assertEquals(1, underTest.size());
        assertEquals(false, underTest.isEmpty());
        // Check empty tree, after adding and removing elements
    }

    @Test
    public void height() throws Exception {
        assertEquals(0, underTest.height());

        assertEquals(true, underTest.put(20));
        assertEquals(1, underTest.height());
        // check an empty tree and after adding/removing
    }

    @Test
    public void iterator() throws Exception {
        underTest.put(10); //1
        underTest.put(5);//2
        underTest.put(12);//3
        underTest.put(7);//5
        underTest.put(3);//4
        Integer[] arr = {10,5,3,7,12};
        Integer[] arr2 = new Integer[5];
        Iterator<Integer> itr = underTest.iterator(PreOrder);
        int counter = 0;
        while (itr.hasNext()) {
            arr2[counter] = itr.next() ;
            counter ++;
        }
        Assert.assertArrayEquals( arr, arr2 );

        // Check the three different types of iteration
    }

    @Test
    public void iterator2() throws Exception {
        underTest.put(10); //1
        underTest.put(5);//2
        underTest.put(12);//3
        underTest.put(7);//5
        underTest.put(3);//4
        Integer[] arr = {3,5,7,10,12};
        Integer[] arr2 = new Integer[5];
        Iterator<Integer> itr = underTest.iterator(InOrder);
        int counter = 0;
        while (itr.hasNext()) {
            arr2[counter] = itr.next() ;
            counter ++;
        }
        Assert.assertArrayEquals( arr, arr2 );

        // Check the three different types of iteration
    }

    @Test
    public void iterator3() throws Exception {
        underTest.put(10); //1
        underTest.put(5);//2
        underTest.put(12);//3
        underTest.put(7);//5
        underTest.put(3);//4
        Integer[] arr = {3,7,5,12,10};
        Integer[] arr2 = new Integer[5];
        Iterator<Integer> itr = underTest.iterator(PostOrder);
        int counter = 0;
        while (itr.hasNext()) {
            arr2[counter] = itr.next() ;
            counter ++;
        }
        Assert.assertArrayEquals( arr, arr2 );

        // Check the three different types of iteration
    }

    @Test
    public void contains() throws Exception {
        assertEquals(true, underTest.put(20));
        assertEquals(true, underTest.put(10));
        assertEquals(true, underTest.put(30));
        assertEquals(true, underTest.contains(30));
        assertEquals(true, underTest.contains(20));
        assertEquals(true, underTest.contains(10));

        assertEquals(false, underTest.contains(3));
        assertEquals(false, underTest.contains(2));
        assertEquals(false, underTest.contains(1));
        // Actually in the tree, not in..
    }

    @Test
    public void isProper() throws Exception {
        // Check if proper
    }

    @Test
    public void isBalanced1() throws Exception {
        assertEquals(true, underTest.put(20));
        assertEquals(true, underTest.put(30));
        assertEquals(true, underTest.put(25));
        assertEquals(true, underTest.isBalanced());
        // Make sure it's /always/ balanced
    }
    @Test
    public void isBalanced2() throws Exception {
        assertEquals(true, underTest.put(20));
        assertEquals(true, underTest.put(30));
        assertEquals(true, underTest.put(35));
        assertEquals(true, underTest.isBalanced());
        // Make sure it's /always/ balanced
    }
    @Test
    public void isBalanced3() throws Exception {
        assertEquals(true, underTest.put(20));
        assertEquals(true, underTest.put(10));
        assertEquals(true, underTest.put(5));
        assertEquals(true, underTest.isBalanced());
        // Make sure it's /always/ balanced
    }
    @Test
    public void isBalanced4() throws Exception {
        assertEquals(true, underTest.put(20));
        assertEquals(true, underTest.put(10));
        assertEquals(true, underTest.put(15));
        assertEquals(true, underTest.isBalanced());

        // Make sure it's /always/ balanced
    }



}