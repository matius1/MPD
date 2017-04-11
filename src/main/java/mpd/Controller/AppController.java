package mpd.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mpd.Algorithm.Johnson;
import mpd.Algorithm.McNaughton;
import mpd.Util.McNaughtonTaskTime;
import mpd.Util.TaskTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Magic on 20.03.2017.
 */
@Controller
public class AppController {

    @Autowired
    Johnson johnsonAlgorithm;

    @Autowired
    McNaughton mcNaughton;

    @RequestMapping(value = "/johnson", method = RequestMethod.GET)
    public String johnsonPage(){
        return "/johnsonView";
    }

    @RequestMapping(value = "/mcnaughton", method = RequestMethod.GET)
    public String mcNaughtonPage(){
        return "/mcnaughtonView";
    }

    @RequestMapping(value = "/johnson/getJohnsonResult", method = RequestMethod.GET)
    public
    @ResponseBody
    int[][] johnsonAlgorithm(@RequestParam("tasksTimes") String tasksTimes) throws JSONException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<TaskTime> taskTimes = new ArrayList<>();
        JSONArray jArray = new JSONArray(tasksTimes);
            for (int i=0;i<jArray.length();i++){
                TaskTime taskTime = mapper.readValue(jArray.getString(i),TaskTime.class);
                taskTimes.add(taskTime);
            }

        int i = 1;
        HashMap<String, Integer> processorOne = new HashMap<>();
        HashMap<String, Integer> processorTwo = new HashMap<>();
        for (TaskTime taskTime: taskTimes
             ) {
            processorOne.put(("Z"+i),taskTime.getTaskTimeMachineFirst());
            processorTwo.put(("Z"+i),taskTime.getTaskTimeMachineSecond());
            i++;
        }

        List<String> jobs = johnsonAlgorithm.sequencingJobs(processorOne,processorTwo);
        return johnsonAlgorithm.machineTimes(jobs,processorOne,processorTwo);
    }

    @RequestMapping(value = "/mcnaughton/getMcNaughtonResult", method = RequestMethod.GET)
    public
    @ResponseBody
    int[][] mcNaughtonAlgorithm(@RequestParam("tasksTimes") String tasksTimes,
                             @RequestParam("machineNumber") Integer machineNumber) throws JSONException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<McNaughtonTaskTime> mcNaughtonTaskTimes = new ArrayList<>();
        JSONArray jArray = new JSONArray(tasksTimes);
        for (int i=0;i<jArray.length();i++){
            McNaughtonTaskTime taskTime = mapper.readValue(jArray.getString(i),McNaughtonTaskTime.class);
            mcNaughtonTaskTimes.add(taskTime);
        }

        int i = 1;
        HashMap<String, Integer> machine = new HashMap<>();
        for (McNaughtonTaskTime taskTime: mcNaughtonTaskTimes
                ) {
            machine.put(("Z"+i),taskTime.getTaskTime());
            i++;
        }

        return mcNaughton.sequencingJobs(machine, machineNumber);
    }

    @RequestMapping(value = "/mcnaughton/getMcNaughtonCmax", method = RequestMethod.GET)
    public
    @ResponseBody
    int mcNaughtonCmax() throws JSONException, IOException {
        return mcNaughton.getCmax();
    }
}
