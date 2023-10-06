package uk.co.topfieldconsultancy.stem.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    public Optional<Project> findByIdAndUserId(Long id, Long userId);

    List<Project> findAllByUserId(Long userId);
}