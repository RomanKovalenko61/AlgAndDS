package graphs;

import java.util.*;

public class Graphs {
    public static void main(String[] args) {
        Graph graph = new Graph();

        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");
        graph.addVertex("e");

        graph.addEdge("a", "b");
        graph.addEdge("a", "e");
        graph.addEdge("a", "d");
        graph.addEdge("a", "c");
        graph.addEdge("c", "b");

        System.out.println(graph);

        System.out.println("isConnectedGraph " + graph.isConnectedGraph());
        graph.removeEdge("a", "d");

        System.out.println(graph);
        System.out.println("isConnectedGraph " + graph.isConnectedGraph());
    }
}

class Graph {
    private class Vertex {
        final String id;
        Object data;
        List<Vertex> adjacentVertexes = new ArrayList<>();
        int color = 0; // 0 - white, 1 - black

        public Vertex(String id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return id.equals(vertex.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    private final Map<String, Vertex> vertexes = new HashMap<>();

    public void addVertex(String id, Object data) {
        if (vertexes.get(id) != null) {
            throw new IllegalArgumentException("Node with this ID already exists");
        }
        Vertex newVertex = new Vertex(id);
        newVertex.data = data;
        vertexes.put(id, newVertex);
    }

    public void addVertex(String id) {
        addVertex(id, null);
    }

    public void addEdge(String idFrom, String idTo) {
        Vertex from = vertexes.get(idFrom);
        Vertex to = vertexes.get(idTo);
        if (from == null || to == null) {
            throw new IllegalArgumentException("Node with this id does not exists");
        }
        if (from == to) {
            throw new IllegalArgumentException("Loop edge");
        }
        from.adjacentVertexes.add(to);
        to.adjacentVertexes.add(from);
    }

    public void removeVertex(String id) {
        Vertex removeVertex = vertexes.get(id);
        if (removeVertex == null) {
            throw new IllegalArgumentException("Node with this id does not exists");
        }
        for (Vertex vertex : removeVertex.adjacentVertexes) {
            boolean remove = vertex.adjacentVertexes.remove(removeVertex);
        }
        vertexes.remove(id);
    }

    public void removeEdge(String idFrom, String idTo) {
        Vertex from = vertexes.get(idFrom);
        Vertex to = vertexes.get(idTo);
        if (from == null || to == null) {
            throw new IllegalArgumentException("Node with this id does not exist");
        }
        from.adjacentVertexes.remove(to);
        to.adjacentVertexes.remove(from);
    }

    public boolean isAdjacent(String idFrom, String idTo) {
        Vertex from = vertexes.get(idFrom);
        Vertex to = vertexes.get(idTo);
        if (from == null || to == null) {
            return false;
        }
        return from.adjacentVertexes.contains(to);
    }

    public List<String> getAdjacentVertexId(String id) {
        Vertex vertex = vertexes.get(id);
        if (vertex == null) {
            return null;
        }
        List<String> ids = new ArrayList<>();
        for (Vertex v : vertex.adjacentVertexes) {
            ids.add(v.id);
        }
        return ids;
    }

    public Object getVertexDataById(String id) {
        Vertex vertex = vertexes.get(id);
        if (vertex == null) {
            throw new IllegalArgumentException("Node with this id does not exist");
        }
        return vertex.data;
    }

    public void setVertexDataById(String id, Object data) {
        Vertex vertex = vertexes.get(id);
        if (vertex == null) {
            throw new IllegalArgumentException("Node with this id does not exist");
        }
        vertex.data = data;
    }

    public void bfs(Vertex startVertex) {
        Deque<Vertex> vertexesDeq = new ArrayDeque<>();
        vertexesDeq.push(startVertex);
        while (vertexesDeq.size() > 0) {
            Vertex currentVertex = vertexesDeq.poll();
            for (Vertex v : currentVertex.adjacentVertexes) {
                if (v.color == 0) {
                    vertexesDeq.push(v);
                }
            }
            currentVertex.color = 1;
        }
    }

    public void bfs(String startVertex) {
        Vertex vertex = vertexes.get(startVertex);
        if (vertex == null) {
            return;
        }
        bfs(vertex);
    }

    public void dfs(Vertex startVertex) {
        if (startVertex.color == 1) {
            return;
        }
        startVertex.color = 1;
        for (Vertex v : startVertex.adjacentVertexes) {
            dfs(v);
        }
    }

    public void dfs(String startVertex) {
        Vertex vertex = vertexes.get(startVertex);
        if (vertex == null) {
            return;
        }
        dfs(vertex);
    }

    public boolean isConnectedGraph() {
        boolean result = true;
        repaintVertexesToWhiteColor();
        for (String vertexId : vertexes.keySet()) {
            bfs(vertexes.get(vertexId));
//            dfs(vertexes.get(vertexId));
            break;
        }
        for (String vertexId : vertexes.keySet()) {
            if (vertexes.get(vertexId).color == 0) {
                result = false;
                break;
            }
        }
        repaintVertexesToWhiteColor();
        return result;
    }

    private void repaintVertexesToWhiteColor() {
        for (String vertexId : vertexes.keySet()) {
            vertexes.get(vertexId).color = 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String key : vertexes.keySet()) {
            builder.append(key);
            builder.append(" -> ");
            for (Vertex vertex : vertexes.get(key).adjacentVertexes) {
                builder.append(vertex.id);
                builder.append(", ");
            }
            builder.delete(builder.length() - 2, builder.length());
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
}