package ca.uwo.eng.se2205b.lab4;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * Created by PeakeAndSons on 2017-03-06.
 */
@ParametersAreNonnullByDefault
public class AVLTree<E> implements Tree<E, AVLTree.AVLBinaryNode<E>>{

    private AVLBinaryNode<E> root;
    public Comparator<? super E> comparator;

    public AVLTree() {
        this.root = null;//empty list
        this.comparator = (Comparator<E>)(o1, o2) -> ((Comparable<E>)o1).compareTo(o2);
    }

    @Override
    public int hashCode() {
                return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AVLTree)) {
            return false;
        }
        AVLTree other = (AVLTree) obj;
        if(this.root.size() != other.size()){
            return false;
        }
        return areTreesEqual(this.root, other.root);
//        return super.equals(obj);
    }

    private boolean areTreesEqual(AVLBinaryNode temp, AVLBinaryNode other){
        if(temp == other){
            return true;
        }
        if(temp == null || other == null){
            return false;
        }
        return temp.getElement() == other.getElement() && areTreesEqual(temp.left, other.left) && areTreesEqual(temp.right, other.right);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        toString(this.root, string);
        return string.toString();
    }

    private static <T> void toString(AVLBinaryNode<T> node, StringBuilder string) {
        string.append('{');
        if (node != null) {
            string.append(node.value);
            string.append(", ");
            toString(node.left, string);
            string.append(", ");
            toString(node.right, string);
        }
        string.append('}');
    }

    public AVLTree(@Nullable Comparator<? super E> comparator) {
        if(comparator == null){
            this.comparator = (Comparator<E>)(o1, o2) -> ((Comparable<E>)o1).compareTo(o2);
        }
        else {
            this.comparator = comparator;
        }
        this.root = null;
    }

    @Override
    public Iterator<E> iterator(Traversal how) {
        http://codereview.stackexchange.com/questions/41844/iterator-for-binary-tree-pre-in-and-post-order-iterators
        if( how == Traversal.InOrder){
            return createInOrderIter();

        }
        else if( how == Traversal.PreOrder){
            return createPreOrderIter();
        }
        else if( how == Traversal.PostOrder){
            return createPostOrderIter();
        }
        else{
            throw new UnsupportedOperationException();
        }
    }

    private Iterator<E> createInOrderIter() {
        return new InOrderItr();
    }

    private Iterator<E> createPreOrderIter() {
        return new PreOrderItr();
    }

    private Iterator<E> createPostOrderIter() {
        return new PostOrderItr();
    }

    private class PreOrderItr implements Iterator<E> {
        private final Stack<AVLBinaryNode<E>> stack;

        public PreOrderItr() {
            stack = new Stack<AVLBinaryNode<E>>();
            stack.add(root);
        }

        @Override
        public boolean hasNext(){
            return !stack.isEmpty();
        }
        @Override
        public E next(){
            if (!hasNext()) throw new NoSuchElementException("No more nodes remain to iterate");

            final AVLBinaryNode<E> temp = stack.pop();

            if (temp.left != null) {
                stack.push(temp.left);
            }
            if (temp.right != null) {
                stack.push(temp.right);
            }

            return temp.value;
        }
    }

    private class TreeNodeDataPostOrder {
        AVLBinaryNode<E> treeNode;
        boolean visitedLeftAndRightBranches;

        TreeNodeDataPostOrder(AVLBinaryNode<E> treeNode, Boolean visitedLeftAndRightBranches) {
            this.treeNode = treeNode;
            this.visitedLeftAndRightBranches = visitedLeftAndRightBranches;
        }
    }

    private class PostOrderItr implements Iterator<E> {
        private final Stack<TreeNodeDataPostOrder> stack;
        private PostOrderItr() {
            stack = new Stack<TreeNodeDataPostOrder>();
            stack.add(new TreeNodeDataPostOrder(root, false));
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more nodes remain to iterate");
            }

            while (hasNext()) {
                final TreeNodeDataPostOrder treeNodeData = stack.peek();
                final AVLBinaryNode<E> treeNode = treeNodeData.treeNode;

                if (!treeNodeData.visitedLeftAndRightBranches) {
                    if (treeNode.right != null) {
                        stack.add(new TreeNodeDataPostOrder(treeNode.right, false));
                    }
                    if (treeNode.left != null) {
                        stack.add(new TreeNodeDataPostOrder(treeNode.left, false));
                    }
                    treeNodeData.visitedLeftAndRightBranches = true;
                } else {
                    stack.pop();
                    return treeNode.value;
                }
            }

            throw new AssertionError("A node has not been returned when it should have been.");
        }
    }

    private class TreeNodeDataInOrder {
        AVLBinaryNode<E> treeNode;
        boolean visitedLeftBranch;
        TreeNodeDataInOrder(AVLBinaryNode<E> treeNode, Boolean foo) {
            this.treeNode = treeNode;
            this.visitedLeftBranch = foo;
        }
    }

    private class InOrderItr implements Iterator<E> {
        private final Stack<TreeNodeDataInOrder> stack;

        public InOrderItr() {
            stack = new Stack<TreeNodeDataInOrder>();
            stack.add(new TreeNodeDataInOrder(root, false));
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more nodes remain to iterate");
            }

            while (hasNext()) {
                final TreeNodeDataInOrder treeNodeData = stack.peek();
                final AVLBinaryNode<E> treeNode = treeNodeData.treeNode;

                if (!treeNodeData.visitedLeftBranch) {
                    if (treeNode.left != null) {
                        stack.add(new TreeNodeDataInOrder(treeNode.left, false));
                    }
                    treeNodeData.visitedLeftBranch = true;
                } else {
                    stack.pop();
                    if (treeNode.right != null) {
                        stack.add(new TreeNodeDataInOrder(treeNode.right, false));
                    }
                    return treeNode.value;
                }
            }
            throw new AssertionError("A node has not been returned when it should have been.");
        }

    }

    @Override
    public boolean contains(E element) {
        return checkKeyBool(this.root, element);
    }

    private boolean checkKeyBool(AVLBinaryNode<E> temp, E e) {
        //equal
        if (temp == null) {
            return false;
        }
        int myValue = comparator.compare(temp.getElement(), e);
        //element found
        if (myValue == 0) {
            return true;
        }
        //less then
        else if (myValue < 0) {
            return checkKeyBool(temp.getLeft(), e);
        }
        //greater then
//        else if (myValue > 0) {
        else{
            return checkKeyBool(temp.getRight(), e);
        }
    }

    @Override
    public boolean put(E element) {
        //empty list
        if(contains(element)){
            return false;
        }
        if(this.root == null){              //empty case
            this.root = new AVLBinaryNode<E>(element, null);
            this.root.size ++;
            return true;
        }
        insert(null, this.root, element);
        return true;
    }

    private AVLBinaryNode<E> insert ( AVLBinaryNode<E> parent ,AVLBinaryNode<E> tmp, E e) {
        if(parent != null){
            parent.size++;
            parent.children.add(tmp);
        }
        if(tmp == null){
            AVLBinaryNode<E> curr = new AVLBinaryNode<E>(e , parent);
            selfBalanceAfterInsert(curr);
            return curr;
        }
        if(comparator.compare(tmp.getElement(), e) <0 ){
            tmp.left = insert ( parent = tmp,  tmp.getLeft(),e);
        }
        else {
            tmp.right = insert ( parent = tmp,  tmp.getRight(),e);
        }
        return tmp;
    }

    private AVLBinaryNode selfBalanceAfterInsert(AVLBinaryNode<E> node) {
        if(isBalanced()){//whole tree balanced
            return null;
        }
        if(node.isBalanced()){      //current node balanced look at parent
            if(node.parent != null) {
                return selfBalanceAfterInsert(node.parent);
            }
            return null;
        }
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        //2 step roatation...
        if (leftHeight - rightHeight == -2){
            //  goal:x          start:z         step 1: z          step 2:x
            //     / \                 \                 \               / \
            //    z   y                 y                 x             z   y
            //                         /                   \
            //                        x                     y
            //additional case which is step 1
            if(node.right.left == null){
                rotateLeft(node);
            }
            else{
                node.right = rotateRight(node.right);
            }
        }
        if (leftHeight - rightHeight == 2){
            //  goal:x          start:z         step 1: z          step 2:x
            //     / \               /                 /                 / \
            //    y   z             y                 x                 y   z
            //                       \               /
            //                        x             y
            //additional case which is step 1
            if(node.right.right == null){
                rotateRight(node);
            }
            else{
                node.left = rotateRight(node.left);
            }
        }
        return null;
    }

    private AVLBinaryNode rotateRight(AVLBinaryNode x) {
        AVLBinaryNode curr = x.left;
        x.left = curr.right;
        curr.right = x;

        //curr.size = x.size;
        //recalculate height and size.....
//        x.size = 1 + size(x.left) + size(x.right);
//        x.height = 1 + Math.max(height(x.left), height(x.right));
//        curr.height = 1 + Math.max(height(curr.left), height(curr.right));

        return curr;
    }

    private AVLBinaryNode rotateLeft(AVLBinaryNode x) {
        AVLBinaryNode curr = x.right;
        x.right = curr.left;
        curr.left = x;

        //curr.size = x.size;
        //recalculate size and height
//        x.size = 1 + size(x.left) + size(x.right);
//        x.height = 1 + Math.max(height(x.left), height(x.right));
//        curr.height = 1 + Math.max(height(curr.left), height(curr.right));

        return curr;
    }

    @Override
    public int putAll(SortedSet<? extends E> elements) {
        int count = 0;
        for (E s : elements) {
            put(s);
            count ++;
        }
        return count;
    }

    @Override
    public boolean remove(E element) {
        if(this.root.size == 1){
            if(this.root.value == element){
                this.root = null;
                return true;
            }
            else {
                return false;
            }
        }
        else if(this.root == null){
            return false;
        }
        else {
            this.root = deleteRoot(this.root, element);
            return true;
        }
    }

    private AVLBinaryNode<E> deleteRoot(AVLBinaryNode<E> temp, E e) {
        if(temp == null){
            return null;
        }
        temp.size--;
        int myValue = comparator.compare(temp.getElement(), e);
        //less then
        if( myValue < 0 ){
            temp.left = deleteRoot(temp.left, e);
        }
        else if(myValue > 0){
            temp.right = deleteRoot(temp.right, e);
        }
        //found
        else{
            if(temp.right == null ){
                temp = temp.left;
                if(!isBalanced()){
                    selfBalanceAfterInsert(temp);
                }
            }
            else{
                E newValue = min(temp.right);
                temp.value = newValue;
                temp.right = deleteRoot( temp.right, newValue );   //recursive call.
            }
        }
        return temp;
    }

    private E min(AVLBinaryNode<E> temp) {
        if(temp.left == null) {
            return temp.value ;
        }
        else {
            return min(temp.left);
        }
    }

    @Override
    public void setRoot(@Nullable AVLBinaryNode<E> root) {
        this.root = root;
    }

    @Nullable
    @Override
    public AVLBinaryNode<E> getRoot() {
        return this.root;
    }

    @Override
    public int size() {
        if(this.root == null){
            return 0;
        }
        return this.root.size;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public int height() {
        return getHeight(this.root);
    }

    private int getHeight(AVLBinaryNode<E> temp ){
        if (temp == null) {
            return 0;
        }
        return (1+ Math.max(getHeight(temp.left),getHeight(temp.right)));
    }

    @Override
    public boolean isBalanced() {
        if(this.root == null){
            return true;
        }
        int leftHeight = getHeight(this.root);
        int rightHeight = getHeight(this.root);
        if (Math.abs(leftHeight - rightHeight) <= 1) {
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean isProper() {
        return getIsProper(this.root);
    }

    private boolean getIsProper(AVLBinaryNode<E> temp) {
        if(temp != null) {
            if ( temp.isLeaf() ) {
                return true;
            }
            if(temp.getRight() !=null && temp.getLeft() != null){
                return getIsProper(temp.getLeft()) && getIsProper(temp.getRight());
            }
        }
        return false;
    }

    public static class AVLBinaryNode<E> extends BinaryNode<E, AVLBinaryNode<E>> {

        E value;
        private AVLBinaryNode<E> left;
        private AVLBinaryNode<E> right;
        private AVLBinaryNode<E> parent;
        int size;
        Collection<AVLBinaryNode<E>> children =  new ArrayList<>();

        AVLBinaryNode(E elem, @Nullable AVLBinaryNode parent) {
            this.value = elem;
            this.parent = parent;
        }

        @Nullable
        @Override
        AVLBinaryNode<E> getLeft() {
            return this.left;
        }

        @Override
        AVLBinaryNode<E> setLeft(@Nullable AVLBinaryNode<E> left) {
            this.left = left;
            return this.left;
        }

        @Nullable
        @Override
        AVLBinaryNode<E> getRight() {
            return this.right;
        }

        @Override
        AVLBinaryNode<E> setRight(@Nullable AVLBinaryNode<E> right) {
            this.right = right;
            return this.right;
        }

        @Nullable
        @Override
        AVLBinaryNode<E> getParent() {
            return this.parent;
        }

        @Nullable
        @Override
        AVLBinaryNode<E> setParent(@Nullable AVLBinaryNode<E> parent) {
            this.parent = parent;
            return this.parent;
        }

        @Override
        public E getElement() {
            return this.value;
        }

        @Override
        public E setElement(E element) {
            this.value = element;
            return this.value;
        }

        @Override
        public int size() {
            return size;
        }
        @Override
        public boolean isBalanced() {
            return false;
        }

        @Override
        public boolean isProper() {
            return false;
        }

        @Override
        public boolean isInternal() {
            return getLeft() != null || getRight() != null;
        }

        @Override
        public boolean isLeaf() {
            return getLeft() == null && getRight() == null;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }
    }
}
