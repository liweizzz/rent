package study.arithmetic;

public class Snowflake {
    private final long twepoch = 1288834974657L;
    private final long machineIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxMachineId = -1L ^ (-1L << machineIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long sequenceBits = 12L;
    private final long machineIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + machineIdBits;
    private final long timestampLeftShift = sequenceBits + machineIdBits + datacenterIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long machineId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public Snowflake(long machineId, long datacenterId) {
        if (machineId > maxMachineId || machineId < 0) {
            throw new IllegalArgumentException(String.format("Machine ID can't be greater than %d or less than 0", maxMachineId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("Datacenter ID can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.machineId = machineId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (machineId << machineIdShift) |
                sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        Snowflake snowflake = new Snowflake(1, 1);
        for (int i = 0; i < 10; i++) {
            long id = snowflake.nextId();
            System.out.println(id);
        }
    }
}
