import org.w3c.dom.css.Counter;

import java.util.ArrayList;
import java.util.HashMap;

class Node {
    int key;
    Node left, right;
    int height;

    public Node(int d) {
        key = d;
        height = 1; // новый узел добавляется как лист
    }
}

class AVLTree {
    private Node root;

    // Получить высоту узла
    private int height(Node N) {
        if (N == null)
            return 0;
        return N.height;
    }

    // Получить баланс узла
    private int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    // Правый поворот
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Выполняем поворот
        x.right = y;
        y.left = T2;

        // Обновляем высоты
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Возвращаем новый корень
        return x;
    }

    // Левый поворот
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Выполняем поворот
        y.left = x;
        x.right = T2;

        // Обновляем высоты
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Возвращаем новый корень
        return y;
    }

    // Вставка узла
    public Node insert(Node node, int key) {
        // 1. Выполняем стандартную вставку BST
        if (node == null)
            return (new Node(key));

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else // Дубликаты не допускаются
            return node;

        // 2. Обновляем высоту узла предка
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // 3. Получаем баланс узла
        int balance = getBalance(node);

        // Если узел стал несбалансированным, выполняем соответствующий поворот

        // Левый левый случай
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Правый правый случай
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Левый правый случай
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Правый левый случай
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        // Возвращаем указатель на узел (не изменился)
        return node;
    }

    // Вспомогательная функция для печати дерева в порядке обхода
    public void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.key + "\t");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public static int Counter(Node node, int height, int c){
        if(node != null){
            if (node.height != height) {
                return (Counter(node.right, height, c) + Counter(node.left, height, c));
            }
            else if(node != null && node.height == height) {
                c += 1;
                return (c);
            }
        }
        return c;
    }
    public static ArrayList<Integer> Usels(Node node, int height, ArrayList<Integer> t){
        if (t.size() == 0){
            t = new ArrayList<>();
        }
        if(node != null){
            if (node.height != height) {
                ArrayList<Integer> t_temp = new ArrayList<>();
                t_temp.addAll(Usels(node.right, height, t));
                t_temp.addAll(Usels(node.left, height, t));
                return (t_temp);
            }
            else if(node != null && node.height == height) {
                t.add(node.key);
                return (t);
            }
        }
        return t;
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.root = tree.insert(tree.root, 4);
        tree.root = tree.insert(tree.root, 2);
        tree.root = tree.insert(tree.root, 6);
        tree.root = tree.insert(tree.root, 1);
        tree.root = tree.insert(tree.root, 3);
        tree.root = tree.insert(tree.root, 5);
        tree.root = tree.insert(tree.root, 7);

        int answer = Counter(tree.root, 2, 0);
        System.out.println("\nCount of usels at " + 2 + " height = " + answer);
        ArrayList<Integer> usels = new ArrayList<>();
        usels = Usels(tree.root, 2, usels);
        System.out.println("Usels at " + 2 + " height: " + usels);
    }
}