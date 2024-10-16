package pt.pa.adts;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
public class TreeLinkedTest {

    @Test
    public void testIsEmpty() {
        TreeLinked<String> emptyTree = new TreeLinked<>();
        assertTrue(emptyTree.isEmpty());

        TreeLinked<String> tree = new TreeLinked<>("Ecosystem");
        assertFalse(tree.isEmpty());
    }

    @Test
    public void testIsExternal() {
        TreeLinked<String> tree = new TreeLinked<>("Ecosystem");
        Position<String> ecosystemPos = tree.root();
        Position<String> tunaPos = tree.insert(ecosystemPos, "Tuna");
        Position<String> sardinePos = tree.insert(tunaPos, "Sardine");

        assertTrue(tree.isExternal(sardinePos));
        assertFalse(tree.isExternal(ecosystemPos));
    }

    @Test
    public void testIsRoot() {
        TreeLinked<String> tree = new TreeLinked<>("Ecosystem");

        Position<String> ecosystemPos = tree.root();
        assertTrue(tree.isRoot(ecosystemPos));

        Position<String> tunaPos = tree.insert(ecosystemPos, "Tuna");
        Position<String> sardinePos = tree.insert(tunaPos, "Sardine");

        assertFalse(tree.isRoot(sardinePos));
    }


    @Test
    public void testInsertShouldReturnCorrectPosition() {
        TreeLinked<String> tree = new TreeLinked<>("Ecosystem");
        Position<String> rootPos = tree.root();
        Position<String> newPos = tree.insert(rootPos, "NewNode");
        assertEquals("NewNode", newPos.element());
    }

    @Test
    public void testRemoveShouldReturnCorrectPosition() {
        TreeLinked<String> tree = new TreeLinked<>("Ecosystem");
        Position<String> ecosystemPos = tree.root();
        Position<String> tunaPos = tree.insert(ecosystemPos, "Tuna"); // Insere Tuna como filho de Ecosystem
        Position<String> sardinePos = tree.insert(tunaPos, "Sardine"); // Insere Sardine como filho de Tuna

        // Remover Sardine e verificar se o elemento retornado é o correto
        String removedElement = tree.remove(sardinePos);
        assertEquals("Sardine", removedElement);

        // Verificar se o grau de Tuna diminuiu após a remoção
        assertEquals(0, tree.degree(tunaPos)); // Tuna não deve ter mais filhos após a remoção de Sardine
    }


    @Test
    public void testInsertThrowsInvalidPositionException() {
        TreeLinked<String> tree = new TreeLinked<>("Ecosystem");
        assertThrows(InvalidPositionException.class, () -> tree.insert(null, "NewNode"));
    }

    @Test
    public void testDegree() {
        TreeLinked<String> tree = new TreeLinked<>("Ecosystem");
        Position<String> ecosystemPos = tree.root();
        Position<String> tunaPos = tree.insert(ecosystemPos, "Tuna");
        Position<String> sardinePos = tree.insert(tunaPos, "Sardine");

        assertEquals(1, tree.degree(tunaPos)); // Tuna tem 1 filho (Sardine)
        assertEquals(0, tree.degree(sardinePos)); // Sardine não tem filhos
        assertEquals(1, tree.degree(ecosystemPos)); // Ecosystem tem 1 filho (Tuna)
    }

    @Test
    public void testDegreeThrowsInvalidPositionException() {
        TreeLinked<String> tree = new TreeLinked<>("Ecosystem");
        assertThrows(InvalidPositionException.class, () -> tree.degree(null)); // Testa exceção com posição inválida
    }

    @Test
    public void testElements() {
        // Teste com árvore não vazia
        TreeLinked<String> tree = new TreeLinked<>("Ecosystem");
        Position<String> ecosystemPos = tree.root();
        Position<String> tunaPos = tree.insert(ecosystemPos, "Tuna");
        Position<String> sharkPos = tree.insert(ecosystemPos, "Shark");
        tree.insert(tunaPos, "Sardine");

        // Espera-se que retorne todos os elementos na árvore
        Iterable<String> elements = tree.elements();
        List<String> elementList = new ArrayList<>();
        for (String element : elements) {
            elementList.add(element);
        }

        // Verificar se contém os elementos corretos
        List<String> expectedElements = Arrays.asList("Ecosystem", "Tuna", "Sardine", "Shark");
        assertEquals(expectedElements, elementList);

        // Teste com árvore vazia
        TreeLinked<String> emptyTree = new TreeLinked<>();
        Iterable<String> emptyElements = emptyTree.elements();
        assertFalse(emptyElements.iterator().hasNext()); // Deve ser vazio
    }


    @Test
    public void testChildren() throws InvalidPositionException {
        TreeLinked<String> tree = new TreeLinked<>("Ecosystem");
        Position<String> ecosystemPos = tree.root();
        Position<String> tunaPos = tree.insert(ecosystemPos, "Tuna");
        Position<String> sharkPos = tree.insert(ecosystemPos, "Shark");
        Position<String> sardinePos = tree.insert(tunaPos, "Sardine");

        // Verificar se retorna os filhos de "Ecosystem"
        Iterable<Position<String>> children = tree.children(ecosystemPos);
        List<String> childElements = new ArrayList<>();
        for (Position<String> child : children) {
            childElements.add(child.element());
        }

        // Verificar se contém "Tuna" e "Shark" como filhos de "Ecosystem"
        List<String> expectedChildren = Arrays.asList("Tuna", "Shark");
        assertEquals(expectedChildren, childElements);

        // Verificar se "Sardine" não tem filhos
        Iterable<Position<String>> sardineChildren = tree.children(sardinePos);
        assertFalse(sardineChildren.iterator().hasNext()); // Deve ser vazio

        // Testar InvalidPositionException ao passar uma posição nula
        assertThrows(InvalidPositionException.class, () -> {
            tree.children(null);
        });
    }
}