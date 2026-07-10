package domain.monitoring.stats;

import domain.DomainManager;
import domain.GlobalStats;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GlobalStatsRegistry {

    private static final Logger logger = LoggerFactory.getLogger(GlobalStatsRegistry.class);

    private GlobalStats instance;

    @PostConstruct
    public void init() {
        GlobalStats[] existing = new GlobalStats[1];
        DomainManager.getGlobalStatsList().forEach(gs -> {
            if (existing[0] == null) existing[0] = gs;
        });

        instance = existing[0] != null ? existing[0] : createGlobalStats();
        logger.info("GlobalStats singleton ready (gemId={})", instance.getGemId());
    }

    private GlobalStats createGlobalStats() {
        return DomainManager.globalStatsBuilder()
                .executionTimeMs(0)
                .processedEventsCount(0)
                .efficiency(0)
                .healthScore(85)
                .totalCo2Emissions(0.0)
                .totalEnergyConsumption(0.0)
                .build()
                .orElseThrow(() -> new IllegalStateException("Failed to create GlobalStats singleton. Check if all required attributes are set in the builder."));
    }

    public GlobalStats getInstance() {
        return instance;
    }
}
