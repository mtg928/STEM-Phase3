package uk.co.topfieldconsultancy.stem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.topfieldconsultancy.stem.domain.FmecaRepository;
import uk.co.topfieldconsultancy.stem.domain.fmeca.FmecaCalculator;
import uk.co.topfieldconsultancy.stem.infrastructure.web.FmecaController;

import java.util.List;

@Component
public class FmecaApplication {

    @Autowired
    FmecaRepository fmecaRepository;

    public FmecaCalculator create(FmecaController.CreateFmecaRequest createFmecaRequest, Long userId, Long projectId) {
        FmecaCalculator saved = fmecaRepository.save(FmecaCalculator.builder()
                .projectId(projectId)
                .userId(userId)
                .parentFmecaId(createFmecaRequest.getParentFmecaId())
                .componentId(createFmecaRequest.getComponentId())
                .functionName(createFmecaRequest.getFunctionName())
                .mpgType(createFmecaRequest.getMpgType())
                .calcfile(createFmecaRequest.getCalcfile())
                .calcfileId(createFmecaRequest.getCalcfileId())
                .standards(createFmecaRequest.getStandards())
                .comments(createFmecaRequest.getComments())
                .systemCode(createFmecaRequest.getSystemCode())
                .systemComponent(createFmecaRequest.getSystemComponent())
                .subSystemCode(createFmecaRequest.getSubSystemCode())
                .subSystemComponent(createFmecaRequest.getSubSystemComponent())
                .function(createFmecaRequest.getFunction())
                .phase(createFmecaRequest.getPhase())
                .failureMode(createFmecaRequest.getFailureMode())
                .failureCause(createFmecaRequest.getFailureCause())
                .severityClass(createFmecaRequest.getSeverityClass())
                .failureProbability(createFmecaRequest.getFailureProbability())
                .failureEffectProbability(createFmecaRequest.getFailureEffectProbability())
                .failureModeRatio(createFmecaRequest.getFailureModeRatio())
                .failureRate(createFmecaRequest.getFailureRate())
                .operatingTimeInHours(createFmecaRequest.getOperatingTimeInHours())
                .build());
        return saved;
    }

    public List<FmecaCalculator> findAll(Long userId, Long projectId) {
        return fmecaRepository.findAllByUserIdAndProjectId(userId, projectId);
    }

    public FmecaCalculator update(FmecaController.UpdateFmecaRequest updateFmecaRequest, Long userId, Long projectId, Long fmecaId) {
        FmecaCalculator savedFmecaCalculator = fmecaRepository.findByIdAndUserIdAndProjectId(fmecaId, userId, projectId)
                .orElseThrow();

        savedFmecaCalculator.setComponentId(updateFmecaRequest.getComponentId());
        savedFmecaCalculator.setFunctionName(updateFmecaRequest.getFunctionName());
        savedFmecaCalculator.setMpgType(updateFmecaRequest.getMpgType());
        savedFmecaCalculator.setCalcfileId(updateFmecaRequest.getCalcfileId());
        savedFmecaCalculator.setCalcfile(updateFmecaRequest.getCalcfile());
        savedFmecaCalculator.setStandards(updateFmecaRequest.getStandards());
        savedFmecaCalculator.setComments(updateFmecaRequest.getComments());
        savedFmecaCalculator.setParentFmecaId(updateFmecaRequest.getParentFmecaId());
        savedFmecaCalculator.setSystemCode(updateFmecaRequest.getSystemCode());
        savedFmecaCalculator.setSystemComponent(updateFmecaRequest.getSystemComponent());
        savedFmecaCalculator.setSubSystemCode(updateFmecaRequest.getSubSystemCode());
        savedFmecaCalculator.setSubSystemComponent(updateFmecaRequest.getSubSystemComponent());
        savedFmecaCalculator.setFunction(updateFmecaRequest.getFunction());
        savedFmecaCalculator.setPhase(updateFmecaRequest.getPhase());
        savedFmecaCalculator.setFailureMode(updateFmecaRequest.getFailureMode());
        savedFmecaCalculator.setFailureCause(updateFmecaRequest.getFailureCause());
        savedFmecaCalculator.setSeverityClass(updateFmecaRequest.getSeverityClass());
        savedFmecaCalculator.setFailureProbability(updateFmecaRequest.getFailureProbability());
        savedFmecaCalculator.setFailureEffectProbability(updateFmecaRequest.getFailureEffectProbability());
        savedFmecaCalculator.setFailureModeRatio(updateFmecaRequest.getFailureModeRatio());
        savedFmecaCalculator.setFailureRate(updateFmecaRequest.getFailureRate());
        savedFmecaCalculator.setOperatingTimeInHours(updateFmecaRequest.getOperatingTimeInHours());
        savedFmecaCalculator.setFailureModeCriticality(savedFmecaCalculator.calculateFailureModeCriticality());

        FmecaCalculator updatedFmeca = fmecaRepository.save(savedFmecaCalculator);
        return updatedFmeca;
    }


    // TODO: If parent fmeca is deleted, delete all the children
    public boolean delete(Long fmecaId, Long userId, Long projectId) {
        FmecaCalculator byIdAndUserId = fmecaRepository.findByIdAndUserIdAndProjectId(fmecaId, userId, projectId)
                .orElseThrow();
        fmecaRepository.delete(byIdAndUserId);
        return true;
    }

    public List<FmecaCalculator> findAllForParentFmeca(Long userId, Long projectId, Long parentFmecaId) {
        return fmecaRepository.findAllByUserIdAndProjectIdAndParentFmecaId(userId, projectId, parentFmecaId);
    }
}
