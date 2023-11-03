package uk.co.topfieldconsultancy.stem.infrastructure.web;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.topfieldconsultancy.stem.application.AuthenticationApplication;
import uk.co.topfieldconsultancy.stem.application.ProjectApplication;
import uk.co.topfieldconsultancy.stem.domain.Project;
import uk.co.topfieldconsultancy.stem.domain.exception.AuthorizationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProjectController {
    Logger logger = LoggerFactory.getLogger(ProjectApplication.class);
    @Autowired
    ProjectApplication projectApplication;

    @Autowired
    AuthenticationApplication authenticationApplication;

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public ResponseEntity createProject(@RequestBody CreateProjectRequest createProjectRequest, @RequestHeader(value = "Authorization") String authorizationHeader) {
        logger.info("Creating project");
        try {
            Long userId = authenticationApplication.extractUserIdFromAuthorizationHeader(authorizationHeader);
            Project createdProject = projectApplication.create(createProjectRequest, userId);
            return ResponseEntity.ok(CreateProjectResponse.builder()
                    .id(createdProject.getId())
                    .name(createdProject.getName())
                    .type(createdProject.getType())
                    .abbreviation(createdProject.getAbbreviation())
                    .description(createdProject.getDescription())
                    .client(createdProject.getClient())
                    .comments(createdProject.getComments())
                    .owner(createdProject.getOwner())
                    .status(createdProject.getStatus())
                    .createdDate(createdProject.getCreatedDate())
                    .lastUpdated(createdProject.getLastUpdated())
                    .label(createdProject.getLabel())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ErrorResponse.builder()
                            .error(e.getLocalizedMessage())
                            .error_message("Project was not created. Please try later.")
                            .build());
        }
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public ResponseEntity findProjectById(@PathVariable("id") Long id, @RequestHeader(value = "Authorization") String authorizationHeader) {
        try {
            Long userId = authenticationApplication.extractUserIdFromAuthorizationHeader(authorizationHeader);
            Project projectForUser = projectApplication.findById(id, userId);
            return ResponseEntity.ok(CreateProjectResponse.builder()
                    .id(projectForUser.getId())
                    .name(projectForUser.getName())
                    .type(projectForUser.getType())
                    .abbreviation(projectForUser.getAbbreviation())
                    .description(projectForUser.getDescription())
                    .client(projectForUser.getClient())
                    .comments(projectForUser.getComments())
                    .owner(projectForUser.getOwner())
                    .status(projectForUser.getStatus())
                    .label(projectForUser.getLabel())
                    .createdDate(projectForUser.getCreatedDate())
                    .lastUpdated(projectForUser.getLastUpdated())
                    .build());
        } catch (AuthorizationException exception) {
            return ResponseEntity.status(401)
                    .body(ErrorResponse.builder()
                            .error(exception.getLocalizedMessage())
                            .error_message("Please login again!")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ErrorResponse.builder()
                            .error(e.getLocalizedMessage())
                            .error_message("Project was not found. Please check your id.")
                            .build());
        }
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ResponseEntity getAllProjects(@RequestHeader(value = "Authorization") String authorizationHeader) {
        try {
            Long userId = authenticationApplication.extractUserIdFromAuthorizationHeader(authorizationHeader);
            List<Project> projectsForUser = projectApplication.findAll(userId);

            return ResponseEntity.ok(projectsForUser.stream()
                    .map(project -> CreateProjectResponse.builder()
                            .id(project.getId())
                            .name(project.getName())
                            .type(project.getType())
                            .abbreviation(project.getAbbreviation())
                            .description(project.getDescription())
                            .client(project.getClient())
                            .comments(project.getComments())
                            .owner(project.getOwner())
                            .status(project.getStatus())
                            .createdDate(project.getCreatedDate())
                            .lastUpdated(project.getLastUpdated())
                            .label(project.getLabel())
                            .build())
                    .collect(Collectors.toList()));

        } catch (AuthorizationException exception) {
            return ResponseEntity.status(401)
                    .body(ErrorResponse.builder()
                            .error(exception.getLocalizedMessage())
                            .error_message("Please login again!")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ErrorResponse.builder()
                            .error(e.getLocalizedMessage())
                            .error_message("Searching for projects was not successfull!")
                            .build());
        }
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateProject(@PathVariable("id") Long id, @RequestBody UpdateProjectRequest updateProjectRequest, @RequestHeader(value = "Authorization") String authorizationHeader) {
        try {
            Long userId = authenticationApplication.extractUserIdFromAuthorizationHeader(authorizationHeader);
            Project updatedProject = projectApplication.update(updateProjectRequest, id, userId);

            return ResponseEntity.ok(CreateProjectResponse.builder()
                    .id(updatedProject.getId())
                    .name(updatedProject.getName())
                    .type(updatedProject.getType())
                    .abbreviation(updatedProject.getAbbreviation())
                    .description(updatedProject.getDescription())
                    .client(updatedProject.getClient())
                    .comments(updatedProject.getComments())
                    .owner(updatedProject.getOwner())
                    .status(updatedProject.getStatus())
                    .createdDate(updatedProject.getCreatedDate())
                    .lastUpdated(updatedProject.getLastUpdated())
                    .label(updatedProject.getLabel())
                    .build());

        } catch (AuthorizationException exception) {
            return ResponseEntity.status(401)
                    .body(ErrorResponse.builder()
                            .error(exception.getLocalizedMessage())
                            .error_message("Please login again!")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ErrorResponse.builder()
                            .error(e.getLocalizedMessage())
                            .error_message("Project was not found. Please check your id.")
                            .build());
        }
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(@PathVariable("id") Long id, @RequestHeader(value = "Authorization") String authorizationHeader) {
        try {
            Long userId = authenticationApplication.extractUserIdFromAuthorizationHeader(authorizationHeader);
            boolean isProjectDeleted = projectApplication.delete(id, userId);
            return ResponseEntity.ok()
                    .build();

        } catch (AuthorizationException exception) {
            return ResponseEntity.status(401)
                    .body(ErrorResponse.builder()
                            .error(exception.getLocalizedMessage())
                            .error_message("Please login again!")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ErrorResponse.builder()
                            .error(e.getLocalizedMessage())
                            .error_message("Project was not found. Please check your id.")
                            .build());
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class CreateProjectRequest {
        private String name;
        private String type;
        private String abbreviation;
        private String description;
        private String client;
        private String owner;
        private String label;
        private String status;
        private String comments;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class CreateProjectResponse {
        private Long id;
        private String name;
        private String type;
        private String abbreviation;
        private String description;
        private String client;
        private String owner;
        private String status;
        private String comments;
        private String label;
        private LocalDateTime createdDate;
        private LocalDateTime lastUpdated;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateProjectRequest {
        private Long id;
        private String name;
        private String type;
        private String abbreviation;
        private String description;
        private String client;
        private String owner;
        private String status;
        private String comments;
        private String label;
        private LocalDateTime createdDate;
        private LocalDateTime lastUpdated;
    }


}
