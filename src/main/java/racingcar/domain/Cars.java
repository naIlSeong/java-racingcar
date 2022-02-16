package racingcar.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import racingcar.domain.movestrategy.MovingStrategy;

public class Cars {

    private static final int MIN_COUNT = 2;
    private static final int MAX_COUNT = 5;
    private static final String ERROR_CAR_COUNT = "자동차는 2대 이상 5대 이하이어야 합니다.";
    private static final String ERROR_DUPLICATE_NAME = "중복된 이름입니다.";

    private final List<Car> cars;

    private Cars(final List<Car> cars) {
        validateCarCount(cars.size());
        validateDuplicateName(cars.stream()
                .map(Car::getName)
                .collect(Collectors.toList()));
        this.cars = cars;
    }

    public static Cars withNames(final List<String> names) {
        List<Car> cars = names.stream()
                .map(Car::new)
                .collect(Collectors.toList());

        return new Cars(cars);
    }

    public static Cars withCars(final List<Car> cars) {
        return new Cars(cars);
    }

    private void validateCarCount(final int size) {
        if (size > MAX_COUNT || size < MIN_COUNT) {
            throw new IllegalArgumentException(ERROR_CAR_COUNT);
        }
    }

    private void validateDuplicateName(final List<String> names) {
        Set<String> tempNameSet = new HashSet<>();
        for (String name : names) {
            tempNameSet.add(name);
        }

        if (tempNameSet.size() < names.size()) {
            throw new IllegalArgumentException(ERROR_DUPLICATE_NAME);
        }
    }

    public void moveCars(MovingStrategy movingStrategy) {
        for (Car car : cars) {
            car.move(movingStrategy);
        }
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Car car : cars) {
            stringBuilder.append(car + "\n");
        }
        return stringBuilder.toString();
    }
}
