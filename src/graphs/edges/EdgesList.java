package graphs.edges;

import java.util.*;

// https://www.youtube.com/watch?v=Yvp0-Og2T28
public class EdgesList {
    public static void main(String[] args) throws Exception {
        Graph graph = new Graph();

        graph.addVertex("a", "a");
        graph.addVertex("b", "b");
        graph.addVertex("c", "c");
        graph.addVertex("d", "d");
        graph.addVertex("e", "e");

        graph.addEdge("a", "e");
        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("a", "d");
        graph.addEdge("b", "c");

        System.out.println(graph);

//        graph.removeEdge("a", "b");
//        System.out.println(graph);

//        graph.removeVertex("a");
//        System.out.println(graph);

//        System.out.println(graph.adjacent("a", "c"));
//        System.out.println(graph.adjacent("c", "d"));
        System.out.println(graph.neighbors("c"));
    }
}

class Graph {
    private List<Vertex> vertexes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    public Vertex findNodeById(String id) {
        for (Vertex vertex : vertexes) {
            if (Objects.equals(id, vertex.getId())) {
                return vertex;
            }
        }
        return null;
    }

    public void addVertex(String id, Object value) {
        Vertex vertexById = findNodeById(id);
        if (vertexById != null) {
            throw new IllegalArgumentException("Vertex with this id already exist");
        }
        vertexes.add(new Vertex(id, value));
    }

    public void addEdge(String node_id_a, String node_id_b) {
        Vertex from = findNodeById(node_id_a);
        Vertex to = findNodeById(node_id_b);
        if (from == null || to == null) {
            throw new IllegalArgumentException("No node with this id");
        }
        if (from == to) {
            throw new IllegalArgumentException("Loop Edge");
        }
        edges.add(new Edge(from, to));
    }

    public boolean removeEdge(String node_id_a, String node_id_b) {
        Vertex from = findNodeById(node_id_a);
        Vertex to = findNodeById(node_id_b);
        if (from == null || to == null) {
            throw new IllegalArgumentException("No node with this id");
        }
        for (Edge edge : edges) {
            if (edge.getFrom() == from && edge.getTo() == to || edge.getFrom() == to && edge.getTo() == from) {
                edges.remove(edge);
                return true;
            }
        }
        return false;
    }

    public void removeVertex(String id) {
        Vertex removeVertex = findNodeById(id);
        if (removeVertex == null) {
            throw new IllegalArgumentException("No node with this id");
        }
        List<Edge> removeEdgeList = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getTo() == removeVertex || edge.getFrom() == removeVertex) {
                removeEdgeList.add(edge);
            }
        }
        for (Edge edge : removeEdgeList) {
            edges.remove(edge);
        }
        vertexes.remove(removeVertex);
    }

    public boolean adjacent(String node_id_a, String node_id_b) {
        Vertex from = findNodeById(node_id_a);
        Vertex to = findNodeById(node_id_b);
        if (from == null || to == null) {
            throw new IllegalArgumentException("No node with this id");
        }
        for (Edge edge : edges) {
            if (edge.getFrom() == from && edge.getTo() == to || edge.getFrom() == to && edge.getTo() == from) {
                return true;
            }
        }
        return false;
    }

    public Set<Vertex> neighbors(String id) {
        Vertex vertex_from = findNodeById(id);
        if (vertex_from == null) {
            throw new IllegalArgumentException("No node with this id");
        }
        Set<Vertex> neighbors = new HashSet<>();
        for (Edge edge : edges) {
            if (edge.getTo() == vertex_from) {
                neighbors.add(edge.getFrom());
            }
            if (edge.getFrom() == vertex_from) {
                neighbors.add(edge.getTo());
            }
        }
        return neighbors;
    }

    @Override
    public String toString() {
        return "graphs.edges.Graph{" +
                "vertexes=" + vertexes +
                ", edges=" + edges +
                '}';
    }

    class Vertex {
        private String id;
        private Object value;

        public Vertex(String id, Object value) {
            this.id = id;
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return id;
        }
    }

    class Edge {
        private Vertex from;
        private Vertex to;

        public Edge(Vertex from, Vertex to) {
            this.from = from;
            this.to = to;
        }

        public Vertex getFrom() {
            return from;
        }

        public Vertex getTo() {
            return to;
        }

        @Override
        public String toString() {
            return from.getId() + " - " + to.getId();
        }
    }
}