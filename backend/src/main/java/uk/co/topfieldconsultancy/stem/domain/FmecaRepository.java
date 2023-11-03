package uk.co.topfieldconsultancy.stem.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.co.topfieldconsultancy.stem.domain.fmeca.FmecaCalculator;

import java.util.List;
import java.util.Optional;

@Repository
public interface FmecaRepository extends JpaRepository<FmecaCalculator, Long> {

    Optional<FmecaCalculator> findByIdAndUserIdAndProjectId(Long id, Long userId, Long projectId);
    List<FmecaCalculator> findAllByUserIdAndProjectId(Long userId, Long projectId);
    List<FmecaCalculator> findAllByUserIdAndProjectIdAndParentFmecaId(Long userId, Long projectId, Long parentFmecaId);
}
