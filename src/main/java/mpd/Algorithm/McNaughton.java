package mpd.Algorithm;

import mpd.Util.Job;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Magic on 10.04.2017.
 */
@Component
public class McNaughton {

    private static int Cmax;
    private static int processorTime;
    private static int processor;
    private static List<Job> jobsTime;

    public int getCmax(){
        return Cmax;
    }

    public int[][] sequencingJobs(HashMap<String, Integer> machine, int numberOfMachine) {

        int maxTime;
        int fullTime = 0;
        processor = 1;

        maxTime = maxTime(machine);
        for (Map.Entry<String, Integer> entry : machine.entrySet()
                ) {
            fullTime += entry.getValue();
        }
        int midTimeProc = (int) Math.ceil((double)fullTime/(double)numberOfMachine);
        Cmax = Math.max(maxTime, midTimeProc);

        return machineTimes(machine);
    }

    public int[][] machineTimes(HashMap<String, Integer> machine)
    {
        jobsTime = new ArrayList<>();
        processorTime = Cmax;

        for (Map.Entry<String, Integer> entry :
                machine.entrySet()) {
            getTask(entry,entry.getValue());
        }

        int[][] machineTimes = new int[jobsTime.size()][5];
        int i = 0;
        int proc = 0;
        for (Job job : jobsTime
                ) {
            machineTimes[i][0] = Integer.parseInt(job.getTaskName().substring(1,2));
            machineTimes[i][1] = job.getProcessor();
            if(proc != job.getProcessor()) {
                machineTimes[0][2] = 0;
                proc++;
            }
            else {
                machineTimes[i][2] = machineTimes[i - 1][3];
            }
            machineTimes[i][3] = machineTimes[i][2] + job.getTaskTime();
            machineTimes[i][4] = Cmax;
            i++;
        }
        return machineTimes;
    }

    public static void getTask(Map.Entry<String, Integer> entry, int taskTime){
        if(taskTime <= processorTime) {
            jobsTime.add(new Job(entry.getKey(), processor, taskTime));
            processorTime -= taskTime;
        }
        else{
            jobsTime.add(new Job(entry.getKey(), processor, processorTime));
            taskTime -= processorTime;
            processorTime = Cmax;
            processor++;
            getTask(entry,taskTime);
        }
    }

    public static Integer maxTime(HashMap<String, Integer> map){
        return Collections.max(map.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getValue();
    }
}
