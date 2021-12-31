package recipes.business.ext;

import java.util.List;

public final class ListsElementsRemover {

    private ListsElementsRemover() {
    }

    public static void removeStartingFromIndex(final List<?> list, final int index) {
        var iterator = list.listIterator(index);
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }
}
