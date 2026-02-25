import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class BST {

    private Node root;
    private int size;

    /**
     * Verifica se a árvore é AVL (Árvore Binária de Busca Balanceada).
     * Uma árvore é considerada AVL se, para todos os nós, a diferença de altura
     * entre as subárvores esquerda e direita for no máximo 1.
     * @return true se a árvore for AVL, false caso contrário
     */
    public boolean isAVL() {
        //TODO: implementar
        if(isEmpty()){
            return true;
        }
        return isAVL(this.root);
    }

    /**
     * Verifica se a árvore com raiz no nó especificado é AVL.
     * @param node o nó raiz da árvore/subárvore a ser verificado
     * @return true se a árvore com raiz no nó for AVL, false caso contrário
     */
    private boolean isAVL(Node node){

        if(node == null){
            return true;
        }
        if(!isBalance(node)){
            return false;
        }
        return isAVL(node.left) && isAVL(node.right);

    }

    /**
     * Retorna a altura da árvore.
     */
    public int height() {
        //TODO implementar
        if(isEmpty()) return -1;
        return heightRecursive(this.root);
    }

    /**
     * Calcula recursivamente a altura de uma árvore com raiz no nó especificado.
     * @param node o nó raiz da árvore/subárvore
     * @return a altura da árvore; retorna 0 se o nó for nulo ou folha
     */
    private int heightRecursive(Node node){

        if(node == null){
            return 0;
        }
        if(node.left == null && node.right == null){
            return 0;
        }

        int heightLeft = heightRecursive(node.left);
        int heightRight = heightRecursive(node.right);
        return Math.max(heightLeft,heightRight) + 1;
    }

    /**
     * Calcula o fator de balanceamento de um nó. O fator de balanceamento é a diferença
     * entre as alturas das subárvores esquerda e direita.
     * @param node o nó para o qual calcular o fator de balanceamento
     * @return o fator de balanceamento do nó
     */
    private int balance(Node node) {
        return node.balance();
    }

    /**
     * Verifica se um nó está balanceado. Um nó está balanceado se o módulo do
     * seu fator de balanceamento for menor ou igual a 1.
     * @param node o nó a ser verificado
     * @return true se o nó estiver balanceado, false caso contrário
     */
    public boolean isBalance(Node node){
        int balanced = balance(node);
        return balanced >= -1 && balanced <= 1;
    }

    /**
     * Executa uma rotação à esquerda no nó especificado. A rotação à esquerda
     * é usada para corrigir desbalanceamentos na subárvore direita.
     * @param node o nó a ser rotacionado
     */
    private void rotationLeft(Node node){

        if(node == null){
            return;
        }
        Node newRoot = node.right;
        Node x = newRoot.left;
        newRoot.left = node;

        if(x == null){
            node.right = null;
        } else {
            node.right = x;
            x.parent = node;
        }
        if(node.parent == null){
            this.root = newRoot;
        } else {
            newRoot.parent = node.parent;
            if(node.parent.left == node){
                node.parent.left = newRoot;
            } else {
                node.parent.right = newRoot;
            }
        }
        node.updateHeight();
        newRoot.updateHeight();

        }


    /**
     * Executa uma rotação à direita no nó especificado. A rotação à direita
     * é usada para corrigir desbalanceamentos na subárvore esquerda.
     * @param node o nó a ser rotacionado
     */
    private void rotationRight(Node node){

        if (node == null) {
            return;
        }

        Node newRoot = node.left;
        Node x = newRoot.right;
        newRoot.right = node;
        if(x !=  null){
             x.parent = node;
        }
        node.left = x;

        if(node.parent == null){
            this.root = newRoot;
            newRoot.parent = null;
        } else {
            newRoot.parent = node.parent;
            if(node.parent.left == node){
                node.parent.left = newRoot;
            } else {
                node.parent.right = newRoot;
            }
        }
        node.parent = newRoot;
        node.updateHeight();
        newRoot.updateHeight();
    }


    /**
     * Verifica e retorna o primeiro nó desbalanceado encontrado a partir do nó especificado,
     * subindo pela árvore até a raiz.
     * @param node o nó inicial para verificar o balanceamento
     * @return o primeiro nó desbalanceado encontrado ou null se não houver desbalanceamento
     */
    public Node checkBalance(Node node){
        Node aux = node;
        while(aux != null){
            if(!isBalance(aux)){
                return aux;
            }
            aux = aux.parent;
        }
        return null;
    }

    /**
     * Rebalanceia a árvore executando as rotações necessárias a partir do nó especificado.
     * @param node o nó desbalanceado que precisa ser rebalanceado
     */
    public void rebalance(Node node){
        rotation(node);
    }

    /**
     * Executa a rotação apropriada (simples ou dupla) baseada no tipo de desbalanceamento do nó.
     * @param node o nó desbalanceado para o qual executar a rotação
     */
    private void rotation(Node node){

        if(node.isLeftPending()){
            Node y = node.left;

            if(y.left != null){
                rotationRight(node);
            } else {
                rotationLeft(y);
                rotationRight(node);
            }
        } else {
            Node y = node.right;

            if(y.right != null){
                rotationLeft(node);
            } else {
                rotationRight(y);
                rotationLeft(node);
            }
        }
    }

    /**
     * Busca o nó cujo valor é igual ao passado como parâmetro. Essa é a implementação
     * iterativa clássica da busca binária em uma árvore binária de pesquisa.
     * @param element O elemento a ser procurado.
     * @return O nó contendo o elemento procurado. O método retorna null caso
     * o elemento não esteja presente na árvore.
     */
    public Node search(int element) {

        Node aux = this.root;

        while (aux != null) {
            if (aux.value == element) return aux;
            if (element < aux.value) aux = aux.left;
            if (element > aux.value) aux = aux.right;
        }

        return null;

    }

    /**
     * Verifica se a árvore está vazia.
     * @return true se a árvore estiver vazia (raiz nula), false caso contrário
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * Implementação iterativa da adição de um elemento em uma árvore binária de pequisa.
     * @param element o valor a ser adicionado na árvore.
     */
    public void add(int element) {
        this.size += 1;
        if (isEmpty())
            this.root = new Node(element);
        else {

            Node aux = this.root;

            while (aux != null) {

                if (element < aux.value) {
                    if (aux.left == null) {
                        Node newNode = new Node(element);
                        aux.left = newNode;
                        newNode.parent = aux;

                        //Node balanceNode = checkBalance(newNode);
                        //if(balanceNode != null){
                            ///rebalance(balanceNode);
                        //}
                        return;
                    }

                    aux = aux.left;
                } else {
                    if (aux.right == null) {
                        Node newNode = new Node(element);
                        aux.right = newNode;
                        newNode.parent = aux;
                         //Node balanceNode = checkBalance(newNode);
                        //if(balanceNode != null){
                        //    rebalance(balanceNode);
                       // }
                        return;
                    }

                    aux = aux.right;
                }
            }
        }

    }


    /**
     * Retorna o nó que contém o valor máximo da árvore. Implementação recursiva.
     * @return o nó contendo o valor máximo da árvore ou null se a árvore estiver vazia.
     */
    public Node min() {
        if (isEmpty()) return null;
        return min(this.root);
    }

    /**
     * Retorna o nó que contém o valor máximo da árvore cuja raiz é passada como parâmetro. Implementação recursiva.
     * @param a raiz da árvore.
     * @return o nó contendo o valor máximo da árvore ou null se a árvore estiver vazia.
     */
    private Node min(Node node) {
        if (node.left == null) return node;
        else return min(node.left);
    }

    /**
     * Retorna o nó que contém o valor máximo da árvore. Implementação iterativa.
     * @return o nó contendo o valor máximo da árvore ou null se a árvore estiver vazia.
     */
    public Node max() {
        if (isEmpty()) return null;

        Node node = this.root;
        while(node.right != null)
            node = node.right;

        return node;
    }

    /**
     * Retorna o nó que contém o valor máximo da árvore cuja raiz é passada como parâmetro. Implementação recursiva.
     * @param raiz da árvore.
     * @return o nó contendo o valor máximo da árvore ou null se a árvore estiver vazia.
     */

    private Node max(Node node) {
        if (node.right == null) return node;
        else return max(node.right);
    }

    /**
     * Retorna o nó cujo valor é predecessor do valor passado como parâmetro.
     * @param valor O nó para o qual deseja-se identificar o predecessor.
     * @return O nó contendo o predecessor do valor passado como parâmetro. O método retorna null caso não haja
     * predecessor.
     */
    public Node predecessor(Node node) {
        if (node == null) return null;

        if (node.left != null)
            return max(node.left);
        else {
            Node aux = node.parent;

            while (aux != null && aux.value > node.value)
                aux = aux.parent;

            return aux;
        }
    }

    /**
     * Retorna o nó cujo valor é sucessor do valor passado como parâmetro.
     * @param valor O valor para o qual deseja-se identificar o sucessor.
     * @return O nó contendo o sucessor do valor passado como parâmetro. O método retorna null
     * caso não haja sucessor.
     */
    public Node sucessor(Node node) {
        if (node == null) return null;

        if (node.right != null)
            return min(node.right);
        else {
            Node aux = node.parent;

            while (aux != null && aux.value < node.value)
                aux = aux.parent;

            return aux;
        }
    }


    /**
     * Implementação recursiva do método de adição.
     * @param element elemento a ser adicionado.
     */
    public void recursiveAdd(int element) {

        if (isEmpty())
            this.root = new Node(element);
        else {
            Node aux = this.root;
            recursiveAdd(aux, element);
        }
        this.size += 1;

    }

    /**
     * Método para auxiliar na implementação recursiva do método de adição.
     * @param node a raíz da árvore.
     * @param element elemento a ser adicionado.
     */
    private void recursiveAdd(Node node, int element) {

        if (element < node.value) {
            if (node.left == null) {
                Node newNode = new Node(element);
                node.left = newNode;
                newNode.parent = node;
                return;
            }
            recursiveAdd(node.left, element);
        } else {
            if (node.right == null) {
                Node newNode = new Node(element);
                node.right = newNode;
                newNode.parent = node;
                return;
            }
            recursiveAdd(node.right, element);
        }

    }

    /**
     * Remove the node with the value.
     * @param value
     */
    public void remove(int value) {
        Node toRemove = search(value);
        if (toRemove != null) {
            remove(toRemove);
            this.size -= 1;
        }

    }

    /**
     * Remove node. Private method to control recursion.
     * @param toRemove
     */
    private void remove(Node toRemove) {

        // First case: node is leaf.
        if (toRemove.isLeaf()) {
            if (toRemove == this.root)
                this.root = null;
            else {
                if (toRemove.value < toRemove.parent.value)
                    toRemove.parent.left = null;
                else
                    toRemove.parent.right = null;
            }

        // Second case: node has only left child or only right child
        } else if (toRemove.hasOnlyLeftChild()) {
            if (toRemove == this.root)  {
                this.root = toRemove.left;
                this.root.parent = null;
            } else {
                toRemove.left.parent = toRemove.parent;
                if (toRemove.value < toRemove.parent.value)
                    toRemove.parent.left = toRemove.left;
                else
                    toRemove.parent.right = toRemove.left;
            }
        } else if (toRemove.hasOnlyRightChild()) {
            if (toRemove == this.root) {
                this.root = toRemove.right;
                this.root.parent = null;
            } else {
                toRemove.right.parent = toRemove.parent;
                if (toRemove.value < toRemove.parent.value)
                    toRemove.parent.left = toRemove.right;
                else
                    toRemove.parent.right = toRemove.right;
            }

        // Third case: node has two children
        } else {
            Node sucessor = sucessor(toRemove);
            toRemove.value = sucessor.value;
            remove(sucessor);
        }

    }



    /**
     * Busca o nó cujo valor é igual ao passado como parâmetro. Essa é a implementação
     * recursiva clássica da busca binária em uma árvore binária de pesquisa.
     * @param element O elemento a ser procurado.
     * @return O nó contendo o elemento procurado. O método retorna null caso
     * o elemento não esteja presente na árvore.
     */
    public Node recursiveSearch(int element) {
        return recursiveSearch(this.root, element);
    }

    /**
     * Busca o nó cujo valor é igual ao passado como parâmetro na sub-árvore cuja raiz é node. Trata-se de um método auxiliar
     * para a busca recursiva.
     * @param node a raiz da árvore.
     * @param element O elemento a ser procurado.
     * @return O nó contendo o elemento procurado. O método retorna null caso
     * o elemento não esteja presente na árvore.
     */
    private Node recursiveSearch(Node node, int element) {
        if (node == null) return null;
        if (element == node.value) return node;
        if (element < node.value) return recursiveSearch(node.left, element);
        else return recursiveSearch(node.right, element);
    }

    /**
     * Percorre a árvore em pré-ordem.
     */
    public void preOrder() {
        preOrder(this.root);
    }

    /**
     * Método auxiliar para percorrer a árvore em pré-ordem recursivamente.
     * @param node o nó atual sendo visitado
     */
    private void preOrder(Node node) {
        if (node != null) {
            System.out.println(node.value);
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    /**
     * Percorre a árvore em-ordem.
     */
    public void inOrder() {
        inOrder(this.root);
    }

    /**
     * Método auxiliar para percorrer a árvore em ordem recursivamente.
     * @param node o nó atual sendo visitado
     */
    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.value);
            inOrder(node.right);
        }

    }

    /**
     * Percorre a árvore em pos-ordem.
     */
    public void posOrder() {
        posOrder(this.root);
    }

    /**
     * Método auxiliar para percorrer a árvore em pós-ordem recursivamente.
     * @param node o nó atual sendo visitado
     */
    private void posOrder(Node node) {
        if (node != null) {
            posOrder(node.left);
            posOrder(node.right);
            System.out.println(node.value);
        }

    }

    /**
     * Percorre a árvore em largura.
     * @return Uma lista com a os elementos percorridos em largura.
     */
    public ArrayList<Integer> bfs() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        Deque<Node> queue = new LinkedList<Node>();

        if (!isEmpty()) {
            queue.addLast(this.root);
            while (!queue.isEmpty()) {
                Node current = queue.removeFirst();

                list.add(current.value);

                if(current.left != null)
                    queue.addLast(current.left);
                if(current.right != null)
                    queue.addLast(current.right);
            }
        }
        return list;
    }

    /**
     * @return o tamanho da árvore.
     */
    public int size() {
        return this.size;
    }

}


