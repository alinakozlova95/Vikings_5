package mephi.viking5.service;
import mephi.viking5.model.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class VikingStatisticsService {

    private final VikingService vikingService;
    private final Random random = new Random();

    public VikingStatisticsService(VikingService vikingService) {
        this.vikingService = vikingService;
    }

    private List<Viking> vikings() {
        return vikingService.findAll();
    }
    
    public long countAgeGreaterThan(int age) {
        return vikings().stream()
                .filter(v -> v.age() > age)
                .count();
    }

    public long countAgeLessThan(int age) {
        return vikings().stream()
                .filter(v -> v.age() < age)
                .count();
    }

    public long countAgeBetween(int min, int max) {
        return vikings().stream()
                .filter(v -> v.age() >= min && v.age() <= max)
                .count();
    }

    public long countAgeOutside(int min, int max) {
        return vikings().stream()
                .filter(v -> v.age() < min || v.age() > max)
                .count();
    }

    public long countBeardAndHair(BeardStyle beard, HairColor hair) {
        return vikings().stream()
                .filter(v -> v.beardStyle() == beard && v.hairColor() == hair)
                .count();
    }

    public long countOneOrTwoAxes() {
        return vikings().stream()
                .filter(v -> {
                    long axes = v.equipment().stream()
                            .filter(e -> e.name().toLowerCase().contains("axe"))
                            .count();
                    return axes == 1 || axes == 2;
                })
                .count();
    }

    public List<Viking> redBeardedSortedByAge() {
        return vikings().stream()
                .filter(v -> v.hairColor() == HairColor.Red)
                .filter(v -> v.beardStyle() != BeardStyle.CLEAN_SHAVEN)
                .sorted(Comparator.comparingInt(Viking::age))
                .collect(Collectors.toList());
    }

    public Optional<Viking> randomTallViking() {
        List<Viking> tall = vikings().stream()
                .filter(v -> v.heightCm() > 180)
                .toList();

        if (tall.isEmpty()) return Optional.empty();
        return Optional.of(tall.get(random.nextInt(tall.size())));
    }

    public List<Viking> legendaryEquipment() {
        return vikings().stream()
                .filter(v -> v.equipment().stream()
                        .anyMatch(e -> "Legendary".equals(e.quality())))
                .toList();
    }

    public int maxId() {
        return vikings().stream()
                .mapToInt(Viking::id)
                .max()
                .orElse(-1);
    }

    public int[] evenIds() {
        return vikings().stream()
                .mapToInt(Viking::id)
                .filter(id -> id % 2 == 0)
                .toArray();
    }
}