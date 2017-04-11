package mpd.Algorithm;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Magic on 05.03.2017.
 */
@Component
public class Johnson {

    public List<String> sequencingJobs(HashMap<String, Integer> machineOne, HashMap<String, Integer> machineTwo) {
        String key;
        List<String> jobs = new ArrayList<>();
        HashMap<String, Integer> L1 = new HashMap<>(); //sorted
        HashMap<String, Integer> L2 = new HashMap<>(); //not sorted
        L1.putAll(machineOne);
        while (L1.size() != 0){
            key = minIndex(L1);
            if (L1.get(key) < machineTwo.get(key)) {
                jobs.add(key);
                L1.remove(key);
            }
            else {
                L2.put(key,L1.get(key));
                L1.remove(key);
            }
        }
        while (L2.size() != 0) {
            key = maxIndex(L2);
            jobs.add(key);
            L2.remove(key);
        }
        return jobs;
    }


    public static String minIndex(HashMap<String, Integer> map) {
        return Collections.min(map.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    public static String maxIndex(HashMap<String, Integer> map) {
        return Collections.max(map.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    public int[][] machineTimes(List<String> jobs, HashMap<String, Integer> machineOne, HashMap<String, Integer> machineTwo)
    {
        int[][] machineTimes = new int[jobs.size()][5];
        int i = 0;
        for (String job : jobs
             ) {
                machineTimes[i][0] = Integer.parseInt(job.substring(1,2));
                if(i-1<0)
                    machineTimes[0][1] = 0;
                else
                    machineTimes[i][1] = machineTimes[i-1][2];
                machineTimes[i][2] = machineTimes[i][1]+ machineOne.get(job);
                if(machineTimes[i][2] > (i != 0 ? machineTimes[i-1][4] : 0))
                    machineTimes[i][3] = machineTimes[i][2];
                else
                    machineTimes[i][3] = machineTimes[i-1][4];
                machineTimes[i][4] = machineTimes[i][3]+ machineTwo.get(job);
                i++;
        }
        return machineTimes;
    }
}
