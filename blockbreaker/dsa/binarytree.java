package dsa;

import java.util.*;

class Nodee {
    int data;
    Nodee left;
    Nodee right;

    public Nodee(int data) {
        this.data = data;
        left = right = null;
    }

    public void insert(int value) {
        if (left == null) {
            left = new Nodee(value);
        } else if (right == null) {
            right = new Nodee(value);
        } else {
            left.insert(value);
        }
    }
}

public class binarytree {

    public static void main(String[] args) {
        Nodee root = new Nodee(70);
        root.insert(20);
        root.insert(30);
        root.insert(40);
        root.insert(50);

        Scanner sc=new Scanner((System.in));
        System.out.println("Enter your choice \n 1.Inoder \n 2.Postorder \n 3.Preorder \n");
        int ch = sc.nextInt();
        switch(ch) {
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

            default:
                System.out.print("Preorder Traversal: ");
                preorder(root);
                System.out.println();
                break;
        }
    }

    public static void inorder(Nodee root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.data + " ");
            inorder(root.right);
        }
    }

    public static void postorder(Nodee root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.data + " ");
        }
    }

    public static void preorder(Nodee root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorder(root.left);
            preorder(root.right);
        }
    }
}
