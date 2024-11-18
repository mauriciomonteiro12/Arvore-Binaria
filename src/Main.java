import java.util.Scanner;

public class Main {

    // Interface Position (representa uma posição na árvore)
    public interface Position<E> {
        E element();
    }

    // Interface BinaryTree (representa a estrutura da árvore binária)
    public interface BinaryTree<E> {

        // Retorna o número de nós da árvore
        int size();

        // Retorna se a árvore está vazia
        boolean isEmpty();

        // Retorna a raiz da árvore
        Position<E> root();

        // Retorna o filho esquerdo de uma posição
        Position<E> left(Position<E> v);

        // Retorna o filho direito de uma posição
        Position<E> right(Position<E> v);

        // Verifica se a posição tem filho à esquerda
        boolean hasLeft(Position<E> v);

        // Verifica se a posição tem filho à direita
        boolean hasRight(Position<E> v);

        // Adiciona um nó à árvore
        Position<E> addRoot(E e);

        // Método para adicionar um filho à esquerda ou direita de um nó
        void addChild(Position<E> parent, E value, boolean isLeft);
    }

    // Classe que implementa um nó binário
    public static class BTNode<E> implements Position<E> {
        private E element;
        private BTNode<E> left, right, parent;

        public BTNode(E e) {
            element = e;
            left = right = parent = null;
        }

        public E element() {
            return element;
        }

        public BTNode<E> getLeft() {
            return left;
        }

        public void setLeft(BTNode<E> left) {
            this.left = left;
        }

        public BTNode<E> getRight() {
            return right;
        }

        public void setRight(BTNode<E> right) {
            this.right = right;
        }

        public BTNode<E> getParent() {
            return parent;
        }

        public void setParent(BTNode<E> parent) {
            this.parent = parent;
        }
    }

    // Classe que implementa a árvore binária
    public static class BinaryTreeImpl<E> implements BinaryTree<E> {
        private BTNode<E> root;
        private int size;

        public BinaryTreeImpl() {
            root = null;
            size = 0;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public Position<E> root() {
            return root;
        }

        public Position<E> left(Position<E> v) {
            BTNode<E> node = (BTNode<E>) v;
            return node.getLeft();
        }

        public Position<E> right(Position<E> v) {
            BTNode<E> node = (BTNode<E>) v;
            return node.getRight();
        }

        public boolean hasLeft(Position<E> v) {
            BTNode<E> node = (BTNode<E>) v;
            return node.getLeft() != null;
        }

        public boolean hasRight(Position<E> v) {
            BTNode<E> node = (BTNode<E>) v;
            return node.getRight() != null;
        }

        public Position<E> addRoot(E e) {
            if (root != null) {
                throw new IllegalStateException("Tree already has a root.");
            }
            root = new BTNode<>(e);
            size++;
            return root;
        }

        // Método para adicionar um filho à esquerda ou direita de um nó
        public void addChild(Position<E> parent, E value, boolean isLeft) {
            BTNode<E> parentNode = (BTNode<E>) parent;
            BTNode<E> newChild = new BTNode<>(value);

            if (isLeft) {
                parentNode.setLeft(newChild);
            } else {
                parentNode.setRight(newChild);
            }
            newChild.setParent(parentNode);
            size++;
        }

        // Método recursivo para imprimir a árvore (in-order)
        public void printTree(Position<E> node, int level) {
            if (node == null) return;

            printTree(left(node), level + 1);  // Recursão à esquerda
            System.out.println(" ".repeat(level * 4) + node.element());  // Imprime o valor do nó
            printTree(right(node), level + 1); // Recursão à direita
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();

        // Pergunta ao usuário para inserir a raiz da árvore
        System.out.print("Digite o valor da raiz: ");
        int rootValue = scanner.nextInt();
        Position<Integer> root = tree.addRoot(rootValue);
        System.out.println("Raiz da árvore definida como: " + rootValue);

        // Adicionando filhos à árvore
        boolean addingChildren = true;
        while (addingChildren) {
            System.out.println("\nDeseja adicionar um filho a algum nó?");
            System.out.println("1. Sim");
            System.out.println("2. Não");
            System.out.print("Escolha (1 ou 2): ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                // Pergunta qual nó adicionar um filho
                System.out.print("Digite o valor do nó pai: ");
                int parentValue = scanner.nextInt();
                Position<Integer> parent = findNode(tree, parentValue);

                if (parent != null) {
                    // Pergunta qual filho o usuário quer adicionar
                    System.out.print("Digite o valor do novo nó: ");
                    int childValue = scanner.nextInt();
                    System.out.print("Será filho à esquerda? (1 - Sim, 0 - Não): ");
                    int isLeftChoice = scanner.nextInt();
                    tree.addChild(parent, childValue, isLeftChoice == 1);
                    System.out.println("Nó " + childValue + " adicionado.");
                } else {
                    System.out.println("Nó pai não encontrado!");
                }
            } else {
                addingChildren = false;
            }
        }

        // Exibe a árvore
        System.out.println("\nÁrvore Binária construída:");
        tree.printTree(root, 0); // Imprime a árvore a partir da raiz

        scanner.close();
    }

    // Função para procurar um nó na árvore
    public static Position<Integer> findNode(BinaryTreeImpl<Integer> tree, int value) {
        return findNodeRecursive(tree.root(), tree, value);
    }

    // Função recursiva para procurar o nó desejado
    public static Position<Integer> findNodeRecursive(Position<Integer> node, BinaryTreeImpl<Integer> tree, int value) {
        if (node == null) return null;

        // Verifica se encontrou o nó
        if (node.element() == value) {
            return node;
        }

        // Procura na subárvore à esquerda
        Position<Integer> left = tree.left(node);
        Position<Integer> foundInLeft = findNodeRecursive(left, tree, value);
        if (foundInLeft != null) return foundInLeft;

        // Procura na subárvore à direita
        Position<Integer> right = tree.right(node);
        return findNodeRecursive(right, tree, value);
    }
}
