package ISUGeoguessr.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long>
    {
        Stats findById(int StatsId);

        @Transactional
        void deleteById(int StatsId);
    }
