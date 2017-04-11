package mpd.Util;

/**
 * Created by Magic on 10.04.2017.
 */
public class Job {

    private String taskName;
    private int processor;
    private int taskTime;

    public Job(String taskName, int processor, int taskTime) {
        this.taskName = taskName;
        this.processor = processor;
        this.taskTime = taskTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getProcessor() {
        return processor;
    }

    public void setProcessor(int processor) {
        this.processor = processor;
    }

    public int getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(int taskTime) {
        this.taskTime = taskTime;
    }
}
