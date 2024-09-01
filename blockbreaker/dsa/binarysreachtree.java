package dsa;

import java.util.*;

class Node {
    int data;
    Node left;
    Node right;

    Node(int val) {
        data = val;
        left = right = null;
    }

    public void insert(Node root, int value) {
        if (value < root.data) {
            if (root.left == null) {
                root.left = new Node(value);
            } else {
                insert(root.left, value);
            }
        } else if (value > root.data) {
            if (root.right == null) {
                root.right = new Node(value);
            } else {
                insert(root.right, value);
            }
        }
    }

    public Node delete(Node root, int value) {
        if (root == null) {
            return null;
        }

        if (value < root.data) {
            root.left = delete(root.left, value);
        } else if (value > root.data) {
            root.right = delete(root.right, value);
        } else {
            // Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            Node successor = findMin(root.right);
            root.data = successor.data;
            root.right = delete(root.right, successor.data);
        }

        return root;
    }

    private Node findMin(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
}

public class binarysreachtree {

    public static void main(String[] args) {
        Node root = new Node(10);
        root.insert(root, 20);
        root.insert(root, 30);
        root.insert(root, 40);
        root.insert(root, 50);

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your choice \n1. Inorder \n2. Postorder \n3. Preorder \n4. Delete Node \n");
        int ch = sc.nextInt();

        switch (ch) {
            case 1:
                System.out.print("Inorder Traversal: ");
                inorder(root);
                System.out.println();
                break;

            case 2:
                System.out.print("Postorder Traversal: ");
                postorder(root);
                System.out.println();
                break;

            case 3:
                System.out.print("Preorder Traversal: ");
                preorder(root);
                System.out.println();
                break;

            case 4:
                System.out.print("Enter value to delete: ");
                int valueToDelete = sc.nextInt();
                root = root.delete(root, valueToDelete);
                System.out.println("Node deleted.");
                inorder(root);
                break;

            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    public static void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.data + " ");
            inorder(root.right);
        }
    }

    public static void postorder(Node root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.data + " ");
        }
    }

    public static void preorder(Node root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorder(root.left);
            preorder(root.right);
        }
    }
}
