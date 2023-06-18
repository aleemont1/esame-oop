import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeImpl<E> implements Tree<E> {

    private final E root;
    private final List<Tree<E>> children;

    TreeImpl(final E root, final List<Tree<E>> children) {
        super();
        this.root = root;
        this.children = children;
    }

    @Override
    public E getRoot() {
        return this.root;
    }

    @Override
    public List<Tree<E>> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public Set<E> getLeafs() {
        if (this.children.isEmpty()) {
            return Collections.singleton(this.root);
        }
        return this.children.stream().flatMap(t -> t.getLeafs().stream()).collect(Collectors.toSet());
    }

    @Override
    public Set<E> getAll() {
        return Stream.concat(
                Stream.of(this.root),
                this.children.stream().flatMap(t -> t.getAll().stream())).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return root.toString() + children;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;
        result = prime * result + ((children == null) ? 0 : children.hashCode());
        result = prime * result + ((root == null) ? 0 : root.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this != obj) {
            return false;
        }
        if (!(obj instanceof TreeImpl)) {
            return false;
        }
        TreeImpl other = (TreeImpl) obj;
        if (this.root == null) {
            if (other.root != null) {
                return false;
            }
        } else if (!other.root.equals(this.root)) {
            return false;
        }
        if (this.children == null) {
            if (other.children != null) {
                return false;
            }
        } else if (!other.children.equals(this.children)) {
            return false;
        }
        return true;
    }
}
