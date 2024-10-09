package ISUGeoguessr.Stats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatsRepository extends JpaRepository<Stats, Long> {
    Stats findById(int StatsId);

    void deleteById(int StatsId);
}
