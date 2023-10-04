package uk.co.topfieldconsultancy.stem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.topfieldconsultancy.stem.domain.Project;
import uk.co.topfieldconsultancy.stem.domain.ProjectRepository;
import uk.co.topfieldconsultancy.stem.infrastructure.web.ProjectController;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectApplication {

    @Autowired
    public ProjectRepository projectRepository;

    public Project create(ProjectController.CreateProjectRequest createProjectRequest, Long userId) {

        Project saved = projectRepository.save(Project.builder()
                .type(createProjectRequest.getType())
                .abbreviation(createProjectRequest.getAbbreviation())
                .description(createProjectRequest.getDescription())
                .name(createProjectRequest.getName())
                .userId(userId)
                .build());
        return saved;
    }

    public Project findById(Long projectId, Long userId) {
        return projectRepository.findByIdAndUserId(projectId, userId).orElseThrow();
    }

    public List<Project> findAll(Long userId){
        return projectRepository.findAllByUserId(userId);
    }

    public Project update(ProjectController.UpdateProjectRequest updateProjectRequest, Long projectId, Long userId){
        Project projectToUpdate = projectRepository.findByIdAndUserId(projectId, userId).orElseThrow();

        projectToUpdate.setAbbreviation(updateProjectRequest.getAbbreviation());
        projectToUpdate.setType(updateProjectRequest.getType());
        projectToUpdate.setName(updateProjectRequest.getName());
        projectToUpdate.setDescription(updateProjectRequest.getDescription());

        Project saved = projectRepository.save(projectToUpdate);
        return saved;
    }

    public boolean delete(Long projectId, Long userId) {
        Project byIdAndUserId = projectRepository.findByIdAndUserId(projectId, userId).orElseThrow();
        projectRepository.delete(byIdAndUserId);
        return true;
    }
}
