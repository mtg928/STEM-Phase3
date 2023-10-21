package uk.co.topfieldconsultancy.stem.domain.fmeca;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "fmeca")
public class FmecaCalculator {
    private long id;
    private Long projectId;
    private Long userId;
    private long parentFmecaId;
    private Long componentId;
    private String functionName;
    private String mpgType;
    private Long calcfileId;
    private String calcfile;
    private String standards;
    private String comments;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Double calculateFailureModeCriticality() {
        try {
            return this.getFailureEffectProbability() * this.getFailureModeRatio() * this.getFailureRate() * this.getOperatingTimeInHours();
        } catch (Exception ex) {
            return 0D;
        }
    }
}
