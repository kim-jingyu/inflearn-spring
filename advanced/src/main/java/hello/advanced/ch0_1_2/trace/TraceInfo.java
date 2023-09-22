package hello.advanced.ch0_1_2.trace;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TraceInfo {
    private String id;
    private int level;

    public TraceInfo() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.level = 0;
    }

    private TraceInfo(String id, int level) {
        this.id = id;
        this.level = level;
    }

    public TraceInfo createNextLevelInfo() {
        return new TraceInfo(id, level + 1);
    }

    public TraceInfo createPreviousLevelInfo() {
        return new TraceInfo(id, level - 1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }
}
