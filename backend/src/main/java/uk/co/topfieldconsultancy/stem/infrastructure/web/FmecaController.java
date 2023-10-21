package uk.co.topfieldconsultancy.stem.infrastructure.web;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.topfieldconsultancy.stem.application.AuthenticationApplication;
import uk.co.topfieldconsultancy.stem.application.FmecaApplication;
import uk.co.topfieldconsultancy.stem.domain.exception.AuthorizationException;
import uk.co.topfieldconsultancy.stem.domain.fmeca.FmecaCalculator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FmecaController {

    Logger logger = LoggerFactory.getLogger(FmecaController.class);

    @Autowired
    FmecaApplication fmecaApplication;

    @Autowired
    AuthenticationApplication authenticationApplication;

    @RequestMapping(value = "/projects/{id}/fmeca", method = RequestMethod.POST)
    public ResponseEntity createFmeca(@RequestBody CreateFmecaRequest createFmecaRequest, @PathVariable("id") Long projectId, @RequestHeader(value = "Authorization") String authorizationHeader) {
        logger.info("Creating fmeca for project " + projectId);
        try {
            Long userId = authenticationApplication.extractUserIdFromAuthorizationHeader(authorizationHeader);
            FmecaCalculator createdFmeca = fmecaApplication.create(createFmecaRequest, userId, projectId);

            FmecaResponse fmecaResponse = FmecaResponse.builder()
                    .id(createdFmeca.getId())
                    .parentFmecaId(createdFmeca.getParentFmecaId())
                    .systemCode(createdFmeca.getSystemCode())
                    .systemComponent(createdFmeca.getSystemComponent())
                    .subSystemCode(createdFmeca.getSubSystemCode())
                    .subSystemComponent(createdFmeca.getSubSystemComponent())
                    .function(createdFmeca.getFunction())
                    .phase(createdFmeca.getPhase())
                    .failureMode(createdFmeca.getFailureMode())
                    .failureCause(createdFmeca.getFailureCause())
                    .severityClass(createdFmeca.getSeverityClass())
                    .failureProbability(createdFmeca.getFailureProbability())
                    .failureEffectProbability(createdFmeca.getFailureEffectProbability())
                    .failureModeRatio(createdFmeca.getFailureModeRatio())
                    .failureRate(createdFmeca.getFailureRate())
                    .operatingTimeInHours(createFmecaRequest.getOperatingTimeInHours())
                    .failureModeCriticality(createdFmeca.calculateFailureModeCriticality())
                    .build();

            return ResponseEntity.ok(fmecaResponse);
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
                            .error_message("Fmeca was not created. Please try later.")
                            .build());
        }
    }

    @RequestMapping(value = "/projects/{id}/fmeca", method = RequestMethod.GET)
    public ResponseEntity findAllFmecas(@PathVariable("id") Long projectId, @RequestHeader(value = "Authorization") String authorizationHeader) {
        logger.info("Reading all fmeca for project and user " + projectId);
        try {
            Long userId = authenticationApplication.extractUserIdFromAuthorizationHeader(authorizationHeader);
            List<FmecaCalculator> fmecaCalculatorsForProject = fmecaApplication.findAll(userId, projectId);

            List<FmecaResponse> listOfFmecas = fmecaCalculatorsForProject.stream()
                    .map(fmecaCalculator -> FmecaResponse.builder()
                            .id(fmecaCalculator.getId())
                            .parentFmecaId(fmecaCalculator.getParentFmecaId())
                            .systemCode(fmecaCalculator.getSystemCode())
                            .systemComponent(fmecaCalculator.getSystemComponent())
                            .subSystemCode(fmecaCalculator.getSubSystemCode())
                            .subSystemComponent(fmecaCalculator.getSubSystemComponent())
                            .function(fmecaCalculator.getFunction())
                            .phase(fmecaCalculator.getPhase())
                            .failureMode(fmecaCalculator.getFailureMode())
                            .failureCause(fmecaCalculator.getFailureCause())
                            .severityClass(fmecaCalculator.getSeverityClass())
                            .failureProbability(fmecaCalculator.getFailureProbability())
                            .failureEffectProbability(fmecaCalculator.getFailureEffectProbability())
                            .failureModeRatio(fmecaCalculator.getFailureModeRatio())
                            .failureRate(fmecaCalculator.getFailureRate())
                            .operatingTimeInHours(fmecaCalculator.getOperatingTimeInHours())
                            .failureModeCriticality(fmecaCalculator.calculateFailureModeCriticality())
                            .build())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(listOfFmecas);
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
                            .error_message("Fmeca was not found. Please try later.")
                            .build());
        }
    }

    @RequestMapping(value = "/projects/{id}/fmeca/{fmecaid}", method = RequestMethod.PUT)
    public ResponseEntity createFmeca(@RequestBody UpdateFmecaRequest updateFmecaRequest, @PathVariable("id") Long projectId, @PathVariable("fmecaid") Long fmecaId, @RequestHeader(value = "Authorization") String authorizationHeader) {
        logger.info("Creating fmeca for project " + projectId);
        try {
            Long userId = authenticationApplication.extractUserIdFromAuthorizationHeader(authorizationHeader);
            FmecaCalculator updatedFmeca = fmecaApplication.update(updateFmecaRequest, userId, projectId, fmecaId);

            FmecaResponse fmecaResponse = FmecaResponse.builder()
                    .id(updatedFmeca.getId())
                    .parentFmecaId(updatedFmeca.getParentFmecaId())
                    .subSystemCode(updatedFmeca.getSubSystemCode())
                    .subSystemComponent(updatedFmeca.getSubSystemComponent())
                    .function(updatedFmeca.getFunction())
                    .phase(updatedFmeca.getPhase())
                    .failureMode(updatedFmeca.getFailureMode())
                    .failureCause(updatedFmeca.getFailureCause())
                    .severityClass(updatedFmeca.getSeverityClass())
                    .failureProbability(updatedFmeca.getFailureProbability())
                    .failureEffectProbability(updatedFmeca.getFailureEffectProbability())
                    .failureModeRatio(updatedFmeca.getFailureModeRatio())
                    .failureRate(updatedFmeca.getFailureRate())
                    .operatingTimeInHours(updatedFmeca.getOperatingTimeInHours())
                    .failureModeCriticality(updatedFmeca.getFailureModeCriticality())
                    .build();

            return ResponseEntity.ok(fmecaResponse);
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
                            .error_message("Fmeca was not updated. Please try later.")
                            .build());
        }


    }

    @RequestMapping(value = "/projects/{id}/fmeca/{fmecaid}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(@PathVariable("id") Long projectId, @PathVariable("fmecaid") Long fmecaId, @RequestHeader(value = "Authorization") String authorizationHeader) {
        try {
            Long userId = authenticationApplication.extractUserIdFromAuthorizationHeader(authorizationHeader);
            boolean isFmecaDeleted = fmecaApplication.delete(fmecaId, userId, projectId);
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
                            .error_message("Fmeca was not found. Please check your id.")
                            .build());
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class CreateFmecaRequest {
        private Long id;
        private long parentFmecaId;
        private String systemCode;
        private String systemComponent;
        private String subSystemCode;
        private String subSystemComponent;
        private String function;
        private String phase;
        private String failureMode;
        private String failureCause;
        private String severityClass;
        private String failureProbability;
        private Double failureEffectProbability;
        private Double failureModeRatio;
        private Double failureRate;
        private Double operatingTimeInHours;
        private Double failureModeCriticality;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateFmecaRequest {
        private Long id;
        private long parentFmecaId;
        private String systemCode;
        private String systemComponent;
        private String subSystemCode;
        private String subSystemComponent;
        private String function;
        private String phase;
        private String failureMode;
        private String failureCause;
        private String severityClass;
        private String failureProbability;
        private Double failureEffectProbability;
        private Double failureModeRatio;
        private Double failureRate;
        private Double operatingTimeInHours;
        private Double failureModeCriticality;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class FmecaResponse {
        private Long id;
        private long parentFmecaId;
        private String systemCode;
        private String systemComponent;
        private String subSystemCode;
        private String subSystemComponent;
        private String function;
        private String phase;
        private String failureMode;
        private String failureCause;
        private String severityClass;
        private String failureProbability;
        private Double failureEffectProbability;
        private Double failureModeRatio;
        private Double failureRate;
        private Double operatingTimeInHours;
        private Double failureModeCriticality;
    }
}