/**
 * Classe que representa um nó da árvore binária de busca.
 * Cada nó contém um valor, referências para os filhos esquerdo e direito,
 * referência para o pai e informações sobre altura.
 */
class Node {

    int value;
    Node left;
    Node right;
    Node parent;
    int altura;

    /**
     * Construtor do nó.
     * @param v o valor a ser armazenado no nó
     */
    Node(int v) {
        this.value = v;
    }

    /**
     * Retorna o filho esquerdo do nó.
     * @return o nó filho esquerdo
     */
    public Node getLeft() {
        return this.left;
    }

    /**
     * Retorna o filho direito do nó.
     * @return o nó filho direito
     */
    public Node getRight() {
        return this.right;
    }

    /**
     * Retorna o nó pai.
     * @return o nó pai
     */
    public Node getParent() {
        return this.parent;
    }

    /**
     * Retorna a altura armazenada do nó.
     * @return a altura do nó
     */
    public int height() {
        return this.altura;
    }

    /**
     * Calcula recursivamente a altura da subárvore com raiz no nó especificado.
     * @param node o nó raiz da subárvore
     * @return a altura da subárvore
     */
    private int heightRecursive(Node node){
        if(node == null){
            return 0;
        }
        int heightLeft = heightRecursive(node.left);
        int heightRight = heightRecursive(node.right);
        return Math.max(heightLeft, heightRight) + 1;
    }


