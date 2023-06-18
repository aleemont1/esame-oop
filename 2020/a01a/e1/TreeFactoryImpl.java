import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeFactoryImpl implements TreeFactory {

    @Override
    public <E> Tree<E> singleValue(E root) {
        return new TreeImpl<>(root, Collections.emptyList());
    }

    @Override
    public <E> Tree<E> twoChildren(E root, Tree<E> child1, Tree<E> child2) {
        return new TreeImpl<>(root, List.of(child1, child2));
    }

    @Override
    public <E> Tree<E> oneLevel(E root, List<E> children) {
        List<Tree<E>> c = new ArrayList<>();
        for (E child : children) {
            c.add(this.singleValue(child));
        }
        return new TreeImpl<>(root, c);
    }

    @Override
    public <E> Tree<E> chain(E root, List<E> list) {
        if (list.isEmpty()) {
            return singleValue(root);
        }
        return new TreeImpl<E>(root, List.of(chain(list.get(0), list.subList(1, list.size()))));
    }

}
