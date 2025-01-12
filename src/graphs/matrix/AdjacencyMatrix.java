package graphs.matrix;

import java.util.Arrays;
import java.util.Objects;

public class AdjacencyMatrix {
    public static void main(String[] args) {
        Graph graph = new Graph(10);

        graph.addVertex("a", null);
        graph.addVertex("b", null);
        graph.addVertex("c", null);
        graph.addVertex("d", null);
        graph.addVertex("e", null);

        graph.addEdge("a", "b");
        graph.addEdge("a", "e");
        graph.addEdge("a", "d");
        graph.addEdge("a", "c");
        graph.addEdge("c", "b");

        System.out.println(graph);

        System.out.println(graph.findVertexById("c"));

//        graph.removeEdge("b", "c");
//        System.out.println(graph);

//        graph.removeVertex("c");
//        System.out.println(graph);

        System.out.println(graph.isAdjacency("b", "c"));
        System.out.println(Arrays.toString(graph.getAdjacencyIndexes("b")));
    }
}

class Graph {
    private static final int DEFAULT_CAPACITY = 10;

    Vertex[] vertexes;
    int[][] matrix;

    int size = 0;

    public Graph() {
        vertexes = new Vertex[DEFAULT_CAPACITY];
        matrix = new int[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    }

    public Graph(int capacity) {
        vertexes = new Vertex[capacity];
        matrix = new int[capacity][capacity];
    }

    public int getSize() {
        return size;
    }

    public int findVertexById(String id) {
        int index = -1;
        for (int i = 0; i < vertexes.length; i++) {
            Vertex v = vertexes[i];
            if (v == null) {
                break;
            } else if (v.isPresent()) {
                if (Objects.equals(v.getId(), id)) {
                    index = i;
                }
            }
        }
        return index;
    }

    public void addVertex(String id, Object data) {
        int index = findVertexById(id);
        if (index != -1) {
            throw new IllegalArgumentException("Node with this ID already exists");
        } else {
            vertexes[size++] = new Vertex(id, null, true);
        }
    }

    public void addEdge(String idFrom, String idTo) {
        int indexFrom = findVertexById(idFrom);
        int indexTo = findVertexById(idTo);
        if (indexFrom == -1 || indexTo == -1) {
            throw new IllegalArgumentException("Node with this id does not exists");
        }
        if (indexFrom == indexTo) {
            throw new IllegalArgumentException("Loop edge");
        }
        matrix[indexFrom][indexTo] = 1;
        matrix[indexTo][indexFrom] = 1;
    }

    public void removeVertex(String id) {
        int removeIndex = findVertexById(id);
        if (removeIndex == -1) {
            throw new IllegalArgumentException("Node with this id does not exists");
        }
        vertexes[removeIndex].setPresent(false);
        for (int i = 0; i < size; i++) {
            matrix[removeIndex][i] = 0;
        }
        for (int i = 0; i < size; i++) {
            matrix[i][removeIndex] = 0;
        }
//        System.arraycopy(vertexes, removeIndex + 1, vertexes, removeIndex, size - 1 - removeIndex);
        size--;
    }

    public void removeEdge(String idFrom, String idTo) {
        int indexFrom = findVertexById(idFrom);
        int indexTo = findVertexById(idTo);
        if (indexFrom == -1 || indexTo == -1) {
            throw new IllegalArgumentException("Node with this id does not exists");
        }
        matrix[indexFrom][indexTo] = 0;
        matrix[indexTo][indexFrom] = 0;
    }

    public boolean isAdjacency(String idFrom, String idTo) {
        int indexFrom = findVertexById(idFrom);
        int indexTo = findVertexById(idTo);
        if (indexFrom == -1 || indexTo == -1) {
            throw new IllegalArgumentException("Node with this id does not exists");
        }
        if (matrix[indexFrom][indexTo] == 1) {
            return true;
        }
        return false;
    }

    public String[] getAdjacencyIndexes(String id) {
        int vertexIndex = findVertexById(id);
        if (vertexIndex == -1) {
            throw new IllegalArgumentException("Node with this id does not exists");
        }
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (matrix[vertexIndex][i] == 1) {
                count++;
            }
        }
        String[] ids = new String[count];
        int insertIndex = 0;
        for (int i = 0; i < size; i++) {
            if (matrix[vertexIndex][i] == 1) {
                ids[insertIndex++] = String.valueOf(i);
            }
        }
        return ids;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AdjacencyMatrix");
        builder.append("\n");
        for (int i = 0; i < vertexes.length; i++) {
            if (vertexes[i] == null) {
                break;
            }
            builder.append("\t");
            builder.append(vertexes[i]);
        }
        builder.append("\n");
        for (int i = 0; i < vertexes.length; i++) {
            if (vertexes[i] == null) {
                break;
            }
            builder.append(vertexes[i]);
            for (int j = 0; j < vertexes.length; j++) {
                if (vertexes[j] == null) {
                    break;
                }
                builder.append("\t");
                builder.append(matrix[i][j]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    class Vertex {
        private final String id;
        private Object data;
        private boolean isPresent;

        public Vertex(String id, Object data, Boolean isPresent) {
            this.id = id;
            this.data = data;
            this.isPresent = isPresent;
        }

        public String getId() {
            return id;
        }

        public void setPresent(boolean present) {
            isPresent = present;
        }

        public boolean isPresent() {
            return isPresent;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}