    /**
     * Verifica se o nó tem um desbalanceamento à esquerda (fator de balanceamento > 1).
     * @return true se estiver pendente para a esquerda, false caso contrário
     */
    public boolean isLeftPending(){
        return balance() > 1;
    }
    /**
     * Verifica se o nó tem um desbalanceamento à direita (fator de balanceamento < -1).
     * @return true se estiver pendente para a direita, false caso contrário
     */
    public boolean isRightPending(){
        return balance() < -1;
    }

    /**
     * Verifica se o nó tem apenas o filho esquerdo.
     * @return true se tiver apenas filho esquerdo, false caso contrário
     */
    public boolean hasOnlyLeftChild() {
        return (this.left != null && this.right == null);
    }

    /**
     * Verifica se o nó tem apenas o filho direito.
     * @return true se tiver apenas filho direito, false caso contrário
     */
    public boolean hasOnlyRightChild() {
        return (this.left == null && this.right != null);
    }

    /**
     * Verifica se o nó é uma folha (não tem filhos).
     * @return true se for uma folha, false caso contrário
     */
    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    /**
     * Calcula o fator de balanceamento do nó. O fator é a diferença entre
     * a altura da subárvore esquerda e a altura da subárvore direita.
     * @return o fator de balanceamento do nó
     */
    public int balance() {
        return (heightRecursive(this.left) - heightRecursive(this.right));
    }

    /**
     * Atualiza a altura armazenada no nó com base nas alturas dos seus filhos.
     */
    public void updateHeight(){
        this.altura = Math.max(heightRecursive(this.left), heightRecursive(this.right)) + 1;
    }

}
