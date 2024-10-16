package pt.pa;

import pt.pa.adts.Position;
import pt.pa.adts.TreeLinked;

/**
 *
 * @author amfs
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Criar a árvore e definir a raiz
        TreeLinked<String> ecosystemTree = new TreeLinked<>("Ecosystem");

        // Criar as posições dos pais
        Position<String> ecosystemPos = ecosystemTree.root();
        Position<String> tunaPos = ecosystemTree.insert(ecosystemPos, "Tuna");
        Position<String> sharkPos = ecosystemTree.insert(ecosystemPos, "Shark");
        Position<String> eaglesPos = ecosystemTree.insert(ecosystemPos, "Eagles");

        // Adicionar os demais nós
        ecosystemTree.insert(tunaPos, "Mackerel");
        ecosystemTree.insert(tunaPos, "Barracuda");
        ecosystemTree.insert(sharkPos, "Dolphin");
        ecosystemTree.insert(eaglesPos, "Snakes");
        ecosystemTree.insert(eaglesPos, "Rabbits");
        ecosystemTree.insert(tunaPos, "Sardine");

        // Imprimir a árvore
        System.out.println(ecosystemTree.toString());
    }
}