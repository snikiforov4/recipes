package recipes.business.mapper;

import com.google.common.base.Converter;
import org.springframework.stereotype.Component;
import recipes.persistence.entity.Direction;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class DirectionsMapper extends Converter<String[], List<Direction>> {

    @Override
    @Nonnull
    protected List<Direction> doForward(@Nonnull String[] directions) {
        List<Direction> result = new ArrayList<>();
        for (int i = 0; i < directions.length; i++) {
            Direction direction = new Direction();
            direction.setDescription(directions[i]);
            direction.setOrderNumber(i);
            result.add(direction);
        }
        return result;
    }

    @Override
    @Nonnull
    protected String[] doBackward(@Nonnull List<Direction> directions) {
        return directions.stream()
                .sorted(Comparator.comparingInt(Direction::getOrderNumber))
                .map(Direction::getDescription)
                .toArray(String[]::new);
    }
}
