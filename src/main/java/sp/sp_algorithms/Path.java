package main.java.sp.sp_algorithms;

import main.java.sp.graph.Edge;
import main.java.sp.graph.Graph;
import main.java.sp.graph.Vertex;

import java.util.LinkedList;
import java.util.StringJoiner;

public final class Path<V, E> extends LinkedList<Vertex<V, E>> {
    private final Graph<V, E> graph;
    private final Vertex<V, E> source;
    private final Vertex<V, E> destination;
    private final SPAlgorithm<V, E> algorithm;
    private final double cost;
    private final boolean isPath;

    public Path(Path<V, E> path) {
        super(path);
        this.graph = path.graph;
        this.source = path.source;
        this.destination = path.destination;
        this.algorithm = path.algorithm;
        this.cost = path.cost;
        this.isPath = path.isPath;
    }

    public Path(SPAlgorithm<V, E> algorithm) {
        this.graph = algorithm.graph;
        this.source = algorithm.source;
        this.destination = algorithm.destination;
        DirectedTree<V, E> directedTree = algorithm.directedTree;
        this.algorithm = algorithm;
        isPath = directedTree.containsKey(this.destination);
        if (!isPath) {
            cost = -1;
            return;
        }
        add(destination);
        Vertex<V, E> targetVertex = destination;
        while (!targetVertex.equals(source)) {
            Vertex<V, E> parentVertex = directedTree.get(targetVertex).getKey();
            addFirst(parentVertex);
            targetVertex = parentVertex;
        }
        cost = directedTree.get(destination).getCost();
    }

    public double getCost() {
        return cost;
    }

    public Vertex<V, E> getSource() {
        return source;
    }

    public Vertex<V, E> getDestination() {
        return destination;
    }

    public Graph<V, E> getGraph() {
        return graph;
    }

    public boolean isPath() {
        return isPath;
    }

    public void print() {
        if (!isPath) {
            System.out.println(algorithm + " failed to find a path");
            return;
        }
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        for (int i = 0; i < size() - 1; i++) {
            Vertex<V, E> s = get(1);
            Vertex<V, E> d = get(i + 1);
            Edge<V, E> link = s.getEdgeTo(d);
            sj.add("    " + s.getLabel() + " => " + link.getLabel() + " => " + d.getLabel());
        }
        System.out.println(toString() + " is:");
        System.out.println(sj.toString());
        System.out.println("cost=" + cost);
    }

    public String toString() {
        return "Shortest path from " + source + " to " + destination + " in " + graph + " with " + algorithm.getName();
    }


}
